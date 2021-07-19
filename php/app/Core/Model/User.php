<?php 
declare(strict_types=1);

namespace Project\Core\Model;

use Phalcon\Mvc\Model;
use Phalcon\Validation;
use Phalcon\Validation\Validator\Uniqueness;

class User extends Model
{
    protected $id;
    protected $username;
    protected $password;
    protected $registerTime;
    protected $modifyTime;

    public function onConstruct()
    {
        $this->setSource('pre_user');
    }

    public function validation()
    {
        $validator = new Validation();
        $validator->add('username', new Uniqueness([
             'message'      => '该用户名已被占用',
             'cancelOnFail' => true
        ]));
        return $this->validate($validator);
    }

    public function getId(): int
    {
        return (int) $this->id;
    }
    
    public function getUsername(): string
    {
        return (string) $this->username;
    }
    
    public function getPassword(): string
    {
        return (string) $this->password;
    }

    public function getRegisterTime(): int
    {
        return (int) $this->registerTime;
    }

    public function getModifyTime(): int
    {
        return (int) $this->modifyTime;
    }

    public function setUsername(string $username): User
    {
        $this->username = $username;
        
        return $this;
    }

    public function setPassword(string $password): User
    {
        $this->password = $this->getDi()->getShared('security')->hash($password);
        
        return $this;
    }

    public function setRegisterTime(int $registerTime): User
    {
        $this->registerTime = $registerTime;
        
        return $this;
    }

    public function setModifyTime(int $modifyTime): User
    {
        $this->modifyTime = $modifyTime;
        
        return $this;
    }
}
?>