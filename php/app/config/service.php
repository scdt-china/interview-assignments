<?php
/**
 * Created by XHL
 *
 * @author: justinzhang@leadscloud.com
 * @since: 2020/1/8 10:13
 */

use Phalcon\DI\FactoryDefault as Factory;
use Phalcon\Url;
use Phalcon\Mvc\Dispatcher;
use Phalcon\Mvc\Router;
use Phalcon\Mvc\View;
use Phalcon\Db\Adapter\Pdo\Mysql as DbAdapter;
$di = new Factory();

/** 配置信息注入 */
$di->set('config', function () use ($config) {
    return $config;
}, true);

/** URL功能注入 */
$di->set('url', function () use ($config) {
    $url = new Url();
    $url->setBaseUri($config->baseUri);
    return $url;
}, true);

/** 调度器注入（注册调度事件响应处理） */
$di->set('dispatcher', function () use ($di) {
    $dispatcher = new Dispatcher();
    return $dispatcher;
}, true);

/* 注册路由策略 */
$di->set('router', function () {
    $router = new Router();
    $router->add("/api/register", array(
        'controller' => 'reg',
        'action'     => 'ajaxRegByUsername',
        'subject' => 1
    ));
    return $router;
}, true);

/** 视图模板引擎注入 */
$di->set('view', function () use ($config) {
    $view = new View();
    $view->setViewsDir($config->path->views);
    $view->registerEngines(array(
        '.phtml' => 'Phalcon\Mvc\View\Engine\Php'
    ));
    return $view;
}, true);


/** session 注入 */
$di->set('session', function (){
    //暂时使用空适配器，不能做任何调用
    $session = new \Phalcon\Session\Manager();
    $session->setAdapter(new \Phalcon\Session\Adapter\Noop());
    return $session;
}, true);

/** crypt 注入 */
$di->set('crypt', function (){
    $crypt = new \Phalcon\Crypt();
    $crypt->setKey("8OHD4N6ZEm#EqWv5jU5pU?61P+3FjPZenACE?goJYoA=");
    return $crypt;
}, true);

/** cookies 注入 */
$di->set('cookies', function (){
    $cookies = new \Phalcon\Http\Response\Cookies();
    $cookies->setSignKey("vA+pE&W\/x?m+fTR3wzP6IbqnnfDgoSlTQA4PBoq9bmk\/LG99y0Jt1Q==");
    //引用时，是否使用加密最好从新设置
    $cookies->useEncryption(true);
    return $cookies;
}, true);


/* 注册数据库链接 */
$di->set('dbCommon', function () use ($di, $config) {
    $connection = new DbAdapter(array(
        'host' => $config->db_common->host,
        'username' => $config->db_common->username,
        'password' => $config->db_common->password,
        'dbname' => $config->db_common->dbname,
    ));
    return $connection;
}, true);
