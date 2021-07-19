<?php
/**
 * Multiple 调试监听器
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Multiple;

use Phalcon\Events\Event;
use Phalcon\Loader;
use Phalcon\Di;
use Phalcon\Mvc\Application;
use Phalcon\Mvc\Dispatcher;
use Phalcon\Mvc\Router;
use Phalcon\Mvc\Router\Route;

class Debug
{
    protected $session = [];
    protected $stamp = [];

    public function beforeCheckPath(Event $event, Loader $loader)
    {
        //echo 'Hello';
        if (APP_TIME > 1) echo $loader->getCheckedPath() . "<br>";
        return true;
    }

    public function pathFound(Event $event, Loader $loader)
    {
        //
        return true;
    }

    public function afterCheckClass(Event $event, Loader $loader)
    {
        //
        return true;
    }

    public function beforeServiceResolve(Event $event, Di $di, array $data)
    {
        if (APP_TIME > 1) echo 'Di['.$data['name'].']-';
        return true;
    }

    public function afterServiceResolve(Event $event, Di $di, array $data)
    {
        //序列化
        return true;
    }

    public function boot(Event $event, Application $application)
    {
        echo 'boot-';
        return true;
    }

    public function beforeCheckRoutes(Event $event, Router $router)
    {
        echo 'beforeCheckRoutes--';
        return true;
    }

    public function matchedRoute(Event $event, Router $router, Route $route)
    {
        echo 'matchedRoute--';
        return true;
    }

    public function notMatchedRoute(Event $event, Router $router, Route $route)
    {
        echo 'notMatchedRoute--';
        return true;
    }

    /**
     * 顺序 按优先级依次 降低
     * @Auth({
     *      'accredit' : {'sessionKey' : 'userData'},
     *      'repeat'   : {'time' : 5, 'limit' : 5},
     *      'resubmit' : {'time' : 1, 'limit' : 1}
     * })
     * @Cache({
     *      'http' : {'limiter' : 'public', 'expire' : 30},
     *      'file' : {'rule' : 'name-id', 'expire' : 30}
     * })
     */
    public function beforeHandleRequest(Event $event, Application $application, Dispatcher $dispatcher)
    {
        //$result      = true;
        //$annotations = $application->annotations;
        //$controller  = $dispatcher->getControllerClass();
        //$action      = $dispatcher->getActionName().'Action';
        //if ($annotations->getMethod($controller, $action)->has('Auth')) {
        //    $tactics = $annotations->getMethod($controller, $action)->get('Auth')->getArguments();
        //    if ($tactics[0]) {
        //        foreach ($tactics[0] as $type => $settings) {
        //            if (\method_exists($this, $method = \lcfirst($type).'Auth')) {
        //                if (!($result = $this->$method($application, $dispatcher, $settings))) break;
        //            }
        //        }
        //    }
        //}
        //return $result;

        if (APP_TIME > 1) echo 'App1-', '<br />';
        return true;
    }

    public function afterHandleRequest(Event $event, Application $application, $controller)
    {
        if (APP_TIME > 1) echo 'App-', '<br />';
        return true;
    }
}

?>