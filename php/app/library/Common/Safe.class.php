<?php
/**
 * d登录信息加密
 */

namespace Common;


class Safe extends \LibraryBase
{
    //有效时间,秒
    const EXPIRE = 86400;
    //名称
    const COOKIE_NAME = "site_auth";

    /**
     * 数据结构
     * @var array
     */
    private static $_structure = [
        "userId"=>'',               //用户编号，必填
        "userName"=>'',             //用户名称，必填
        "createTime"=>'',           //创建时间
    ];

    /**
     * 获取对应的数据结构
     * *@return array
     *
     **/
    public static function getStructure(){
        return self::$_structure;
    }

    /**
     *
     *
     * @return string
     */
    private static function _getCookieName(){
        return LEADSCLOUD_ENV.'_'.self::COOKIE_NAME;
    }

    /**
     *
     * @return object
     */
    private static function _getCookiesService(){
        $cookies = parent::getServiceInstance("cookies");
        $cookies->useEncryption(true);
        return $cookies;
    }

    /**
     * 设置
     * @param array $args self::$_structure;
     * @return bool
     **/
    public static function set(array $args=[]){
        //验证参数
        foreach (self::$_structure as $key => $val){
            if(!array_key_exists($key, $args)){
                return false;
            }
        }

        //增加时间
        if(empty($args['createTime'])){
            $args['createTime'] = time();
        }

        $service = self::_getCookiesService();
        $name = self::_getCookieName();


        $service->set($name, json_encode($args), time()+self::EXPIRE);

        return $service->send();
    }

    /**
     * 获取
     *
     * @return  false | self::$_structure
     */
    public static function get(){
        $service = self::_getCookiesService();
        $name = self::_getCookieName();
        if(!$service->has($name)){
            return false;
        }
        $data = trim($service->get($name));
        if(empty($data)){
            return false;
        }
        $data = json_decode($data, true);
        if(empty($data)){
            return false;
        }
        //判断是否超时
        if(($data['createTime']+self::EXPIRE) <  time()){
            return false;
        }

        return $data;
    }
}