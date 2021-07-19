<?php
/**
 * 通用服务
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$di->setShared('config', new Phalcon\Config\Adapter\Grouped([
    ['adapter' => 'php', 'filePath' => __DIR__.'/CommonConfig.php'],
    ['adapter' => 'php', 'filePath' => __DIR__.'/'.APP_TYPE.'/Config.php'],
    ['adapter' => 'php', 'filePath' => BASE_PATH.'/conf/'.APP_NAME.'/config.php'],
    ['adapter' => 'php', 'filePath' => BASE_PATH.'/conf/'.APP_NAME.'/develop.php']
]));

if ($adapters = $di->get('config')->path('logs.stream')->toArray()) {
    foreach ($adapters as $name => $file) {
        $adapters[$name] = new Phalcon\Logger\Adapter\Stream($file);
    }
    $di->setShared('logger', new Phalcon\Logger('product', $adapters));
}

$di->setShared('cache', new Phalcon\Cache\CacheFactory(
    $storage = new Phalcon\Cache\AdapterFactory(
        new Phalcon\Storage\SerializerFactory(),
        ['memcache' => "Project\\Library\\Cache\\Memcache"]
    )
));

//$di->setShared('yac', $yac);

$di->setShared('escaper', new Phalcon\Escaper());
$di->setShared('security', (new Phalcon\Security())->setWorkFactor(9));
$di->setShared('crypt', (new Phalcon\Crypt())->setKey('ro8JzrxrXUfF4Pj3'));

$serviceClass = 'Project\Shown\\'.APP_TYPE.'\Listener';
$di->setShared(APP_TYPE.'Listener', new $serviceClass());
if (APP_TIME > 1) {
    $serviceClass = 'Project\Shown\\'.APP_TYPE.'\Debug';
    $di->setShared('DebugListener', new $serviceClass());
}

?>