<?php
/**
 * Console 业务逻辑服务
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

include_once(\dirname(__DIR__).'/CommonServices.php');
include_once(BASE_PATH.'/conf/'.APP_NAME.'/services.php');

$di->setShared('loader', new Phalcon\Loader());

$dispatcher = new Phalcon\Cli\Dispatcher();
$dispatcher->setDefaultNamespace($di->get('config')->path('default.namespace'));
$di->setShared('dispatcher', $dispatcher);

$di->setShared('router', new Phalcon\Cli\Router());


//可变参数服务（一般为动态实例化服务）（其他尽量共享化）
//...

return $services;

?>