<?php
declare(strict_types=1);

namespace Project\Demo\Controller;

class IndexController extends CommonController
{
    public function indexAction()
    {
        $this->response->redirect('user/register');
    }
}
?>