<?php
declare(strict_types=1);

namespace Project\Demo\Controller;

use Phalcon\Mvc\View;

class CommonController extends BaseController
{
    public function beforeExecuteRoute($dispatcher)
    {
        $this->view->setViewsDir(dirname(__DIR__).'\\View');
        $this->view->setVar('pageTitle', 'Demo');
        $this->view->setVar('staticUrl', $this->url->getStaticBaseUri());
        $this->view->disableLevel(View::LEVEL_LAYOUT);
    }
}
?>