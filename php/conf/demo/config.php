<?php
/**
 * 应用配置
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['default']['namespace'] = 'Project\Demo\Controller';

$settings['url']['baseUri']       = '/';
$settings['url']['staticBaseUri'] = '/assets/demo/';
$settings['url']['basePath']      = '/';

// Https 前提下，overall + client 模式 仅可用 Cookie
$settings['session']['mode']['origin']   = 'external';
$settings['session']['mode']['side']     = 'server';    // 存 server 就仅传 唯一标识符， 存 client 就传 唯一标识符和认证信息
$settings['session']['mode']['transfer'] = 'cookie';    // overall 模式可用 cookie/url，alone 模式可用 header/url
$settings['session']['mode']['adapter']  = 'stream';
$settings['session']['basic']['name']            = 'key';
$settings['session']['basic']['gc_maxlifetime']  = 1800;
$settings['session']['basic']['use_trans_sid']   = 1;
$settings['session']['basic']['use_cookies']     = 0;
$settings['session']['basic']['cookie_lifetime'] = 1800;
$settings['session']['basic']['cookie_domain']   = 'localhost';
$settings['session']['basic']['cookie_httponly'] = 1;
$settings['session']['basic']['cookie_secure']   = 0;
$settings['session']['basic']['cookie_samesite'] = 'Strict';
$settings['session']['basic']['cache_limiter']   = 'public';
$settings['session']['basic']['cache_expire']    = 300;
$settings['session']['manage']['name']         = 'USID';
$settings['session']['manage']['uniqueId']     = 'demo';
$settings['session']['stream']['prefix']       = 'demo-';
$settings['session']['stream']['savePath']     = BASE_PATH.'/var/temp/';

$settings['database']['host']     = 'localhost';
$settings['database']['port']     = '3308';
$settings['database']['username'] = 'root';
$settings['database']['password'] = 'root';
$settings['database']['dbname']   = 'demo';

return $settings;

?>