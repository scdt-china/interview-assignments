<?php
namespace frontend\components;

use Yii;
use yii\web\Controller;

class BaseController extends Controller
{
    public function init() {
        if(empty($_SESSION['user_id']))
        {
            $this->initLogin();
        }
        parent::init();
    }

    // 初始化
    public function initLogin() {
        header("Location:".Yii::$app->request->baseUrl.'/site/register');exit;
    }
}
