package com.crazyacking.cnblogs.login.impl;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class HttpClientLoginImpl {


    public static void main(String args[]) throws Exception {
        HttpClientLoginImpl clientLogin = new HttpClientLoginImpl();
        clientLogin.getCookies();

    }

    public Cookie[] getCookies() throws IOException {

        //登陆 Url
        String loginUrl = "http://passport.cnblogs.com/login.aspx";

        //需登陆后访问的 Url
        String dataUrl = "http://home.cnblogs.com/";


        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));


        PostMethod postMethod = new PostMethod(loginUrl);

        NameValuePair[] data = {
                new NameValuePair("__VIEWSTATE", "/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt"),
                new NameValuePair("__EVENTVALIDATION", "/wEdAAUyDI6H/s9f+ZALqNAA4PyUhI6Xi65hwcQ8/QoQCF8JIahXufbhIqPmwKf992GTkd0wq1PKp6+/1yNGng6H71Uxop4oRunf14dz2Zt2+QKDEIYpifFQj3yQiLk3eeHVQqcjiaAP"),
                new NameValuePair("tbUserName", "crazyacking"),
                new NameValuePair("tbPassword", "199312181012"),
                new NameValuePair("txtReturnUrl", dataUrl),
                new NameValuePair("btnLogin", "登  录")
        };

        postMethod.setRequestBody(data);

        Cookie[] cookies = null;
        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);

            //获得登陆后的 Cookie
            cookies = httpClient.getState().getCookies();
        } catch (Exception e) {
            System.out.println(e);
        }

        String cookiesString = "";
        for (Cookie cookie : cookies) {
            cookiesString += cookie.toString() + ";";
        }

        System.out.println(cookiesString);

        GetMethod getMethod = new GetMethod(dataUrl);

        //每次访问需授权的网址时需带上前面的 cookie 作为通行证
        getMethod.setRequestHeader("cookie", cookiesString);

        try {

            //标明来自哪里的链接，以及UA
            postMethod.setRequestHeader("Referer", "http://unmi.cc");
            postMethod.setRequestHeader("User-Agent", "Unmi Spot");

            httpClient.executeMethod(getMethod);

            //打印出返回数据，检验一下是否成功
            String text = getMethod.getResponseBodyAsString();
            System.out.println(text);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            postMethod.releaseConnection();
        }

        return null;
    }
}