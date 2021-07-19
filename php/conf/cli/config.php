<?php
/**
 * 应用配置
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$settings = [];

$settings['version'] = '1.0';
$settings['modules']['scaffold']['className'] = 'Project\Scaffold\Module';

$settings['default']['module']    = 'scaffold';
$settings['default']['namespace'] = 'Project\Scaffold\Task';

$settings['structure']['degree'] = 1;
$settings['structure']['length'] = 8;
$settings['structure']['binDir'] = BASE_PATH.'/bin/';
$settings['structure']['webDir'] = BASE_PATH.'/pub/';
$settings['structure']['cofDir'] = BASE_PATH.'/conf/';
$settings['structure']['logDir'] = BASE_PATH.'/var/log/';
$settings['structure']['tplDir'] = BASE_PATH.'/var/data/tpl/';
$settings['structure']['arcDir'] = APP_PATH.'/Core/Component/.e.g/src/';
$settings['structure']['mvcDir'] = ['View', 'Controller', 'Listener'];
$settings['structure']['dddDir'] = ['Domain', 'Service', 'Listener'];

return $settings

?>