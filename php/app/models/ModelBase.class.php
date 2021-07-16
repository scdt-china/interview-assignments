<?php
/**
 *
 * 公共model
 * @author luori <luori2004@dingtalk.com>
 * @date  2020/1/10 18:17
 * @version 1.0.0
 * @copyright Copyright 2020
 */

use \Phalcon\Mvc\Model\Resultset\Simple;
use \Phalcon\Mvc\Model;

class ModelBase extends Model
{
    public static function _doSql( $sql )
    {
        $model = new static();
        if (strtolower(substr(trim($sql), 0, 6)) == 'select') {
            return new Simple(null, $model, $model->getReadConnection()->query($sql));
        } else {
            return $model->getWriteConnection()->execute($sql);
        }
    }
}