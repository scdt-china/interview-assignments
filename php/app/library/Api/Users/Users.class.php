<?php
use \Users as UesersModel;
USE \Common\Format;
/**
 * 用户接口
 *
 */
namespace Api\Users;

class Users extends  \LibraryBase {



    /**
     * 使用name得到用户信息
     *
     * @param string $name
     * @return mixed 正确返回信息数组，错误反复空
     */
    public static function getInfoByUsername($name = '') {

        // 查找用户
        $para ['conditions'] = "username = :username:";
        $para ['bind'] = array (
                'username' => $name
        );
        $user = UesersModel::findFirst ( $para );
        if (empty ( $user )) {
            return false;
        }
        return $user->toArray ();
    }


    /**
     * 新增一个的用户
     * @param string $username
     * @param string $password
     * @return array
     */
    public static function createUser($username='', $password='') {
        try {
            // 开启事务
            $dbUser = \Phalcon\DI::getDefault ()->getDbCommon ();
            $dbUser->begin ();

            // 实例化用户表
            $userObj = new UesersModel();
            // 用户名不区分大小写
            $userObj->username = strtolower ( $username );
            //密码加密
            $userObj->password =  Format::generatePw($password);
            //创建时间
            $userObj->create_time = date('Y-m-d H:i:s');
            if(!$userObj->save()){
                // 事务回滚
                $dbUser->rollback ();

                //判断用户名是否重复
                $isExist = false;
                foreach($userObj->getErrorMessages as $msg){
                    if(stripos($msg, 'exist') && stripos($msg, 'username')){
                        $isExist = true;
                        break;
                    }
                }
                if($isExist){
                    throw new \Exception(null, -15030);
                }else{
                    throw new \Exception(null, -15031);
                }

            }

            // 事务提交
            $dbUser->commit ();
            // 用户id
            $data ['id'] = $userObj->id;
            return parent::success( $data );

        } catch ( \Exception $e ) {
            return parent::error($e->getCode());
        }
    }
}