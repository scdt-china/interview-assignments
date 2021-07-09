<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2021/7/8
 * Time: 20:46
 */

namespace app\api\model;


use think\facade\Db;
use think\Model;

class User extends Model
{
    /**
     * 主键
     * @var string
     */
    protected $pk = 'user_id';

    /**
     * 是否需要自动写入时间戳
     * @var bool
     */
    protected $autoWriteTimestamp = true;

    public function addUserItem(array $data)
    {
        return self::save($data);
    }

    public function getRow($value)
    {
        return Db::name('user')->where(['username' => $value])->find();
    }

}