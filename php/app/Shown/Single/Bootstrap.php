<?php
/**
 * Single Bootstrap
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Single;

use Phalcon\Mvc\Application;
use Project\Shown\AbstractBootstrap;

class Bootstrap extends AbstractBootstrap
{
    public function __construct()
    {
        $this->setEventManager();
        $this->setDi();
        $this->setApp();
    }

    protected function setApp()
    {
        $this->app = new Application($this->di);
        \method_exists($this, APP_MODE) || exit('Don\'t support '.APP_MODE.' mode.');
        \call_user_func([$this, APP_MODE]);
    }

    protected function overall()
    {
        return;
    }

    protected function alone()
    {
        $this->app->useImplicitView(false);
        $this->app->sendHeadersOnHandleRequest(false);
        $this->app->sendCookiesOnHandleRequest(false);
    }

    public function run($args = [])
    {
        $this->setListener();
        \method_exists($this, APP_BOOT) || exit('Don\'t support '.APP_BOOT.' bootstrap.');
        \call_user_func([$this, APP_BOOT]);
    }

    protected function devServer()
    {
        $this->app->handle($_SERVER["REQUEST_URI"])->send();
    }

    protected function webServer()
    {
        $this->app->handle($_SERVER["REQUEST_URI"])->send();
    }

    protected function workServer()
    {
        $worker = new \Spiral\RoadRunner\Http\PSR7Worker(
            \Spiral\RoadRunner\Worker::create(),
            new \Phalcon\Http\Message\ServerRequestFactory(),
            new \Phalcon\Http\Message\StreamFactory(),
            new \Phalcon\Http\Message\UploadedFileFactory()
        );
        while ($request = $worker->waitRequest()) {
            try {
                $worker->respond($this->app->handle($_SERVER["REQUEST_URI"]));
            }
            catch (\Throwable $e) {
                $worker->getWorker()->error((string)$e);
            }
        }
    }
}

?>