<?php
$params = require(__DIR__ . '/../../common/config/params.php');
$config = [
    'id' => 'frontend',
    'basePath' => dirname(__DIR__),
    'bootstrap' => ['log'],
    'language'=>'zh-CN',
    'vendorPath' =>  dirname(dirname(__DIR__)) .'/yii',
    'controllerNamespace' => 'frontend\controllers',
    'params' => $params,
    'components' => [
        'request' => [
            // !!! insert a secret key in the following (if it is empty) - this is required by cookie validation
            'cookieValidationKey' => '4sdfsfsds477',
            'enableCookieValidation' => false,
            'enableCsrfValidation' => false,
        ],
        'redis' => [
            'class' => 'yii\redis\Connection',
            'hostname' => 'localhost',
            'port' => 6379,
        ],
        // 路由配置
        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
            ],
        ],
        'cache' => [
            'class' => 'yii\caching\FileCache',
        ],
        'htmlCache' => [
            'class' => 'yii\caching\FileCache',
            'cachePath' => '@runtime/htmlCache'
        ],
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],
        'mailer' => [
            'class' => 'yii\swiftmailer\Mailer',
            // send all mails to a file by default. You have to set
            // 'useFileTransport' to false and configure a transport
            // for the mailer to send real emails.
            'useFileTransport' => false,
            'transport' => [
                'class' => 'Swift_SmtpTransport',
                'host' => 'smtp.codesafe.cn.com',
                'username' => 'service@codesafe.cn',
                'password' => 'Bjct62713007.-',
                'port' => '25',
                'encryption' => 'tls',
            ]
        ],
        'log' => [
            'traceLevel' => YII_DEBUG ? 3 : 0,
            'targets' => [
                [
                    'class' => 'yii\log\FileTarget',
                    'levels' => ['error', 'warning'],
                ],
                [
                    'class' => 'yii\log\FileTarget',
                    'levels' => ['info', 'error', 'warning'],
                    'categories' => ['jiyong'],
//                    'logVars' => [],
                    'logFile' => '@app/runtime/logs/jiyong-dev.log',
                    'maxFileSize' => 1024 * 10,
                    'maxLogFiles' => 100,
                ],
                [
                    'class' => 'yii\log\FileTarget',
                    'levels' => ['info', 'error', 'warning'],
                    'categories' => ['email'],
//                    'logVars' => [],
                    'logFile' => '@app/runtime/logs/email.log',
                    'maxFileSize' => 1024 * 10,
                    'maxLogFiles' => 100,
                ],
            ],
        ],
    ],
];

if (!YII_ENV_DEV) {
    // configuration adjustments for 'dev' environment
    $config['bootstrap'][] = 'debug';
    $config['modules']['debug'] = [
        'class' => 'yii\debug\Module',
    ];

    $config['bootstrap'][] = 'gii';
    $config['modules']['gii'] = [
        'class' => 'yii\gii\Module',
    ];
}

return $config;
