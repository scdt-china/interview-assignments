<?php
declare(strict_types=1);

namespace Project\Demo\Controller;

use Phalcon\Mvc\Controller;

class BaseController extends Controller
{
    protected function distribute(array $methods, $synHandle)
    {
        //
    }

    public function asynHandle($asynResult, $asynCall)
    {
        //
    }

    public function asynError($type, $asyncError, $asyncCall)
    {
        //
    }
}
?>