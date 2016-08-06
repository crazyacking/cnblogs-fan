package com.crazyacking.cnblogs.login;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created by Mengyu on 2016/8/6.
 */
public class HttpClientLoginImpl1 {
    public static void main(String[] args) {
        //登陆 Url
        String loginUrl = "http://passport.cnblogs.com/login.aspx";

        //需登陆后访问的 Url
        String dataUrl = "http://home.cnblogs.com/";


        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        //设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了
        NameValuePair[] data = {
                new NameValuePair("__VIEWSTATE", "/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt"),
                new NameValuePair("__EVENTVALIDATION", "/wEdAAUyDI6H/s9f+ZALqNAA4PyUhI6Xi65hwcQ8/QoQCF8JIahXufbhIqPmwKf992GTkd0wq1PKp6+/1yNGng6H71Uxop4oRunf14dz2Zt2+QKDEIYpifFQj3yQiLk3eeHVQqcjiaAP"),
                new NameValuePair("tbUserName", "acker01"),
                new NameValuePair("tbPassword", "jsb.123456"),
                new NameValuePair("txtReturnUrl", "http://home.cnblogs.com/"),
                new NameValuePair("btnLogin", "登  录")
        };
        postMethod.setRequestBody(data);

        try {
            HttpClient httpClient = new HttpClient();
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);

            //获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();
            System.out.println(cookies);
            System.out.println("============================================================================");
            String tmpcookies = "";
            for (Cookie c : cookies) {
                tmpcookies += c.toString() + ";";
            }
            System.out.println(tmpcookies);
            //进行登陆后的操作
            GetMethod getMethod = new GetMethod(dataUrl);

            //每次访问需授权的网址时需带上前面的 cookie 作为通行证
            getMethod.setRequestHeader("cookie", tmpcookies);

            //标明来自哪里的链接，以及UA
            postMethod.setRequestHeader("Referer", "http://unmi.cc");
            postMethod.setRequestHeader("User-Agent", "Unmi Spot");

            httpClient.executeMethod(getMethod);

            //打印出返回数据，检验一下是否成功
            String text = getMethod.getResponseBodyAsString();
            System.out.println(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
