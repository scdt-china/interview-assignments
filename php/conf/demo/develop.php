<?php
/**
 * 开发环境配置（只需配置与生产环境不一致的项）
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['session']['mode']['adapter']          = 'stream';
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
$settings['session']['manage']['uniqueId']     = 'xxxx';
$settings['session']['stream']['prefix']       = 'xxxx-';
$settings['session']['stream']['savePath']     = BASE_PATH.'/var/temp/';

return APP_TIME ? $settings : [];

?>