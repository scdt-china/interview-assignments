<?php
/**
 * Micro Bootstrap
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown\Micro;

use Phalcon\Mvc\Micro;
use Phalcon\Mvc\Micro\Collection as MicroCollection;
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
        $this->app = $application = new Micro($this->di);
        $routes = include(BASE_PATH.'/conf/'.APP_NAME.'/routes.php');
        foreach ($routes as $collection) {
            $router = new MicroCollection();
            $router->setHandler($collection['handler']);
            \is_string($collection['handler']) && $router->setLazy(true);
            $collection['prefix'] && $router->setPrefix($collection['prefix']);
            foreach ($collection['assign'] as $method => $defines) {
                foreach ($defines as $define) {
                    $router->$method($define[0], $define[1]);
                }
            }
            $this->app->mount($router);
        }
        //$this->app->notFound([$di['apiHandlers'], 'notFound']);
    }

    public function run()
    {
        $this->setListener();
        \call_user_func([$this, APP_BOOT]);
    }

    protected function webServer()
    {
        if (APP_MODE == 'alone') {
            $application = $this->app;
            $this->app->setResponseHandler(function () use ($application) {
                return $application->response->setContentType('application/json')->setJsonContent($application->getReturnedValue())->send();
            });
        }
        $this->app->handle($_SERVER["REQUEST_URI"]);
    }

    protected function workServer()
    {
        $application = $this->app;
        $this->app->setResponseHandler(function () use ($application) {
            $stream = new \Phalcon\Http\Message\Stream('php://memory', 'wb');
            $response = new \Phalcon\Http\Message\Response();
            if (APP_MODE != 'overall') {
                $stream->write(\json_encode($application->getReturnedValue()));
                $response->withHeader('Content-Type', 'application/json');
            } else {
                $stream->write($application->getReturnedValue());
            }
            return $response->withBody($stream)->withStatus(200);
        });

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