<?php
/**
 * 抽象 Bootstrap 类
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown;

use Phalcon\Events\Manager as EventsManager;
use Phalcon\Di;

abstract class AbstractBootstrap
{
    protected $eventsManager;
    protected $di;
    protected $app;

    protected function setEventManager()
    {
        $this->eventsManager = new EventsManager();
        $this->eventsManager->enablePriorities(true);
    }

    protected function setDi()
    {
        $di = new Di();
        if ($services = include_once(__DIR__.'/'.APP_TYPE.'/Services.php')) {
            foreach ($services as $name => $definition) {
                $di->set($namae, $definition);
            }
        }
        $this->di = $di;
    }

    abstract protected function setApp();

    abstract public function run();

    protected function setListener()
    {
        if ($listeners = $this->di['config']->get('listeners')->toArray()) {
            foreach ($listeners as $service => $setting) {
                if ($setting['items']) {
                    foreach ($setting['items'] as $key => $listener) {
                        $this->eventsManager->attach($setting['name'], $this->di[$listener], 100 - $key);
                    }
                }
                switch ($service) {
                    //case 'router'      : exit('router'); break;
                    case 'di'          : $this->di->setInternalEventsManager($this->eventsManager); break;
                    case 'application' : $this->app->setEventsManager($this->eventsManager); break;
                    default            : if ($this->di->has($service)) $this->di[$service]->setEventsManager($this->eventsManager); break;
                }
            }
        }
    }
}

?>