<?php
/**
 * Created by XHL
 *
 * @author: justinzhang@leadscloud.com
 * @since: 2020/1/8 10:08
 */

header("Content-Type: text/html; charset=UTF-8");
date_default_timezone_set("PRC");

error_reporting(E_ALL);
ini_set('display_errors', 'On');

defined("ROOT_PATH") || define("ROOT_PATH", realpath((dirname(__DIR__))));
defined("APP_PATH") || define("APP_PATH", ROOT_PATH . '/app');

try {
    $config = include APP_PATH . "/config/config.php";
    include APP_PATH . "/config/loader.php";
    include APP_PATH . "/config/service.php";

    \Ns\App::InitProject();

    $application = new \Phalcon\Mvc\Application();
    $application->setDI($di);
    $response = $application->handle($_SERVER["REQUEST_URI"]);
    $response->send();
} catch (Exception $e) {
    $file = str_replace(ROOT_PATH, '', $e->getFile());
    $error_message = '['.$file.':'.$e->getLine().']'.$e->getMessage();
    echo $error_message;
    exit();
}
