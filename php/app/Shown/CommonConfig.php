<?php
/**
 * 通用配置（默认）
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['database']['host']     = 'localhost';
$settings['database']['port']     = '3308';
$settings['database']['username'] = 'root';
$settings['database']['password'] = 'root';
$settings['database']['dbname']   = 'test';
$settings['database']['options']  = [PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'UTF8'"];

$settings['storages']['apcu']['adapter']                      = 'apcu';
$settings['storages']['apcu']['options']['defaultSerializer'] = 'msgpack';
$settings['storages']['apcu']['options']['prefix']            = Yaconf::get('project.'.APP_NAME.'.cache.prefix');
$settings['storages']['apcu']['options']['lifetime']          = 3600;
$settings['storages']['redis']['adapter']                      = 'redis';
$settings['storages']['redis']['options']['defaultSerializer'] = 'json';
$settings['storages']['redis']['options']['prefix']            = Yaconf::get('project.'.APP_NAME.'.cache.prefix');
$settings['storages']['redis']['options']['lifetime']          = 3600;
$settings['storages']['redis']['options']['host']              = '127.0.0.1';
$settings['storages']['redis']['options']['port']              = 6379;
$settings['storages']['redis']['options']['persistent']        = false;
$settings['storages']['stream']['adapter']                      = 'stream';
$settings['storages']['stream']['options']['defaultSerializer'] = 'msgpack';
$settings['storages']['stream']['options']['lifetime']          = 3600;
$settings['storages']['stream']['options']['prefix']            = Yaconf::get('project.'.APP_NAME.'.cache.prefix');
$settings['storages']['stream']['options']['storageDir']        = BASE_PATH.'/var/cache/';
$settings['storages']['memcache']['adapter']                             = 'memcache';
$settings['storages']['memcache']['options']['defaultSerializer']        = 'msgpack';
$settings['storages']['memcache']['options']['lifetime']                 = 3600;
$settings['storages']['memcache']['options']['prefix']                   = Yaconf::get('project.'.APP_NAME.'.cache.prefix');
$settings['storages']['memcache']['options']['servers'][0]['host']       = '127.0.0.1';
$settings['storages']['memcache']['options']['servers'][0]['port']       = 11211;
$settings['storages']['memcache']['options']['servers'][0]['persistent'] = false;
$settings['storages']['memcache']['options']['servers'][0]['weight']     = 1;

// Session 性能优先级：客户端（不需要统一存储的开销）、服务端【Redis（可缓存连接）、Memcached、File】
// Https 前提，overall + client 模式 仅可用 Cookie
$settings['session']['mode']['origin']   = 'external';
$settings['session']['mode']['side']     = 'server'; // 存 server 就仅传 唯一标识符， 存 client 就传 唯一标识符和认证信息
$settings['session']['mode']['transfer'] = 'cookie'; // overall 模式可用 cookie/url，alone 模式可用 header/url
$settings['session']['mode']['token']    = 'xyzHH';  // internal 模式下使用
$settings['session']['mode']['adapter']  = 'redis';

$settings['session']['basic']['name']                         = 'key';
$settings['session']['basic']['gc_maxlifetime']               = 1800;
$settings['session']['basic']['use_trans_sid']                = 1;
$settings['session']['basic']['use_cookies']                  = 0;
$settings['session']['basic']['cookie_lifetime']              = 1800;
$settings['session']['basic']['cookie_domain']                = 'onbin.com';
$settings['session']['basic']['cookie_httponly']              = 1;
$settings['session']['basic']['cookie_secure']                = 1;
$settings['session']['basic']['cookie_samesite']              = 'Strict';
$settings['session']['basic']['cache_limiter']                = 'public';
$settings['session']['basic']['cache_expire']                 = 30;
$settings['session']['manage']['name']                        = 'USID';
$settings['session']['manage']['uniqueId']                    = 'afdfdafdfdfdad';
$settings['session']['redis']['adapter']                      = 'redis';
$settings['session']['redis']['options']['serializer']        = 'msgpack';
$settings['session']['redis']['options']['lifetime']          = 600;
$settings['session']['redis']['options']['prefix']            = 'ss-';
$settings['session']['redis']['options']['host']              = '127.0.0.1';
$settings['session']['redis']['options']['port']              = 6379;
$settings['session']['redis']['options']['index']           = 0;
$settings['session']['redis']['options']['persistent']        = true;
$settings['session']['memcache']['adapter']                             = 'memcache';
$settings['session']['memcache']['options']['defaultSerializer']        = 'msgpack';
$settings['session']['memcache']['options']['lifetime']                 = 600;
$settings['session']['memcache']['options']['prefix']                   = 'ss-';
$settings['session']['memcache']['options']['servers'][0]['host']       = '127.0.0.1';
$settings['session']['memcache']['options']['servers'][0]['port']       = 11211;
//$settings['session']['memcache']['options']['servers'][0]['persistent'] = false;
//$settings['session']['memcache']['options']['servers'][0]['weight']     = 1;

$settings['annotations']['apcu']['adapter']                     = 'apcu';
$settings['annotations']['apcu']['options']['prefix']           = 'anno';
$settings['annotations']['apcu']['options']['lifetime']         = 3600;
$settings['annotations']['stream']['adapter']                   = 'stream';
$settings['annotations']['stream']['options']['annotationsDir'] = BASE_PATH.'/var/cache/';
$settings['annotations']['memory']['adapter']                   = 'memory';

//$settings['listeners']['loader']['name']  = 'loader';
//$settings['listeners']['loader']['items'] = ['commonListener'];
$settings['listeners']['di']['name']  = 'di';
if (APP_TIME > 1) {
    foreach ($settings['listeners'] as $key => $value) {
        $settings['listeners'][$key]['items'][] = 'DebugListener';
    }
}

$settings['approved']['external']['expire'] = 5;
$settings['approved']['external']['limits'] = 5; //刷新频率
$settings['approved']['accredit']['token']  = ['username', 'card'];
$settings['approved']['accredit']['key']    = 'authing';
$settings['approved']['accredit']['expire'] = 600;
$settings['approved']['internal']['token']  = 'onbin';
$settings['approved']['internal']['key']    = 'yaoguai';
$settings['approved']['internal']['expire'] = 120;

return $settings

?>