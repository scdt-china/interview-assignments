<?php
/**
 * Single 事件监听器
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Single;

use Phalcon\Events\Event;
use Phalcon\Mvc\Application;
use Phalcon\Mvc\Dispatcher;
use Project\Shown\CommonListener;

class Listener extends CommonListener
{
    protected $session = [];
    protected $stamp = [];

    public function boot(Event $event, Application $application)
    {
        if (!$this->sessionStart($application)) {
            return false;
        }

        return true;
    }

    public function beforeStartModule(Event $event, Application $application, $module)
    {
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
        $result      = true;
        $annotations = $application->annotations;
        $controller  = $dispatcher->getControllerClass();
        $action      = $dispatcher->getActionName().'Action';
        if ($annotations->getMethod($controller, $action)->has('Auth')) {
            $tactics = $annotations->getMethod($controller, $action)->get('Auth')->getArguments();
            if ($tactics[0]) {
                foreach ($tactics[0] as $type => $settings) {
                    if (\method_exists($this, $method = \lcfirst($type).'Auth')) {
                        if (!($result = $this->$method($application, $dispatcher, $settings))) break;
                    }
                }
            }
        }
        return $result;
    }

    public function afterHandleRequest(Event $event, Application $application, $data)
    {
        //\header("content-type:text/html;charset=utf-8");
        //$application->response->setHeader('Content-Type', 'text/html');
    }
}

?>