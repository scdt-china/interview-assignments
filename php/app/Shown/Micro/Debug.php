<?php
/**
 * Micro 调试监听器
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Micro;

use Phalcon\Events\Event;
use Phalcon\Di;
use Phalcon\Mvc\Micro;

class Debug
{
    public function beforeServiceResolve(Event $event, Di $di, array $data)
    {
        echo 'Di['.$data['name'].']-', '<br />';

        return true;
    }

    public function afterServiceResolve(Event $event, Di $di, array $data)
    {
        echo 'Di2['.$data['name'].']-', '<br />';

        return true;
    }

    public function beforeExecuteRoute(Event $event, Micro $application)
    {
        echo 'beforeExecuteRoute-', '<br />';

        return true;
    }

    public function beforeHandleRoute(Event $event, Micro $application, $data)
    {
        echo 'beforeHandleRoute-', '<br />';

        return true;
    }

    public function afterHandleRoute(Event $event, Micro $application, $return)
    {
        echo 'afterHandleRoute-', '<br />';

        return true;
    }
}
?>