package com.creolophus.liuyi.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;

/**
 * 朝辞白帝彩云间 千行代码一日还 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2020/2/20 下午6:58
 */
public class OkHttp3Util {

  private static final Logger logger = LoggerFactory.getLogger(OkHttp3Util.class);

  private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

  private static final MediaType FORM_DATA = MediaType
      .parse("application/x-www-form-urlencoded; charset=utf-8");

  private static void doAsyncGet(
      String baseUrl, Map<String, String> params, Map<String, String> headersMap,
      Callback callback) {
    OkHttpClient client = OkHttp3Util.getInstance();
    String url = urlJoin(baseUrl, params);
    Request request;
    if (null == headersMap || headersMap.size() == 0) {
      request = new Request.Builder().url(url).build();
    } else {
      Headers headers = setHeaders(headersMap);
      request = new Request.Builder().url(url).headers(headers).build();
    }
    Call call = client.newCall(request);
    call.enqueue(callback);
  }

  private static void doAsyncPost(
      String url, Map<String, String> params, Map<String, String> headersMap, Callback callback) {
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.add(entry.getKey(), entry.getValue());
    }
    FormBody body = builder.build();
    doAsyncPost(url, body, headersMap, callback);
  }

  private static void doAsyncPost(String url, RequestBody body, Map<String, String> headersMap,
      Callback callback) {
    OkHttpClient client = OkHttp3Util.getInstance();
    Request request;
    if (null == headersMap || headersMap.size() == 0) {
      request = new Request.Builder().post(body).url(url).build();
    } else {
      Headers headers = setHeaders(headersMap);
      request = new Request.Builder().post(body).url(url).headers(headers).build();
    }
    Call call = client.newCall(request);
    call.enqueue(callback);
  }

  private static Resp doSyncGet(String baseUrl, Map<String, String> params,
      Map<String, String> headersMap) {
    OkHttpClient client = OkHttp3Util.getInstance();
    String url = urlJoin(baseUrl, params);
    Request request;
    if (null == headersMap || headersMap.size() == 0) {
      request = new Request.Builder().url(url).build();
    } else {
      Headers headers = setHeaders(headersMap);
      request = new Request.Builder().url(url).headers(headers).build();
    }
    Call call = client.newCall(request);
    try {
      return new Resp(call.execute());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Resp doSyncPost(String url, Map<String, String> params,
      Map<String, String> headersMap) {
    FormBody.Builder builder = new FormBody.Builder();
    if (params != null && params.size() > 0) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        builder.add(entry.getKey(), entry.getValue());
      }
    }
    FormBody body = builder.build();
    return doSyncPost(url, body, headersMap);
  }

  private static Resp doSyncPost(String url, RequestBody body, Map<String, String> headersMap) {
    OkHttpClient client = OkHttp3Util.getInstance();
    Request request;
    if (null == headersMap || headersMap.size() == 0) {
      request = new Request.Builder().post(body).url(url).build();
    } else {
      Headers headers = setHeaders(headersMap);
      request = new Request.Builder().post(body).url(url).headers(headers).build();
    }
    Call call = client.newCall(request);
    try {
      return new Resp(call.execute());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Simple async get request.
   *
   * @param url request
   * @param callback call back
   */
  public static void get(String url, Callback callback) {
    doAsyncGet(url, null, null, callback);
  }

  /**
   * Asynchronous get request with parameters
   *
   * @param baseUrl request base url
   * @param params request params
   * @param callback callback
   */
  public static void get(String baseUrl, Map<String, String> params, Callback callback) {
    doAsyncGet(baseUrl, params, null, callback);
  }

  /**
   * Asynchronous get request with parameters and headers
   *
   * @param baseUrl request base url
   * @param params request params
   * @param headers request headers
   * @param callback call back
   */
  public static void get(
      String baseUrl, Map<String, String> params, Map<String, String> headers, Callback callback) {
    doAsyncGet(baseUrl, params, headers, callback);
  }

  /**
   * Synchronous get request with parameters and headers
   *
   * @param baseUrl request base url
   * @param params request params
   * @param headers request headers
   * @return response okhttp3.Response
   */
  public static Resp get(String baseUrl, Map<String, String> params, Map<String, String> headers) {
    return doSyncGet(baseUrl, params, headers);
  }

  /**
   * Simple sync get request.
   *
   * @param url request url
   * @return response okhttp3.Response
   */
  public static Resp get(String url) {
    return doSyncGet(url, null, null);
  }

  /**
   * Synchronous get request with parameters
   *
   * @param baseUrl request base url
   * @param params request params
   * @return response okhttp3.Response
   */
  public static Resp get(String baseUrl, Map<String, String> params) {
    return doSyncGet(baseUrl, params, null);
  }

  public static OkHttpClient getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
   * Asynchronous post request with parameters
   *
   * @param url request url
   * @param params request params
   * @param callback call back
   */
  public static void post(String url, Map<String, String> params, Callback callback) {
    doAsyncPost(url, params, null, callback);
  }

  /**
   * Asynchronous post request with parameters and headers
   *
   * @param url request url
   * @param params request params
   * @param headers request headers
   * @param callback call back
   */
  public static void post(
      String url, Map<String, String> params, Map<String, String> headers, Callback callback) {
    doAsyncPost(url, params, headers, callback);
  }

  /**
   * Asynchronous post json request
   *
   * @param url request url
   * @param json json data
   * @param callback call back
   */
  public static void post(String url, String json, Callback callback) {
    RequestBody body = RequestBody.create(JSON_TYPE, json);
    doAsyncPost(url, body, null, callback);
  }

  /**
   * Asynchronous post json request with headers
   *
   * @param url request url
   * @param json json data
   * @param headersMap request headers
   * @param callback call back
   */
  public static void post(String url, String json, Map<String, String> headersMap,
      Callback callback) {
    RequestBody body = RequestBody.create(JSON_TYPE, json);
    doAsyncPost(url, body, headersMap, callback);
  }

  /**
   * Synchronous post request with parameters
   *
   * @param url request url
   * @param params request params
   * @return response okhttp3.Response
   */
  public static Resp post(String url, Map<String, String> params) {
    return doSyncPost(url, params, null);
  }

  /**
   * Synchronous post request with parameters and headers
   *
   * @param url url
   * @param params request params
   * @param headers request headers
   * @return response okhttp3.Response
   */
  public static Resp post(String url, Map<String, String> params, Map<String, String> headers) {
    return doSyncPost(url, params, headers);
  }

  /**
   * Synchronous post json request
   *
   * @param url request url
   * @param json json data
   * @return response okhttp3.Response
   */
  public static Resp post(String url, String json) {
    RequestBody body = RequestBody.create(JSON_TYPE, json);
    return doSyncPost(url, body, null);
  }

  /**
   * Synchronous post json request with headers
   *
   * @param url request url
   * @param json json data
   * @param headersMap request headers
   * @return response okhttp3.Response
   */
  public static Resp post(String url, String json, Map<String, String> headersMap) {
    RequestBody body = RequestBody.create(JSON_TYPE, json);
    return doSyncPost(url, body, headersMap);
  }

  private static Headers setHeaders(Map<String, String> headersParams) {
    Headers headers;
    okhttp3.Headers.Builder headersBuilder = new okhttp3.Headers.Builder();
    if (headersParams != null) {
      Iterator<String> iterator = headersParams.keySet().iterator();
      String key;
      while (iterator.hasNext()) {
        key = iterator.next().toString();
        headersBuilder.add(key, headersParams.get(key));
      }
    }
    headers = headersBuilder.build();
    return headers;
  }

  public static String urlJoin(String url, Map<String, String> params) {
    StringBuilder endUrl = new StringBuilder(url);
    if (null == params) {
      return url;
    }
    boolean isFirst = true;
    Set<Map.Entry<String, String>> entrySet = params.entrySet();
    for (Map.Entry<String, String> entry : entrySet) {
      if (isFirst && !url.contains("?")) {
        isFirst = false;
        endUrl.append("?");
      } else {
        endUrl.append("&");
      }
      endUrl.append(entry.getKey());
      endUrl.append("=");
      endUrl.append(entry.getValue());
    }
    return endUrl.toString();
  }

  private static class SingletonHolder {

    private static final OkHttpClient INSTANCE = new DefaultOkHttpClientFactory(
        new OkHttpClient.Builder())
        .createBuilder(false)
        .connectTimeout(2000, TimeUnit.MILLISECONDS)
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .followRedirects(false)
        .connectionPool(new DefaultOkHttpClientConnectionPoolFactory()
            .create(Runtime.getRuntime().availableProcessors(), 15 * 60L, TimeUnit.SECONDS)).
            build();
  }

  public static class Resp {

    private String body;
    private int code;
    private Map<String, String> headers;
    private boolean isSuccessful;
    private boolean isRedirect;
    private String protocol;

    public Resp(Response response) {

      if (response != null) {
        this.code = response.code();
        this.isSuccessful = response.isSuccessful();
        this.isRedirect = response.isRedirect();
        this.protocol = response.protocol().toString();

        if (response.headers() != null) {
          headers = new HashMap(response.headers().size());
          for (String header : response.headers().names()) {
            headers.put(header, response.header(header));
          }
        }
        try {
          this.body = response.body().string();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }

    public String getBody() {
      return body;
    }

    public int getCode() {
      return code;
    }

    public Map<String, String> getHeaders() {
      return headers;
    }

    public String getProtocol() {
      return protocol;
    }

    public boolean isRedirect() {
      return isRedirect;
    }

    public boolean isSuccessful() {
      return isSuccessful;
    }

    public String toDetail() {
      final StringBuilder sb = new StringBuilder("{");
      sb.append("\"body\":\"").append(body).append('\"');
      sb.append(",\"code\":").append(code);
      sb.append(",\"headers\":").append(headers);
      sb.append(",\"protocol\":\"").append(protocol).append('\"');
      sb.append('}');
      return sb.toString();
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("{");
      sb.append("\"body\":\"").append(body).append('\"');
      sb.append(",\"code\":").append(code);
      sb.append('}');
      return sb.toString();
    }
  }

}
