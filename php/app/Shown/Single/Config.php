<?php
/**
 * Single 配置（默认）
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['default']['namespace'] = 'Project\Core\Controller';

$settings['url']['baseUri']       = '/';
$settings['url']['staticBaseUri'] = '/assets/';
$settings['url']['basePath']      = '/';

$settings['listeners']['application']['name']  = 'application';
$settings['listeners']['application']['items'] = [APP_TYPE.'Listener']; //优先级依次降低
if (APP_TIME > 1) {
    foreach ($settings['listeners'] as $key => $value) {
        $settings['listeners'][$key]['items'][] = 'DebugListener';
    }
}

$settings['logs']['stream'] = [];
$settings['logs']['stream']['cache'] = BASE_PATH.'/var/log/'.APP_NAME.'/cache.'.date('Ym').'.log';
$settings['logs']['stream']['rpc'] = BASE_PATH.'/var/log/'.APP_NAME.'/rpc.'.date('Ym').'.log';

return $settings;

?>