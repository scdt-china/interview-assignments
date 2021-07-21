<?php
/**
 * 用户名建表语句
 *
      CREATE TABLE `users` (
     `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
     `username` char(50) NOT NULL DEFAULT '' COMMENT '用户名',
     `password` char(50) NOT NULL DEFAULT '' COMMENT '用户密码',
      PRIMARY KEY (`id`),
      KEY `idx_username` (`username`)
      ) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户信息表';

 * Class Index
 */

class Index extends CI_Controller{

      public function __construct()
      {
         parent::__construct();
      }

    /**
     * 登陆注册页面
     */
      public function indexs()
	  {
          $this->load->view('views.tpl.php');
      }

    /**
     * 登陆注册提交接口
     */
      public function register()
      {
          $name = addslashes($_GET['name']);
          $pw1  = addslashes($_GET['pw1']);
          $pw2  = addslashes($_GET['pw2']);

          //校验用户名是否为空
          if (empty($name)) {
              $this->prompt(3);
          } else if (!preg_match('/^[a-zA-Z_][a-zA-Z0-9_]*$/',$name)){
              $this->prompt(4);
          }

          //验证密码是否为空
          if (empty($pw1) || empty($pw2)) {
              $this->prompt(5);
          }

          //验证密码1和密码2是否是否一致
          if ($pw1 != $pw2) {
              $this->prompt(2);
          }

          //验证密码是否符合规则
          if (!preg_match('/(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{6,}/',$pw1)) {
              $this->prompt(6);
          }

          if (preg_match('/\d{3,}/',$pw1)) {
              $this->prompt(7);
          }

          $password = md5($pw1);
          //查询是否有当前用户
          $userInfo = $this->userSelect($name, $password);

          //如果有当前用户名, 对比密码是否一致
          if ($userInfo['password'] != $password) {
              $this->prompt(8);
          } else {
              /*打开会话，将用户名和id存起来*/
              session_start();
              $_SESSION['username']=$name;
              $_SESSION['id']=$userInfo['id'];
              $this->prompt(1);
          }

          //若没有当前用户, 走注册逻辑
          if (!empty($userInfo)) {
              $add = $this->add($name, $password);
          }
          if ($add) {
              /*打开会话，将用户名和id存起来*/
              session_start();
              $_SESSION['username'] = $name;
              $_SESSION['id']       = $userInfo['id'];
              $this->prompt(1);
          } else {
              $this->prompt(9);
          }
      }

      /**
       * 校验提交
       */
      public function prompt($state)
      {
          $arr = [
              '1' => [
                  'code' => 1,
                  'msg'  => '成功',
              ],
              '2' => [
                  'code' => 2,
                  'msg'  => '2次密码输入不一致,请重新输入',
              ],
              '3' => [
                  'code' => 3,
                  'msg'  => '用户名不能为空',
              ],
              '4' => [
                  'code' => 4,
                  'msg'  => '用户名格式不正确,以英文字母或下划线开头 ',
              ],
              '5' => [
                  'code' => 5,
                  'msg'  => '密码不能为空',
              ],
              '6' => [
                  'code' => 6,
                  'msg'  => '密码不符合规则,长度在6位以上,必须有大写字母，小写字母和数字',
              ],
              '7' => [
                  'code' => 7,
                  'msg'  => '密码不符合规则,不能含有3位以上的连续数字',
              ],
              '8' => [
                  'code' => 8,
                  'msg'  => '密码不对, 请重新输入',
              ],
              '9' => [
                  'code' => 9,
                  'msg'  => '失败',
              ],
          ];

          echo json_encode($arr[$state]);
          exit;
      }

      /**
       * 根据用户名查询是否有当前用户名的记录
       */
      public function userSelect($username, $password)
      {
          $link=new PDO("mysql:host=localhost;port=3306;dbname=user","root","");
          if (!$link)
          {
              die('Could not connect: ' . mysql_error());
          }
          $sql = "SELECT id, password FROM user where username = {$username}";
          $info = mysql_query($sql,$link);
          return $info;
      }

      /**
       * 注册用户
       */
      public function add($username, $password)
      {
          $link=new PDO("mysql:host=localhost;port=3306;dbname=user","root","");
          if (!$link)
          {
              die('Could not connect: ' . mysql_error());
          }
          $row = $link->exec("insert into `user`( `username`,  `password`) values ('$username','$password')");
          return $row;
      }



    
}