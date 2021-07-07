<?php
namespace frontend\controllers;

use frontend\models\User;
use Yii;
use yii\web\Controller;

class SiteController extends Controller {
    /**
     * @inheritdoc
     */
    public function actions()
    {
        return [
            'error' => ['class' => 'yii\web\ErrorAction'],
        ];
    }

    /**
     * 首页
     */
    public function actionIndex() {
        if (empty($_SESSION['user_id'])) {
            $this->redirect(['site/register']);
        } else {
            echo $_SESSION['user_id'];
            session_destroy();
        }
    }

    /**
     * 注册
     */
    public function actionRegister() {
        if($_POST) {
            $username = trim($_POST['username']);
            $pwd = md5($_POST['pwd']);
            $info = new User();
            $info->username = $username;
            $info->pwd = $pwd;
            $info->add_time = time();
            $info->save();
            $user_id = Yii::$app->db->getLastInsertID();
            $_SESSION['user_id'] = $user_id;
            $this->redirect(Yii::$app->request->baseUrl.'/site/index');
        } else {
            if(!empty($_SESSION['user_id'])) {
                $this->redirect(Yii::$app->request->baseUrl.'/site/index');
            } else {
                return $this->renderPartial('register');
            }
        }
    }

    /**
     * 注册验证
     */
    public function actionVerify() {
        $username = $_POST['username']; //用户名
        $pwd = $_POST['pwd']; //密码
        $repwd = $_POST['repwd']; //重复密码
        if(preg_match("/^[a-zA-Z_]\w*$/",$username) == 0) {
            echo json_encode(array('status'=>300,'message'=>'账号只能以英文字母或下划线开头;只能包含英文字母,下划线或数字'));exit;
        }
        if($pwd != $repwd) {
            echo json_encode(array('status'=>300,'message'=>'两次密码不一致'));exit;
        }
        if(mb_strlen($pwd) < 6) {
            echo json_encode(array('status'=>300,'message'=>'密码长度在6位以上'));exit;
        }
        if(preg_match("/\d{3}/",$pwd) == 1) {
            echo json_encode(array('status'=>300,'message'=>'密码不能含有3位以上的连续数字'));exit;
        }
        $result1 = preg_match("/(?=.*[A-Z])(?=.*[a-z])/", $pwd);
        $result2 = preg_match("/(?=.*[A-Z])(?=.*[0-9])/", $pwd);
        $result3 = preg_match("/(?=.*[a-z])(?=.*[0-9])/", $pwd);
        if($result1 == 0 && $result2 == 0 && $result3 == 0) {
            echo json_encode(array('status'=>300,'message'=>'密码必须有大写字母,小写字母或数字中的两项'));exit;
        }

        $info = User::findOne(['username' => $username]);
        if($info) {
            echo json_encode(array('status'=>300,'message'=>'该用户名已被注册'));exit;
        } else {
            echo json_encode(array('status'=>200));exit;
        }
    }

    /**
     * 退出登录
     */
    public function actionLogout() {
        session_destroy();
        return $this->redirect(Yii::$app->request->baseUrl.'/site/index');
    }
}
