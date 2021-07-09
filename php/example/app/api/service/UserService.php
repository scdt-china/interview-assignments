<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2021/7/8
 * Time: 21:52
 */

namespace app\api\service;


class UserService
{
    public function __construct()
    {

    }

    /**
     *注册用户
     */
    public function registeredUsers($username, $password, $repeat_password)
    {
        if($password != $repeat_password) {
            return json(['status' => -1, 'msg' => '两次输入密码不一致']);
        }
        $user = new \app\api\model\User();
        $resData = $user->getRow($username);
        if($resData) {
            return json(['status' => -1, 'msg' => '用户名已经存在']);
        }
        $postData = ['username' => $username, 'password' => encrypt($password)];
        $result = $user->addUserItem($postData);
        if($result) {
            return json(['status' => 0, 'msg' => '注册成功']);
        } else {
            return json(['status' => 1, 'msg' => '注册失败！']);
        }
    }


}