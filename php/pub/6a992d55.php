<?php
/**
 * 唯一入口文件
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

extension_loaded('yaconf') || exit('Yaconf is not found!');
extension_loaded('phalcon') || exit('Phalcon is not found!');

define('APP_NAME', $_GET['_site'] ?? ($argv[1] ?? 'demo'));
Yaconf::has('project.'.APP_NAME) || exit('The '.APP_NAME.'\'s settings are not exists!');

define('BASE_PATH', dirname(__DIR__));
define('APP_PATH', BASE_PATH . '/app');
define('APP_TYPE', ucfirst(strtolower(Yaconf::get('project.'.APP_NAME.'.type')))); // 应用类型：Console、Single、Multiple、Micro
define('APP_MODE', strtolower(Yaconf::get('project.'.APP_NAME.'.mode')));          // 架构模式：cli、overall、alone、service
define('APP_BOOT', Yaconf::get('project.'.APP_NAME.'.boot'));                      // 引导类型：devServer、webServer、workServer、none
define('APP_TIME', intval(Yaconf::get('project.'.APP_NAME.'.env') ?? Yaconf::get('project.common.env'))); // 环境：2（开发）、1（测试）、0（生产）
APP_TIME && define('START_TIME', microtime(true));

$loader = include(BASE_PATH.'/vendor/autoload.php');
$namespace = array_merge(Yaconf::get('project.common.namespace'), Yaconf::get('project.'.APP_NAME.'.namespace') ?? []);
foreach ($namespace as $name => $path) {
    $loader->setPsr4("$name\\", $path);
}
$loader->register(true);

$bootClass = 'Project\Shown\\'.APP_TYPE.'\Bootstrap';
switch (APP_BOOT) {
    case 'devServer':
        $uri = urldecode(parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH));
        if ($uri !== '/' && file_exists(__DIR__ . $uri)) {
            return false;
        }
        $_GET['_url'] = $_SERVER['REQUEST_URI'];
        $bootstrap = new $bootClass();
        break;
    case 'webServer':
        $yac = new Yac(Yaconf::get('project.'.APP_NAME.'.cache.prefix'));
        $bootstrap = $yac->get('bootstrap');
        if (!$bootstrap) {
            $bootstrap = new $bootClass();
            $yac->set('bootstrap', $bootstrap);
        }
        break;
    case 'workServer':
        $bootstrap = new $bootClass();
        break;
}

$bootstrap->run();

?>