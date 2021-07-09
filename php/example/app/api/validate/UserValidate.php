<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2021/7/8
 * Time: 17:25
 */

namespace app\api\validate;


class UserValidate extends BaseValidate
{
    protected $rule = [
        'username'  =>  'require|checkUserName',
        'password' => 'require|checkPassWord',
    ];

    protected $message = [
        'username.require' => '用户名必填',
        'password.require' => '密码必填',
    ];

    protected function checkUserName($value)
    {
        $preg='/^[a-zA-Z_]([a-zA-Z0-9_]+)?$/';
        $result = preg_match($preg, $value);
        if ($result) {
            return true;
        } else {
            return "用户名只能以英文字母或者下划线开头，只能包含英文字母，下划线或数字";
        }
    }

    /**
     * @param $value
     * @return bool|string
     * 1.长度在6位以上
     * 2.不能含有3位以上的连续数字
     * 3.必须有大写字母，小写字母或数字中的两项
     */
    protected function checkPassWord($value)
    {
        $replace_1 = '/\d((?<=0)1|(?<=1)2|(?<=2)3|(?<=3)4|(?<=4)5|(?<=5)6|(?<=6)7|(?<=7)8|(?<=8)9){2}/';
        $replace_2 = '/^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[a-zA-Z0-9]{6,30}$/';
        $result_1 = preg_match($replace_1, $value);
        $result_2 = preg_match($replace_2, $value);
        if (empty($result_1) && $result_2) {
            return true;
        } else {
            return "长度在6位以上，不能含有3位以上的连续数字，必须有大写字母，小写字母或数字中的两项";
        }
    }

}