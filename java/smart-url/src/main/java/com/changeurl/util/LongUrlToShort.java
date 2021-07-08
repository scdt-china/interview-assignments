package com.changeurl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 *
 * @Filename LongUrlToShort.java
 *
 * @Description 长URL生成短URL
 *
 * @Version 1.0
 *
 * @Author marui
 *
 * @mail wwwmr1909@163.com
 *
 *
 */
public class LongUrlToShort {

    //测试用的
    public static void main(String[] args) {
        System.out.println(createShortUrl("http://weibo.com/lucd1990/profile"));
        System.out.println(queryLongUrl("http://dwz.cn/vp3cL"));
        System.out.println(custShortUrl("java_comp", "http://www.blogjava.net/jjshcc/archive/2011/04/12/348132.html"));
    }

    /**
     * 让dwz.cn生成长连接对应的短连接
     * @param longUrl
     * @return
     */
    public static String createShortUrl(String longUrl){
        String json = sendPost("http://dwz.cn/create.php", "url=" + longUrl);
        String parms = json.replace("\"", "").replace("\\", "");
        System.out.println(json);
        if(parms.contains("status:0")){
            return parms.substring(parms.indexOf("http:"), parms.indexOf(",", parms.indexOf("http:")));
        }else
            return "error";
    }

    /**
     * 自定义长连接与短连接的映射,并注册到dwz.cn
     * @param tinyUrl
     * @param longUrl
     * @return
     */
    public static boolean custShortUrl(String tinyUrl, String longUrl){
        String json = sendPost("http://dwz.cn/create.php", "url="+longUrl + "&alias=" + tinyUrl);
        String parms = json.replace("\"", "").replace("\\", "");
        System.out.println(json);
        if(parms.contains("status:0")){
            return true;
        }else
            return false;
    }

    /**
     * 查询短连接对应的长连接
     * @param tinyUrl
     * @return
     */
    public static String queryLongUrl(String tinyUrl){
        String json = sendPost("http://dwz.cn/query.php", "tinyurl=" + tinyUrl);
        json = json.replaceAll("\"", "");
        System.out.println(json);
        if(json.contains("status:0")){
            return json.substring(json.indexOf("http:"), json.indexOf("}"));
        }else
            return "error";
    }


    /**
     * post传递参数
     **/
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) "
                    + "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) "
                    + "Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * get传递参数
     **/
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
