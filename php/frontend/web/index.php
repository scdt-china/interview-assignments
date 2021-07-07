<?php
// 部署到生产环境时注释掉下面两行
defined('YII_DEBUG') or define('YII_DEBUG', true);
defined('YII_ENV') or define('YII_ENV', 'dev');

error_reporting(E_ALL & ~(E_STRICT | E_NOTICE));

// 注册 Composer 自动加载器
require(__DIR__ . '/../../yii/autoload.php');

// 包含yii类文件
require(__DIR__ . '/../../yii/yiisoft/yii2/Yii.php');
require(__DIR__ . '/../../common/config/alias.php');

// 加载应用配置
$config = yii\helpers\ArrayHelper::merge(
    require(__DIR__ . '/../config/main.php'),
    require(__DIR__ . '/../../common/config/db.php')
);

// 创建、配置、运行一个应用
(new yii\web\Application($config))->run();
