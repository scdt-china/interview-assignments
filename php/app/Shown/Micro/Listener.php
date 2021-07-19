<?php
/**
 * Micro 事件监听器
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Micro;

use Phalcon\Events\Event;
use Phalcon\Mvc\Micro;
use Project\Shown\CommonListener;

class Listener extends CommonListener
{
    protected $session = [];
    protected $stamp = [];

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
    public function beforeHandleRoute(Event $event, Micro $application, $data)
    {
        //if ($application->request->isPost()) {
            return $this->sessionStart($application);
        //}

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
        //echo 'here';

       // return true;
    }

    public function beforeExecuteRoute(Event $event, Micro $application)
    {
        //if (!$this->sessionStart($application)) {
        //    return false;
        //}

        return true;
    }

    public function afterExecuteRoute(Event $event, Micro $application)
    {
        return true;
    }

    public function afterHandleRoute(Event $event, Micro $application, $return)
    {
        return true;
    }
}
?>