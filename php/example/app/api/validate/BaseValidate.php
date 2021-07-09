<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2021/7/8
 * Time: 17:49
 */

namespace app\api\validate;

use lib\exception\ParameterException;
use think\facade\Request;
use think\Validate;

class BaseValidate extends Validate
{
    public function goCheck()
    {
        //获取http传入的参数
        //对这些参数做校验
        $request = Request::instance();
        $params = $request->param();

        $result = $this->batch(true)->check($params);
        if(!$result){
            $e = new ParameterException([
                'msg' => is_array($this->error) ? implode(
                    ';', $this->error) : $this->error,
            ]);
            throw $e;
        }
        else{
            return true;
        }
    }





}