<?php
/**
 * 通用事件监听器
 *
 * @version 1.0
 * @author ZBin
 */
declare(strict_types=1);

namespace Project\Shown;

use Phalcon\Events\Event;
use Phalcon\Loader;
use Phalcon\Di;
use Phalcon\Mvc\Application;
use Phalcon\Mvc\Micro;
use Phalcon\Mvc\Dispatcher;
use Project\Library\Helper;

class CommonListener
{
    protected $session = [];
    protected $stamp = [];

    /**
     * 会话开始
     * @param mixed $application
     * @return mixed
     */
    protected function sessionStart($application)
    {
        $this->session = $application->di['config']->get('session')->toArray();
        $method = $this->session['mode']['origin'].'Origin';
        return \method_exists($this, $method) ? \call_user_func([$this, $method], $application) : true;
    }

    protected function internalOrigin($application)
    {
        if ($application->request->hasHeader('Authorization')) {
            list(, $key) = \explode($this->session['basic']['name'].' ', $application->request->getHeader('Authorization'), 2);
            if ($key && Helper::dzCode($key) == $this->session['mode']['token']) {
                return true;
            }
        }
        $application->response->setStatusCode(403)->send();
        return false;
    }

    protected function externalOrigin($application)
    {
        if ($this->session['mode']['side'] == 'server') {
            \ini_set('session.use_only_cookies', '0');
            \ini_set('session.name', $this->session['basic']['name']);
            \ini_set('session.gc_maxlifetime', (string)$this->session['basic']['gc_maxlifetime']);
            \session_cache_limiter($this->session['basic']['cache_limiter']);
            \session_cache_expire($this->session['basic']['cache_expire']);
        }

        //使用crc32以有效缩短密文长度、strval规避其可能存在的系统兼容性问题
        //使用Url传递会话ID，每次请求都需校验会话ID的有效性，在客户端禁用缓存的情况下，存在性能不高和可重复首次请求的弊端
        //使用Cookie存储会话ID（默认），兼顾安全、性能和体验，当客户端禁用Cookie的情况下可开启Url传递方式
        $this->stamp['flag'] = \strval(\crc32($application->request->getClientAddress().'-'.$application->request->getUserAgent()));

        $method = $this->session['mode']['transfer'].\ucfirst($this->session['mode']['side']);
        return \method_exists($this, $method) ? \call_user_func([$this, $method], $application) : false;
    }

    /**
     * 会话存服务端 cookie 传输
     * @param mixed $application
     * @return bool
     */
    protected function cookieServer($application)
    {
        \ini_set('session.use_trans_sid', '0');
        \ini_set('session.use_cookies', '1');
        \ini_set('session.cookie_lifetime', (string)$this->session['basic']['cookie_lifetime']); //自动过期
        \ini_set('session.cookie_domain', $this->session['basic']['cookie_domain']);
        \ini_set('session.cookie_httponly', (string)$this->session['basic']['cookie_httponly']); //防XSS
        \ini_set('session.cookie_secure', (string)$this->session['basic']['cookie_secure']);
        \ini_set('session.cookie_samesite', $this->session['basic']['cookie_samesite']); //防会话ID被盗用（另一种CSRF）

        $sessionAdapter = $this->session['mode']['adapter'] == 'stream'
        ? new \Phalcon\Session\Adapter\Stream($this->session['stream'])
        : new \Project\Library\SessionFactory($application->di['storage'], $this->session[$this->session['mode']['adapter']]);

        //认证时验证（提升性能）Url或Header 携带的会话Key 的有效性，防CSRF
        if ($application->cookies->has($this->session['basic']['name'])) {
            $application->session->setAdapter($sessionAdapter)->start();
        } else {
            $application->session->setId(
                Helper::dzCode($this->stamp['flag'], 'ENCODE', '', $this->session['basic']['gc_maxlifetime'])
            )->setAdapter($sessionAdapter)->start();
        }

        return true;
    }

    /**
     * 会话存客户端 cookie 传输
     * @param mixed $application
     * @return bool
     */
    protected function cookieClient($application)
    {
        if (!$application->cookies->has($this->session['basic']['name'])) {
            $application->cookies->set(
                $this->session['basic']['name'],
                Helper::dzCode(\json_encode($this->stamp), 'ENCODE', '', $this->session['basic']['gc_maxlifetime']),
                $this->session['basic']['cookie_lifetime'] + \time(),
                '/',
                $this->session['basic']['cookie_secure'],
                $this->session['basic']['cookie_domain'],
                $this->session['basic']['cookie_httponly'],
                ['samesite' => 'Strict']
            );
            return false;
        }

        return true;
    }

    /**
     * 会话存服务端 url 传输
     * @param mixed $application
     * @return bool
     */
    protected function urlServer($application)
    {
        \ini_set('session.use_cookies', 0);
        \ini_set('session.use_trans_sid', 1);
        \ini_set('session.trans_sid_hosts', '');
        \ini_set('url_rewriter.tags', 'a=href,area=href,frame=src,input=src,form=');
        \ini_set('url_rewriter.hosts', $this->session['basic']['trans_sid_hosts']);

        $sessionAdapter = $this->session['mode']['adapter'] == 'stream'
        ? new \Phalcon\Session\Adapter\Stream($this->session['stream'])
        : new \Project\Library\SessionFactory($application->di['storage'], $this->session[$this->session['mode']['adapter']]);

        //通过Key时间戳，实现自动过期
        //验证Key的有效期和有效性，实现自动过期，防止会话ID注入和被盗用
        if ($application->request->hasQuery($this->session['basic']['name'])) {
            $flag = Helper::dzCode($application->request->getQuery($this->session['basic']['name']));
            if ($flag && $flag == $this->stamp['flag']) {
                $application->session->setAdapter($sessionAdapter)->start();
                return true;
            }
        }
        $application->session->setId(
            $key = Helper::dzCode($this->stamp['flag'], 'ENCODE', '', $this->session['basic']['gc_maxlifetime'])
        )->setAdapter($sessionAdapter)->start();
        $application->response->redirect($application->request->getURI().'?'.$this->session['basic']['name'].'='.$key)->send();
        return false;
    }

    /**
     * 会话存客户端 url 传输
     * @param mixed $application
     * @return bool
     */
    protected function urlClient($application)
    {
        if ($application->request->hasQuery($this->session['basic']['name'])) {
            $stamp = Helper::dzCode($application->request->getQuery($this->session['basic']['name']));
            if ($stamp && \json_decode($stamp)->flag == $this->stamp['flag']) {
                return true;
            }
        }
        $key = Helper::dzCode(\json_encode($this->stamp), 'ENCODE', '', $this->session['basic']['gc_maxlifetime']);
        $application->response->setJsonContent(['status' => 'ok', 'do' => 'redirect', 'data' => $key])->send();
        return false;
    }

    /**
     * 会话存服务端 header 传输
     * @param mixed $application
     * @return bool
     */
    protected function headerServer($application)
    {
        \ini_set('session.use_cookies', 0);
        \ini_set('session.use_trans_sid', 0);

        $sessionAdapter = $this->session['mode']['adapter'] == 'stream'
        ? new \Phalcon\Session\Adapter\Stream($this->session['stream'])
        : new \Project\Library\SessionFactory($application->di['storage'], $this->session[$this->session['mode']['adapter']]);

        //通过Key时间戳，实现自动过期
        //验证Key的有效期和有效性，实现自动过期，防止会话ID注入和被盗用
        if ($application->request->hasHeader('Authorization')) {
            list(, $key) = \explode($this->session['basic']['name'].' ', $application->request->getHeader('Authorization'), 2);
            if ($key && Helper::dzCode($key) == $this->stamp['flag']) {
                $application->session->setId($key)->setAdapter($sessionAdapter)->start();
                return true;
            }
        }
        $application->session->setId(
            $key = Helper::dzCode($this->stamp['flag'], 'ENCODE', '', $this->session['basic']['gc_maxlifetime'])
        )->setAdapter($sessionAdapter)->start();
        $application->response->setJsonContent(['status' => 'ok', 'do' => 'redirect', 'data' => "key $key"])->send();
        return false;

    }

    /**
     * 会话存客户端 header 传输
     * @param mixed $application
     * @return bool
     */
    protected function headerClient($application)
    {
        if ($application->request->hasHeader('Authorization')) {
            list(, $key) = \explode($this->session['basic']['name'].' ', $application->request->getHeader('Authorization'), 2);
            if ($key && \json_decode(Helper::dzCode($key))->flag == $this->stamp['flag']) {
                return true;
            }
        }
        $key = Helper::dzCode(\json_encode($this->stamp), 'ENCODE', '', $this->session['basic']['gc_maxlifetime']);
        $application->response->setJsonContent(['status' => 'ok', 'do' => 'redirect', 'data' => "key $key"])->send();
        return false;
    }

    protected function accreditAuth(Application $application, Dispatcher $dispatcher, $params)
    {
        $method = $this->session['mode']['side'].\ucfirst($this->session['mode']['transfer']);
        \method_exists($this, $method) || exit("Don't support $method mode!");
        return \call_user_func([$this, $method], $application, $dispatcher, $params);
    }

    protected function clientCookie()
    {
        //
    }

    protected function clientUrl()
    {
        //
    }

    protected function clientHeader()
    {
        //
    }

    protected function serverCookie(Application $application, Dispatcher $dispatcher, $params)
    {
        //间接验证会话ID有效性，防CSRF（仅Cookie方式，Url方式必须在每次请求的时候就验证）
        //if ($dispatcher->hasParam('token')) {
        //    $flag = Helper::dzCode($dispatcher->getParam('token'));
        //    if ($flag && $flag == $this->stamp['flag']) {
        //        if (!$application->session->has($params['sessionKey'])) {
        //            $application->response->redirect('/admin/admin/login')->send();
        //            return true;
        //        }
        //        return true;
        //    }
        //}
        //$application->session->destroy();
        //$application->cookies->reset();
        //$application->response->redirect('/')->send();
        if (!$application->session->has($params['sessionKey'])) {
            $application->response->redirect('manager/login')->send();
            return false;
        }
        return true;
    }

    protected function serverUrl(Application $application, Dispatcher $dispatcher, $params)
    {
        if (!$application->session->has($params['sessionKey'])) {
            $application->response->redirect('/user/login')->send();
            return false;
        }
        return true;
    }

    protected function serverHeader()
    {
        //
    }

    protected function repeatAuth(Application $application, Dispatcher $dispatcher, $params)
    {
        //print_r($data);

        return true;
    }

    protected function resubmitAuth(Application $application, Dispatcher $dispatcher, $params)
    {
        if ($application->request->isPost()) {
            if (!$application->security->checkToken()) {
                $application->response->redirect($application->request->getURI())->send();
                return false;
            }
        }

        return true;
    }
}

?>