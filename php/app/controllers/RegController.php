<?php
use \ErrorMessage as ErrorMessage;
USE \Common\Format;
USE \Common\Safe;
USE \Api\Users\Users;
/**
 * 注册
 * @author subin
 *
 */
class RegController extends ControllerBase
{

    /**
     * 初始化
     */
    public function initialize()
    {
        //调用父级验证
        $errCode = parent::initialize();
        if($errCode <>  ErrorMessage::SUCCESS_CODE){
            return parent::jsonError($errCode);
        }
    }

    /**
     * 用户注册页面
     */
    public function indexAction()
    {
        //得到跳转地址
        $returnUrl = $this->request->get('returnUrl');


        //如果已经登录
        if(!empty($this->userLoginInfo)){
            return parent::headGoto($returnUrl);
        }

        //设置标题
        parent::setPageTitle();
        //设置主view
        $this->view->setMainView('reg');
        //view指向
        $this->view->pick('reg/index');
    }

    
    

   /**
    * ajax 通过用户名注册
    */
   public function ajaxRegByUsernameAction(){
       $this->view->disable();
       try {


           $username = $this->request->getPost("username");
           $pw = $this->request->getPost("password");
           $repw = $this->request->getPost("repeat_password");

           //校验用户名格式
           $errCode = Format::checkUsername($username);
           if($errCode <> ErrorMessage::SUCCESS_CODE){
               throw new \Exception(null, $errCode);
           }

           //校验密码
           $errCode = \Api\Member\UcFormat::checkPw($pw);
           if($errCode <> ErrorMessage::SUCCESS_CODE){
               throw new \Exception(null, $errCode);
           }
           //重复密码
           if($pw != $repw){
               throw new \Exception(null, -15029);
           }

           //得到用户信息
           $user = Users::getInfoByUsername($username);
           if(!empty($user)){
               throw new \Exception(null, -15030);
           }

           //数据保存，密码加密在下一个服务
           $result = Users::createUser($username, $pw);
           if ($result['code'] <> 0) {
               $this->error($result['code']);
           }
           $userId = $result['data']['id'];

           //生成token
           $token = Safe::set(['userId'=>$userId, 'userName'=>$username]);

           //返回给前端
           $data = [];
           $data['token'] = $token;
           $data['url'] = '跳转地址';
           
           return parent::jsonSuccess(['token'=>$token]);
       } catch ( \Exception $e ) {
           return parent::jsonError($e->getCode(), $e->getMessage());
       }
   }

}
