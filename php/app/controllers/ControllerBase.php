<?php
/**
 * Created by XHL
 *
 * @author: justinzhang@leadscloud.com
 * @since: 2020/1/8 10:30
 */

use \ErrorMessage as ErrorMessage;
use \Common\Safe;
use \Phalcon\Mvc\Controller;

class ControllerBase extends Controller
{
    /**
     * 响应请求的基本数据结构
     * @var array
     */
    protected $responseData = [
        'code' => 200,
        'msg' => '',
        'data' => null
    ];

    // 用户登录时记录的信息,参考\Common\Safe的$_structure
    protected $userLoginInfo;

    /**
     * 初始化,在initialize()之前
     */
    public function onConstruct()
    {
    }

    /**
     * 初始化
     */
    public function initialize()
    {
        //校验来自客户端口令
        $errCode = self::checkCsrfToken();
        if ($errCode <> ErrorMessage::SUCCESS_CODE) {
            return $errCode;
        }

        //校验ip一段时间内刷新
        $errCode = self::checkCommitRuleIp();
        if ($errCode <> ErrorMessage::SUCCESS_CODE) {
            return $errCode;
        }

        //校验是否登录
        $errCode = self::checkLogin();
        if ($errCode <> ErrorMessage::SUCCESS_CODE) {
            return $errCode;
        }


        return ErrorMessage::SUCCESS_CODE;
    }


    /**
     * 校验是否登录
     * @return int 错误码
     * */
    protected function checkLogin()
    {
        //获取登录名单
        //如果访问接口在名单内且没有登录
        //如果已登录给变量$userLoginInfo赋值
        $this->userLoginInfo = Safe::get();
        if($this->userLoginInfo ===false || !is_array($this->userLoginInfo)){
            return -5;
        }
        else{
            return ErrorMessage::SUCCESS_CODE;
        }
    }


    /**
     * 校验来自客户端口令
     *
     * @return int 错误码
     */
    protected function checkCsrfToken()
    {
        //获取白名单
        //不在白名单内
        //判断提交的token是否正确
        return ErrorMessage::SUCCESS_CODE;
    }


    /**
     * 校验ip一段时间内刷新
     *
     * @return int 错误码
     */
    protected function checkCommitRuleIp()
    {
        //获取白名单
        //不在白名单内
        //判断一段时间内，同一ip，提交次数

        return ErrorMessage::SUCCESS_CODE;
    }


    /**
     * 输出 Json 格式的成功信息
     * @param $data
     * @param string $msg
     * @param int $code
     */
    public function jsonSuccess($data = null, $msg = "", $code = ErrorMessage::SUCCESS_CODE)
    {
        $this->responseData['data'] = $data;
        if ($msg != "") {
            $this->responseData['msg'] = $msg;
        }
        if ($code != 0) {
            $this->responseData['code'] = $code;
        }
        $this->outputJson();
    }

    /**
     * 输出 Json 格式的标准错误信息
     * @param int $code
     * @param string $msg
     * @param null $data
     * @return void
     */
    protected function jsonError($code = -1, $msg = '', $data = null)
    {
        if (empty($code)) {
            $code = -1;
        }
        $this->responseData['code'] = $code;

        if (empty($msg)) {
            $msg = \ErrorMessage::getError($code);
        }
        $this->responseData['msg'] = $msg;

        if ($data != null) {
            $this->responseData['data'] = $data;
        }
        $this->outputJson();
    }


    /**
     * 输出Json格式的返回信息
     * @param null $json
     */
    public function outputJson($json = null)
    {
        if ($json != null && is_array($json)) {
            $this->responseData = $json;
        }
        echo json_encode($this->responseData);
        $this->view->setRenderLevel(\Phalcon\Mvc\View::LEVEL_NO_RENDER);
    }


    /**
     * 获取配置项
     * @param string $key
     * @return mixed
     */
    public function getAppConfig($key = '')
    {
        $array = $this->config->toArray();
        if (!empty(trim($key)) && isset($array[$key])) {
            return $array[$key];
        } else {
            return $array;
        }
    }

    /**
     * head跳转
     *
     * @param string $url
     * @return void
     */
    protected function headGoto($url = '')
    {
        $this->response->redirect($url, true);
        $this->response->send();

    }
}