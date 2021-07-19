<?php
/**
 * Console Bootstrap
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Console;

use Phalcon\Cli\Console;
use Project\Shown\AbstractBootstrap;

class Bootstrap extends AbstractBootstrap
{
    public function __construct()
    {
        $this->setEventManager();
        $this->setDi();
        $this->setApp();
    }

    protected function setApp(){
        $this->app = new Console($this->di);
        $this->app->registerModules($this->di['config']->get('modules')->toArray());
        $this->app->setDefaultModule($this->di['config']->path('default.module'));
    }

    public function run($args = [])
    {
        $this->setListener();
        \call_user_func([$this, APP_BOOT], $args);
    }

    protected function none($args)
    {
        $this->app->handle($args);
    }
}

?>