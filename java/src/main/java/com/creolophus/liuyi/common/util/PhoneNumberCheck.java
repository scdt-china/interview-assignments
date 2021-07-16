package com.creolophus.liuyi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author magicnana
 * @date 2019/7/16 下午6:49
 */
public class PhoneNumberCheck {

  public static boolean isPhoneNumber(String phone) {
    boolean isValid = false;
    String expression = "((^(13|15|18|17|16|19)[0-9]{9}$)|"
        + "(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|"
        + "(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|"
        + "(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
    CharSequence inputStr = phone;
    Pattern pattern = Pattern.compile(expression);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      isValid = true;
    }
    return isValid;
  }
}
