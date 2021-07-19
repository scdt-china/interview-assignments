<?php
/**
 * Micro 业务逻辑服务
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

include(\dirname(__DIR__).'/CommonServices.php');
include(BASE_PATH.'/conf/'.APP_NAME.'/services.php');

$router = new Phalcon\Mvc\Router(false);
$router->removeExtraSlashes(true);
$di->setShared('router', $router);

$di->setShared('request', new Phalcon\Http\Request());

$response = new Phalcon\Http\Response();
$di->setShared('response', $response);
$stream = new Phalcon\Http\Message\Stream('php://memory', 'wb');
$di->setShared('stream', $stream);

$di->setShared('db', [
    'className' => Phalcon\Db\Adapter\Pdo\Mysql::class,
    'arguments' => [
        [
            'type' => 'parameter',
            'value' => $di->get('config')->get('database')->toArray()
        ]
    ]
]);
$di->setShared('modelsManager', new Phalcon\Mvc\Model\Manager());
$di->setShared('modelsMetadata', new Phalcon\Mvc\Model\Metadata\Memory());

if (APP_MODE == 'overall') {
    $di->setShared('view', new Phalcon\Mvc\View\Simple());
}


//可变参数服务（一般为动态实例化服务）（其他尽量共享化）
//...

return $services;

?>