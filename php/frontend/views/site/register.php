<!-- 用户注册 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<?= Yii::$app->request->baseUrl; ?>/css/index.css"/>
    <script src="<?= Yii::$app->request->baseUrl; ?>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<?= Yii::$app->request->baseUrl; ?>/layer/layer.js"></script>
    <title><?=Yii::$app->params['projectTitleUser']?></title>
</head>
<style>
    .layui-layer-tls .layui-layer-title{
        background:#fe0000;
        color:#fff;
        border:none
    }
    .layui-layer-tls .layui-layer-btn a{
        background:#fe0000;
        border-color:#fe0000;
    }
    .layui-layer-tls .layui-layer-btn .layui-layer-btn1{
        background:#92B8B1;
    }
</style>
<body class="login">
    <div class="login_box">
        <img src="<?= Yii::$app->request->baseUrl; ?>/img/logo.png" alt="" class="logo">
        <h5><?=Yii::$app->params['projectNameUser']?></h5>
        <img src="<?= Yii::$app->request->baseUrl; ?>/img/greens.jpg" alt="" class="img">
        <div class="login_right">
            <h1>欢迎注册XXX平台</h1>
            <form action="<?= Yii::$app->request->baseUrl; ?>/site/register" method="post" id="reg_sub">
                <input type="text" placeholder="请输入账号" name="username" id="username">
                <input type="password" placeholder="请输入密码" class="psd" name="pwd" id="pwd">
                <input type="password" placeholder="请再次输入密码" class="psd" name="repwd" id="repwd">
                <p><button type="button" onclick="sub()">注 册</button></p>
            </form>
        </div>
    </div>
<div class="bottom"><?= Yii::$app->params['companyNameShop']; ?>&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://beian.miit.gov.cn" target="_blank" style="color: #fff;">京ICP备XXX号</a></div>
</body>
</html>
<script>
    function sub() {
        var username = $.trim($('#username').val());
        if(/^\s*$/.test(username)) {
            layer.alert('账号不能为空', {
                skin: 'layui-layer-tls'
            });
            return;
        }

        var pwd = $.trim($('#pwd').val());
        if(/^\s*$/.test(pwd)) {
            layer.alert('密码不能为空', {
                skin: 'layui-layer-tls'
            });
            return;
        }

        var repwd = $.trim($('#repwd').val());
        if(/^\s*$/.test(repwd)) {
            layer.alert('确认密码不能为空', {
                skin: 'layui-layer-tls'
            });
            return;
        }

        if(pwd != repwd) {
            layer.alert('两次密码不一致', {
                skin: 'layui-layer-tls'
            });
            return;
        }

        $.ajax({
            type: 'post',
            dataType:'json',
            url: '<?php echo Yii::$app->request->baseUrl; ?>/site/verify',
            data: {'username':username,'pwd':pwd,'repwd':repwd},
            success:function(data){
                if(data.status == 200){
                    $('#reg_sub').submit();
                }else{
                    layer.alert(data.message, {
                        skin: 'layui-layer-tls'
                    });
                    return;
                }
            }
        });
    }
</script>