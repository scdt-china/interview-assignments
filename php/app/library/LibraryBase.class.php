<?php

/**
 * 类库基类
 * @author: subin@leadscloud.com
 * @Date: 2020-1-9
 */
class LibraryBase
{

    /**
     * 返回信息
     * @param $code
     * @param $msg
     * @param array $data
     * @return array
     */
    protected static function response($code=-1, $msg='', $data = array())
    {
        $response = array(
            'code' => $code,
            'msg' => $msg,
            'data' => $data
        );
        return $response;
    }

    /**
     * 成功的返回
     * @param mixed $data
     * @param string $msg
     * @param int $code
     * @return array
     */
    protected static function success($data=null, $msg = 'success', $code = 200)
    {
        return self::response($code, $msg, $data);
    }

    /**
     * 标准错误的返回
     * @param int $code
     * @param string $msg
     * @param mixed $data
     * @return array
     */
    protected static function error($code = -1, $msg='', $data = null)
    {
       if(empty($code) || !is_numeric($code)){
           $code = -1;
       }
       if(empty($msg)){
           $msg = \ErrorMessage::getError($code);
       }
       return self::response($code, $msg, $data);
    }

    /**
     * 获取默认DI
     * @return \Phalcon\DiInterface
     */
    protected static function getDI()
    {
        return \Phalcon\DI::getDefault();
    }

    /**
     * 获取App配置
     * @param string $key
     * @return object
     */
    public static function getAppConfig($key = null)
    {
        $config = self::getDI()->get('config');

        if (!empty($key) && isset($config[$key])) {
            return $config[$key];
        } else {
            return $config;
        }
    }

    /**
     *  获取di中已注入的服务
     *  @param string $serviceName 参考service.php
     *  @return object
     */
    public static function getServiceInstance($serviceName='')
    {
        $service = self::getDI()->get($serviceName);
        return $service;
    }
}