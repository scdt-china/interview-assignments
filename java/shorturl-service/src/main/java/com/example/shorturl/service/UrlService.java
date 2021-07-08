package com.example.shorturl.service;

import com.alibaba.fastjson.JSONObject;
import com.example.shorturl.util.CashMapUtil;
import com.example.shorturl.util.ShorturlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : shenhc
 * @date : 2021/7/6
 * desc:
 */
@Service
public class UrlService {

    public String getShorturl(String longurl){

        String shorturlDb = CashMapUtil.getKeyByValue(longurl);

        String shorturl= null;
        if(StringUtils.isNotBlank(shorturlDb)){
            shorturl = shorturlDb;
        }else {
            shorturl = ShorturlUtil.shorten(longurl);
            CashMapUtil.pushValue(shorturl, longurl);
        }
        return shorturl;
    }

    public String getLongurl(String shorturl){
        String longurl = CashMapUtil.getValue(shorturl);
        JSONObject res = new JSONObject();
        res.put("longurl",longurl);
        return longurl;
    }
}
