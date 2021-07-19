<?php
/**
 * Multiple 业务逻辑服务
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

include(\dirname(__DIR__).'/CommonServices.php');
require(BASE_PATH.'/conf/'.APP_NAME.'/services.php');

$di->setShared('loader', new Phalcon\Loader());

$di->setShared('annotations', (new Phalcon\Annotations\AnnotationsFactory())->load(
    $di['config']->get('annotations')->toArray()[APP_TIME ? 'memory' : 'apcu']
));

$dispatcher = new Phalcon\Mvc\Dispatcher();
$dispatcher->setDefaultNamespace($di['config']->path('default.namespace'));
$di->setShared('dispatcher', $dispatcher);

$url = new Phalcon\Url();
//$url->setBaseUri($di->get('config')->path('url.baseUri'));
$url->setStaticBaseUri($di['config']->path('url.staticBaseUri'));
//$rul->setBasePath($di->get('config')->path('url.basePath'));
$di->setShared('url', $url);

$di->setShared('request', new Phalcon\Http\Request());
$di->setShared('response', new Phalcon\Http\Response());

$di->setShared('db', [
    'className' => Phalcon\Db\Adapter\Pdo\Mysql::class,
    'arguments' => [[
        'type'  => 'parameter',
        'value' => $di->get('config')->get('database')->toArray()
    ]]
]);
$di->setShared('modelsManager', new Phalcon\Mvc\Model\Manager());
$di->setShared('modelsMetadata', new Phalcon\Mvc\Model\Metadata\Memory());

$di->setShared('view', new Phalcon\Mvc\View());

$di->setShared('filter', (new Phalcon\Filter\FilterFactory())->newInstance());

if ($di['config']->path('session.mode.side') == 'server') {
    $di->setShared('storage', $storage);
    $di->setShared('session', new Phalcon\Session\Manager($di['config']->path('session.manage')->toArray()));
}
if (APP_MODE == 'overall' && $di['config']->path('session.mode.side') == 'client' || $di['config']->path('session.mode.transfer') == 'cookie') {
    $di->setShared('cookies', new Phalcon\Http\Response\Cookies());
}

//可变参数服务（一般为动态实例化服务）（其他尽量共享化）
//...

return $services;

?>