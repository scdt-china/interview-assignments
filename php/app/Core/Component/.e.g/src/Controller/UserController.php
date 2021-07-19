<?php
declare(strict_types=1);

namespace Project\Demo\Controller;

use Project\Demo\Validation\Register as RegisterValidation;
use Project\Core\Model\User;

class UserController extends CommonController
{
    public function indexAction()
    {
        return 'Hello World';
    }

    public function registerAction()
    {
        if ($this->request->isPost()) {
            $result = [];

            if (!$this->security->checkToken()) {
                $result['status'] = 0;
                $result['msg'] = '无效提交';
                return \json_encode($result);
            }
            
            $validator = new RegisterValidation();
            $messages = $validator->validate($_POST);
            if (count($messages)) {
                $result['status'] = 0;
                foreach ($messages as $message) {
                    $result['msg'][$message->getField()] = $message->getMessage();
                }
                return \json_encode($result);
            }
            $_POST['registerTime'] = $_POST['modifyTime'] = \time();

            $user = new User();
            $user->assign($_POST, [
                'username',
                'password',
                'registerTime',
                'modifyTime'
            ]);

            if (!$user->create()) {
                $result['status'] = 0;
                if (count($messages = $user->getMessages())) {
                    foreach ($messages as $message) {
                        $result['msg'][$message->getField()] = $message->getMessage();
                    }
                } else {
                    $result['msg'] = '注册失败';
                }
            } else {
                $result['status'] = 1;
                $result['msg'] = '注册成功';
            }

            return json_encode($result);
        }
    }

    public function loginAction()
    {
        if ($this->request->isPost()) {
            //
        }
    }

    public function logoutAction()
    {
        $this->session->destroy();
        return false;
    }
}
?>