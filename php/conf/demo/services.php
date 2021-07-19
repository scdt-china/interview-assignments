<?php
/**
 * 应用服务
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

$router = new Phalcon\Mvc\Router();
$router->removeExtraSlashes(true);
$router->setDefaults([
    'controller' => 'index',
    'action' => 'index'
]);
if ($routes = include('routes.php')) {
    foreach ($routes as $route => $definition) {
        $router->add($route, $definition);
    }
}
$di->setShared('router', $router);


//可变参数服务（一般为动态实例化服务）（其他尽量共享化）
$services = [];

?>