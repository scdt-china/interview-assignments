<?php
use \ErrorMessage as ErrorMessage;
/**
 * 常用用户格式
 * @author subin
 */
namespace Common;

class Format {
	//会员密码加密钥匙（不可修改）
	private static $membr_pw_key = 'd80*73&^%$#';

    /**
     * 计算字符串宽度，一个中文字宽为2
     *
     * @param string $str
     * @static
     * @access public
     * @return string
     */
    public static function stringWidth($str) {
        return mb_strwidth($str, 'UTF-8');
    }

	/**
	 * 校验用户名
	 *
	 * @param string $name
	 * @return int
	 */
	public static function checkUsername($name = '') {
		if (empty($name)) {
			return -15010;
		}

		$len = self::stringWidth($name);
		if ($len< 5 || $len> 16) {
			return -15011;
		}

        //用户名：只能包含英文字母，下划线或数字
        $preg = "/^[A-Za-z0-9_]+$/u";
        if (!preg_match($preg, $name)) {
            return -15013;
        }

		//只能包含英文字母，下划线或数字
        $first = substr($name, 0, 1);
		$preg = "/^[a-z|A-Z|_]$/";
		if (preg_match($first, $name)) {
			return -15012;
		}

		return ErrorMessage::SUCCESS_CODE;
	}
	


	/**
	 * 校验密码
	 *
	 * @param string $pw 密码（必填）
	 * @return int
	 */
	public static function checkPw($pw = '') {
		$result['code'] = 0;

		if (empty($pw)) {
			return -15020;
		}

		//小于7位 || 大于16位
		$len = self::stringWidth($pw);
		if ($len < 7 || $len > 16) {
			return -15021;
		}
        //不是键盘上直接可见的字母、数字和符号
        $preg = "/^[\x21-\x7e]+$/";
        if (!preg_match($preg, $pw)) {
            return -15027;
        }


		//有连续3个数字
		$preg = "/[\w]*[\d]{3,}[\w]*/";
		if (preg_match($preg, $pw)) {
			return -15026;
		}

        //大写字母,小写字母或数字中的两项
        if (!preg_match('/^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]+/s', $pw)){
            return -15023;
        }



        return ErrorMessage::SUCCESS_CODE;
	}



	/**
	 * 给会员生成密码
	 * @param string $str
	 * @return string
	 */
	public static function generatePw($str = '') {
		$md5 = md5(self::$membr_pw_key . $str);
		$segments = str_split($md5, 8);
		$new_salt = $segments[3] . $segments[2] . $segments[1] . $segments[0];
		$new_str = $segments[2] . $segments[1] . $segments[0] . $segments[3];
		return hash('whirlpool', $new_str . $new_salt);
	}
	
}