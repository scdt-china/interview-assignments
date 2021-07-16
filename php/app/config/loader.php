<?php
/**
 * Created by XHL
 *
 * @author: justinzhang@leadscloud.com
 * @since: 2020/1/8 10:12
 */

use Phalcon\Loader;

$loader = new Loader();
$loader->registerDirs(
    [
        $config->path->controllers,
        $config->path->models,
        $config->path->library,
    ]
);
$loader->setExtensions(['php', 'class.php']);
$loader->register();