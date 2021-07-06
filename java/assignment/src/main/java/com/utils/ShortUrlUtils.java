package com.utils;

import java.util.ArrayList;
import java.util.List;

public class ShortUrlUtils {

    public static String getShortText(int count) {
        //自己定义生成MD5加密字符串前的混合KEY
        String[] chars = new String[]{          //要使用生成URL的字符
                "a","b","c","d","e","f","g","h",
                "i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x",
                "y","z","0","1","2","3","4","5",
                "6","7","8","9","A","B","C","D",
                "E","F","G","H","I","J","K","L",
                "M","N","O","P","Q","R","S","T",
                "U","V","W","X","Y","Z"
        };

        int currentNum = count;
        List<Integer> countList = new ArrayList<>();
        countList.add(currentNum % chars.length);
        while(currentNum > 0){
            currentNum /= chars.length;
            if(currentNum > 0){
                countList.add(currentNum);
            }
        }

        StringBuilder sb = new StringBuilder();
        countList.forEach(num -> sb.append(chars[num]));
        return sb.toString();
    }

}
