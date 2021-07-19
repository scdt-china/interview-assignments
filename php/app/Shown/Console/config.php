<?php
/**
 * Console 配置（默认）
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['logs']['stream'] = [];
$settings['logs']['stream']['cache'] = BASE_PATH.'/var/log/'.APP_NAME.'/cache.'.date('Ym').'.log';

return $settings

?>