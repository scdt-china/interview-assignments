<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2021/7/8
 * Time: 15:47
 */

namespace app\api\controller;

use app\api\service\UserService;
use app\api\validate\UserValidate;
use think\facade\Request;

class User
{
    /**
     * 用户注册
     */
    public function register()
    {
        if(Request::isPost()) {
            $username = input('username');
            $password = input('password');
            $repeat_password = input('repeat_password');
            (new UserValidate())->goCheck();
            $userService = new UserService();
            $status = $userService->registeredUsers($username, $password, $repeat_password);
            return $status;
        }
    }

}