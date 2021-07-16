<?php

class Users extends ModelBase
{
    public function initialize()
    {
        $this->setConnectionService("dbCommon"); // 读取动态配置

        //跳过字段
        $this->skipAttributes([
            'update_time'
        ]);

        //对应数据表
        $this->setSource('users');
    }
}
