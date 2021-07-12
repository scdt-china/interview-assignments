from aws_cdk import (core as cdk, 
                     aws_ec2 as ec2, 
                     aws_ecs as ecs,
                     aws_ecs_patterns as ecs_patterns,
                     aws_iam as iam,
                     aws_ecr as ecr,
                     aws_elasticloadbalancingv2 as elb,
                     aws_logs as logs,
                     aws_sns as sns,
                     aws_sns_subscriptions as sub,
                     aws_cloudwatch as cloudwatch,
                     aws_cloudwatch_actions as cloudwatch_actions,
                     aws_codebuild as codebuild,
                     aws_codepipeline as codepipeline,
                     aws_codepipeline_actions as codepipeline_actions
                     )

#modify below variable correspondly

aws_account="<your aws account>"
mail="<your e-mail>"
github_owner="<your github owner>"  #case-sensitive
github_repository="<your repository>"
github_token="<your github token>"

class DevopsStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)
        
        #default is all AZs in region
        vpc = ec2.Vpc(self, "MyVpc", max_azs=2)     # default is all AZs in region
        
        #create ecr
        repository=ecr.Repository(
           self,
           'devpos',
           repository_name='devops',
        )  

        #create iam role used by code build
        execution_role = iam.Role(
            self,
            "codebuild-execution-role",
            assumed_by=iam.ServicePrincipal('codebuild.amazonaws.com'),
            role_name="codebuild-execution-role")

        execution_role.add_to_policy(iam.PolicyStatement(
            effect=iam.Effect.ALLOW,
            resources=['*'],
            actions=[
                'codecommit:GitPull',
                'logs:CreateLogGroup',
                'logs:CreateLogStream',
                'logs:PutLogEvents',
                'codebuild:CreateReportGroup',
                'codebuild:CreateReport',
                'codebuild:UpdateReport',
                'codebuild:BatchPutTestCases',
                'codebuild:BatchPutCodeCoverages',
                'ecr:BatchCheckLayerAvailability',
                'ecr:CompleteLayerUpload',
                'ecr:GetAuthorizationToken',
                'ecr:InitiateLayerUpload',
                'ecr:PutImage',
                'ecr:UploadLayerPart',
            ],
        ))


        #create code build
        #access_token = cdk.SecretValue.plain_text(github_token)
        #GitHubSourceCredentials=codebuild.GitHubSourceCredentials(self,"token", access_token=access_token)
        git_hub_source = codebuild.Source.git_hub(
                owner=github_owner,
                repo=github_repository,
                webhook=False, # optional, default: true if `webhookFilters` were provided, false otherwise
                )

        project=codebuild.Project(self, "devops",
                source=git_hub_source,
                build_spec=codebuild.BuildSpec.from_source_filename(
                filename='buildspec.yml'),
                environment=codebuild.BuildEnvironment(
                    build_image=codebuild.LinuxBuildImage. from_code_build_image_id("aws/codebuild/amazonlinux2-x86_64-standard:3.0"),
                    privileged=True,
                ),
                environment_variables={
                'AWS_DEFAULT_REGION': codebuild.BuildEnvironmentVariable(
                    value='us-east-1'),
                'AWS_ACCOUNT_ID': codebuild.BuildEnvironmentVariable(
                    value=aws_account),
                'IMAGE_TAG': codebuild.BuildEnvironmentVariable(
                    value='latest'),
                'IMAGE_REPO_NAME': codebuild.BuildEnvironmentVariable(
                    value='devops'),
                },
                role=execution_role,
                )


        #create ecs cluster
        cluster = ecs.Cluster(self, "MyCluster", vpc=vpc)
        
        #create ecs task execution role which make sure it could pull image from ecr
        execution_role = iam.Role(
            self,
            "ecs-execution-role",
            assumed_by=iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
            role_name="ecs-execution-role")
        
        execution_role.add_to_policy(iam.PolicyStatement(
            effect=iam.Effect.ALLOW,
            resources=['*'],
            actions=[
                'ecr:GetAuthorizationToken',
                'ecr:BatchCheckLayerAvailability',
                'ecr:GetDownloadUrlForLayer',
                'ecr:BatchGetImage',
                'logs:CreateLogStream',
                'logs:PutLogEvents'
            ],
        ))


        #create snd and email subscription
        my_topic = sns.Topic(self, "MyTopic")
        my_topic.add_subscription(sub.EmailSubscription(mail))
        
        #crete log group
        log_group = logs.LogGroup(
            self,
            "log_group",
            log_group_name="web-logs",
        )

        #create metrid and link to log group
        metric=cloudwatch.Metric(
            metric_name="error-log",
            namespace="error-log-namespace",    
                )

        log_group.add_metric_filter(
            "filter-error-log",
            filter_pattern=logs.FilterPattern.literal('ERROR'),
            metric_name="error-log",
            metric_namespace="error-log-namespace",
        )

        alarm=metric.create_alarm(
            self,
            "error trigger email",
            evaluation_periods=1,
            threshold=10,
            statistic="sum",
            )

        alarm.add_alarm_action(cloudwatch_actions.SnsAction(my_topic))
        
        #create ecs service and loadbalancer
        load_balanced_fargate_service=ecs_patterns.ApplicationLoadBalancedFargateService(self, "MyFargateService",
            cluster=cluster,            # Required
            cpu=512,                    # Default is 256
            memory_limit_mib=2048,      # Default is 512
            desired_count=2,            # Default is 1
            task_image_options=ecs_patterns.ApplicationLoadBalancedTaskImageOptions(
                image=ecs.ContainerImage.from_ecr_repository(repository=repository,tag='latest'),
                container_port=8080,
                execution_role=execution_role,
                log_driver=ecs.LogDriver.aws_logs(stream_prefix="web_logs",log_group=log_group)
                ),
           )
        
        #create codepipine
        source_output = codepipeline.Artifact()
        build_output  = codepipeline.Artifact()
        source_action = codepipeline_actions.GitHubSourceAction(
                action_name="GitHub_Source",
                owner=github_owner,
                repo=github_repository,
                output=source_output,
                #oauth_token=cdk.SecretValue.secrets_manager("/my/github/token"),
                oauth_token=cdk.SecretValue.plain_text(github_token),
                branch="master",
                trigger=codepipeline_actions.GitHubTrigger["WEBHOOK"]
                )
        build_action = codepipeline_actions.CodeBuildAction(
                action_name="CodeBuild",
                project=project,
                input=source_output,
                outputs=[build_output],
                )
        manual_approval_action = codepipeline_actions.ManualApprovalAction(
                action_name="Approve",
                )
        deploy_action = codepipeline_actions.EcsDeployAction(
                action_name="DeployAction",
                service=load_balanced_fargate_service.service,
                image_file=build_output.at_path("imagedefinitions.json"),
        )

        pipeline = codepipeline.Pipeline(self, "MyFirstPipeline",cross_account_keys=False,
                stages=[
                    codepipeline.StageProps(
                        stage_name='Source',
                        actions=[source_action]
                        ),
                    codepipeline.StageProps(
                        stage_name='CodeBuild',
                        actions=[build_action]
                        ),
                    #codepipeline.StageProps(
                    #    stage_name='Approve',
                    #    actions=[manual_approval_action]
                    #)
                    codepipeline.StageProps(
                        stage_name='Deploy',
                        actions=[deploy_action]
                        ),
                    ],
                )

        cdk.CfnOutput(
            self, "ECR_Uri",
            description="ECR repository uri",
            value=repository.repository_uri
        )
