<?php
/**
 * Created by XHL
 *
 * @author: justinzhang@leadscloud.com
 * @since: 2020/1/8 10:12
 */
use Phalcon\Config;

// 环境配置，预留读取配置文件部分
$envConfig = new \Phalcon\Config\Adapter\Ini('/var/etc/config.ini');
$envConfig = $envConfig->toArray();

//环境变量，加到配置文件中
defined('LEADSCLOUD_ENV') || define('LEADSCLOUD_ENV', $envConfig->common->env);
// 应用配置
$appConfig = [
    'baseUri' => '/',
    'title' => '自助建站',
    'path' => array(
        'controllers' => APP_PATH . '/controllers/',
        'models' => APP_PATH . '/models/',
        'views' => APP_PATH . '/views/',
        'library' => APP_PATH . '/library/'
    ),
];

return new Config(array_merge($envConfig, $appConfig));