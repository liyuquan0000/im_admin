package com.jdd.imadmin.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.*;
import okhttp3.Request.Builder;

/**
 * Created by zj on 2019/7/15.
 */
public class OkHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final OkHttpClient builderClient = new OkHttpClient();

    public OkHttpUtil() {
    }

    public static String sendXmlPost(String traceId, String url, String param, boolean isBreak) {
        return sendXmlPost(traceId, url, param, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendXmlGet(String traceId, String url, boolean isBreak) {
        return sendXmlGet(traceId, url, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendJsonPost(String traceId, String url, String json, boolean isBreak) {
        return sendJsonPost(traceId, url, json, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendJsonGet(String traceId, String url, boolean isBreak) {
        return sendJsonGet(traceId, url, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendTextPost(String traceId, String url, String param, boolean isBreak) {
        return sendTextPost(traceId, url, param, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendTextPost(String traceId, String url, String param, Map<String, String> header, boolean isBreak) {
        return sendTextPost(traceId, url, param, header, 1000, 1000, "utf-8", "utf-8", isBreak);
    }

    public static String sendTextGet(String traceId, String url, boolean isBreak) {
        return sendTextGet(traceId, url, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendKVJsonPost(String traceId, String url, Map<String, String> kv, boolean isBreak) {
        return sendKVJsonPost(traceId, url, kv, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendKVTextPost(String traceId, String url, Map<String, String> kv, boolean isBreak) {
        return sendKVTextPost(traceId, url, kv, 1000L, 1000L, "utf-8", "utf-8", isBreak);
    }

    public static String sendXmlGet(String traceId, String url, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                Request request = (new Request.Builder()).url(url).header("Pragma", "no-cache").addHeader("Cache-Control", "no-cache").addHeader("Content-Type", "text/xml;charset=" + reqCharset).get().build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var12) {
            logger.error("系统错误：traceId:{},urlStr：{}，异常：{}", new Object[]{traceId, url, LogExceptionStackTrace.erroStackTrace(var12)});
            return "";
        }
    }

    public static String sendXmlPost(String traceId, String url, String param, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                RequestBody body = RequestBody.create(MediaType.parse("text/xml"), param.getBytes(reqCharset));
                Request request = (new Request.Builder()).url(url).header("Pragma", "no-cache").addHeader("Cache-Control", "no-cache").addHeader("Content-Type", "text/xml;charset=" + reqCharset).post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var14) {
            logger.error("系统错误：traceId:{},urlStr：{}，postData：{}，异常：{}", new Object[]{traceId, url, param, LogExceptionStackTrace.erroStackTrace(var14)});
            return "";
        }
    }

    public static String sendJsonPost(String traceId, String url, String json, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.getBytes(reqCharset));
                Request request = (new Request.Builder()).url(url).header("X-FORMAT", "json").addHeader("Content-Type", "application/json; charset=" + reqCharset).post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var14) {
            logger.error("系统错误：traceId:{},urlStr：{}，postData：{}，异常：{}", new Object[]{traceId, url, json, LogExceptionStackTrace.erroStackTrace(var14)});
            return "";
        }
    }

    public static String sendJsonGet(String traceId, String url, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                Request request = (new Builder()).url(url).header("X-FORMAT", "json").addHeader("Content-Type", "application/json; charset=" + reqCharset).get().build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var12) {
            logger.error("系统错误：traceId:{},urlStr：{}，异常：{}", new Object[]{traceId, url, LogExceptionStackTrace.erroStackTrace(var12)});
            return "";
        }
    }

    public static String sendKVJsonPost(String traceId, String url, Map<String, String> kv, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                okhttp3.FormBody.Builder formBodyBuilder = new okhttp3.FormBody.Builder();
                Iterator var12 = kv.entrySet().iterator();

                while (var12.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) var12.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }

                FormBody body = formBodyBuilder.build();
                Request request = (new Builder()).url(url).header("X-FORMAT", "json").addHeader("Content-Type", "application/json; charset=" + reqCharset).post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var15) {
            logger.error("系统错误：traceId:{},urlStr：{}，kv：{}，异常：{}", new Object[]{traceId, url, GfJsonUtil.toJSONString(kv), LogExceptionStackTrace.erroStackTrace(var15)});
            return "";
        }
    }

    public static String sendKVTextPost(String traceId, String url, Map<String, String> kv, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                okhttp3.FormBody.Builder formBodyBuilder = new okhttp3.FormBody.Builder();
                Iterator var12 = kv.entrySet().iterator();

                while (var12.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) var12.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }

                FormBody body = formBodyBuilder.build();
                Request request = (new Builder()).url(url).addHeader("Content-Type", "text/plain; charset=" + reqCharset).post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var15) {
            logger.error("系统错误：traceId:{},urlStr：{}，kv：{}，异常：{}", new Object[]{traceId, url, GfJsonUtil.toJSONString(kv), LogExceptionStackTrace.erroStackTrace(var15)});
            return "";
        }
    }

    public static String sendTextPost(String traceId, String url, String param, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                RequestBody body = RequestBody.create(MediaType.parse("text/plain; charset=" + reqCharset), param.getBytes(reqCharset));
                Request request = (new Builder()).url(url).addHeader("Content-Type", "text/plain; charset=" + reqCharset).post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var14) {
            logger.error("系统错误：traceId:{},urlStr：{}，param：{}，异常：{}", new Object[]{traceId, url, param, LogExceptionStackTrace.erroStackTrace(var14)});
            return "";
        }
    }

    public static String sendTextPost(String traceId, String url, String param, Map<String, String> header, int connectTimeoutMilliSeconds, int readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout((long) connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout((long) readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                RequestBody body = RequestBody.create(MediaType.parse("text/plain; charset=" + reqCharset), param.getBytes(reqCharset));
                Builder builder = (new Builder()).url(url);
                Iterator var12 = header.entrySet().iterator();

                while (var12.hasNext()) {
                    Map.Entry<String, String> kv = (Map.Entry) var12.next();
                    builder.addHeader((String) kv.getKey(), (String) kv.getValue());
                }

                Request request = builder.post(body).build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var14) {
            logger.error("系统错误：traceId:{},urlStr：{}，param：{}，异常：{}", new Object[]{traceId, url, param, LogExceptionStackTrace.erroStackTrace(var14)});
            return "";
        }
    }

    public static String sendTextGet(String traceId, String url, long connectTimeoutMilliSeconds, long readTimeoutMilliSeconds, String reqCharset, String resCharset, boolean isBreak) {
        try {
            if (isBreakRequest() && isBreak) {
                return "";
            } else {
                OkHttpClient client = builderClient.newBuilder().connectTimeout(connectTimeoutMilliSeconds, TimeUnit.MILLISECONDS).readTimeout(readTimeoutMilliSeconds, TimeUnit.MILLISECONDS).build();
                Request request = (new Builder()).url(url).addHeader("Content-Type", "text/plain; charset=" + reqCharset).get().build();
                Response response = client.newCall(request).execute();
                return new String(response.body().bytes(), resCharset);
            }
        } catch (Exception var12) {
            logger.error("系统错误：traceId:{},urlStr：{}，异常：{}", new Object[]{traceId, url, LogExceptionStackTrace.erroStackTrace(var12)});
            return "";
        }
    }

    public static boolean isBreakRequest() {
        String flag = "";

//        try {
//            flag = PropertiesUtil.get("request.break");
//        } catch (Exception var2) {
//            logger.error("获取熔断开关异常：{}", LogExceptionStackTrace.erroStackTrace(var2));
//        }

        return "true".equalsIgnoreCase(flag);
    }

}
