package com.jdd.imadmin.api.controller.oauth.client;

import com.jdd.imadmin.common.util.OkHttpUtil;
import com.jdd.imadmin.common.util.SSLUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/client")
public class OauthCallBackController {

    /**
     * 回调接口, 用于店信授权中心回调
     * @param code
     * @param state
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void callback(@RequestParam(value = "code") String code,
                         @RequestParam(value = "state", required = false) String state,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("-------------code: " + code);
        System.out.println("-------------state: " + state);
        JSONObject res = getAccessTokenByCode(code);
        System.out.println("-------------res: " + res.toString());
        String accessToken = res.getJSONObject("data").getString("access_token");

        String url = "http://localhost:8083/oauth2/userinfo?access_token=" + accessToken +"&openid=bef13c8d2cb64a5dae6f98c680893184";
        String userInfo = OkHttpUtil.sendTextGet("", url, false);
        System.out.println("---------------userInfo: " + userInfo);

        response.sendRedirect("http://www.baidu.com");

    }

    /**
     * 获取用户accessToken
     * @param code
     * @return
     */
    private JSONObject getAccessTokenByCode(String code) {
        JSONObject authResponse = null;
        CloseableHttpClient httpClient = SSLUtil.createSSLInsecureClient();
        String oauthTokenUrl = "http://localhost:8083/oauth2/user/access_token";
        HttpPost post = new HttpPost(oauthTokenUrl);
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("grant_type", "authorization_code"));
            params.add(new BasicNameValuePair("client_id", "wan_yun_channel"));
            params.add(new BasicNameValuePair("client_secret", "d634f6c006509f11"));
            params.add(new BasicNameValuePair("redirect_uri", "http://localhost:8082/client/callback"));
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            authResponse = new JSONObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                post.releaseConnection();
                httpClient.close();
            } catch (IOException ignored) {
            }
        }

        return authResponse;
    }

    /**
     * 通过refreshToken刷新accessToken
     * @param refreshToken
     * @return
     */
    public static JSONObject getAccessTokenByRefreshToken(String refreshToken) {
        JSONObject authResponse = null;
        CloseableHttpClient httpClient = SSLUtil.createSSLInsecureClient();
        String oauthTokenUrl = "http://localhost:8083/oauth2/user/refresh_token";
        HttpPost post = new HttpPost(oauthTokenUrl);
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("client_id", "wan_yun_channel"));
            params.add(new BasicNameValuePair("client_secret", "d634f6c006509f11"));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            authResponse = new JSONObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                post.releaseConnection();
                httpClient.close();
            } catch (IOException ignored) {
            }
        }

        return authResponse;
    }

    /**
     * 获取全局token
     * @param code
     * @return
     */
    private static JSONObject getAccessToken() {
        JSONObject authResponse = null;
        CloseableHttpClient httpClient = SSLUtil.createSSLInsecureClient();
        String oauthTokenUrl = "http://localhost:8083/oauth2/tp/token";
        HttpPost post = new HttpPost(oauthTokenUrl);
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "client_credentials"));
            params.add(new BasicNameValuePair("client_id", "wan_yun_channel"));
            params.add(new BasicNameValuePair("client_secret", "d634f6c006509f11"));
            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            authResponse = new JSONObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                post.releaseConnection();
                httpClient.close();
            } catch (IOException ignored) {
            }
        }

        return authResponse;
    }

    public static void main(String[] args) {
        /*JSONObject response = getAccessTokenByRefreshToken("ad03cc6a4b8a4e69bd3012b7d3a62390");
        System.out.println("accessToken: " + response.toString());*/

        System.out.println(getAccessToken());
    }

}
