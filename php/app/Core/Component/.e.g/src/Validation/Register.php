<?php 
declare(strict_types=1);

namespace Project\Demo\Validation;

use Phalcon\Validation;
use Phalcon\Validation\Validator\PresenceOf;
use Phalcon\Validation\Validator\Regex;
use Phalcon\Validation\Validator\StringLength;
use \Phalcon\Validation\Validator\Callback;
use Phalcon\Validation\Validator\Confirmation;

class Register extends validation
{
    public function initialize()
    {
        $this->setFilters('username', 'trim');
        $this->setFilters('password', 'trim');

        $this->add('username', new PresenceOf([
             'message'      => '用户名不能为空',
             'cancelOnFail' => true
        ]));
        $this->add('username', new Regex([
            'pattern'      => '/^[a-zA-Z_]+[a-zA-Z0-9_]{1,}$/',
            'message'      => '用户名只能由字母、数字或下划线组成，且不能以数字开头'
        ]));
        
        $this->add('password', new PresenceOf([
            'message'      => '密码不能为空',
            'cancelOnFail' => true
        ]));
        $this->add('password', new StringLength([
            "min"          => 6,
            "message"      => '密码最少 6 位',
            'cancelOnFail' => true
        ]));
        $this->add('password', new Regex([
            'pattern'      => '/(?!([0-9_\W]+|[a-z_\W]+|[A-Z_\W]+)$)^[\w\W]{6,}$/',
            'message'      => '密码必须包含大写字母、小写字母或数字中的两项',
            'cancelOnFail' => true
        ]));
        $this->add('password', new Callback([
            'callback'     => function ($data) {
                \preg_match('/(012|123|234|345|456|567|678|789)/', $data['password'], $matches);
                return empty($matches);
            },
            'message'      => '密码不能含有 3 位以上的连续数字',
            'cancelOnFail' => true
        ]));
        $this->add('password', new Confirmation([
            'message' => '两次密码不一致',
            'with'   => 'surePassword'
        ]));
    }
}
?>