package com.crazyacking.cnblogs.login.impl;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/*
 * 根据Url得到cookie
 */
public class HttpClientLoginImpl {


    public static Cookie[] getCookies1() {

        //登陆 Url
        String loginUrl = "http://passport.cnblogs.com/login.aspx";

        //需登陆后访问的 Url
        String dataUrl = "http://home.cnblogs.com/";

        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));


        PostMethod postMethod = new PostMethod(loginUrl);

        NameValuePair[] data = {
                new NameValuePair("__VIEWSTATE", "/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt"),
                new NameValuePair("__EVENTVALIDATION", "/wEdAAUyDI6H/s9f+ZALqNAA4PyUhI6Xi65hwcQ8/QoQCF8JIahXufbhIqPmwKf992GTkd0wq1PKp6+/1yNGng6H71Uxop4oRunf14dz2Zt2+QKDEIYpifFQj3yQiLk3eeHVQqcjiaAP"),
                new NameValuePair("tbUserName", "yourusername"),
                new NameValuePair("tbPassword", "yourpassword"),
                new NameValuePair("txtReturnUrl", "http://home.cnblogs.com/"),
                new NameValuePair("btnLogin", "登  录")
        };

        postMethod.setRequestBody(data);


//        try {
//            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//            httpClient.executeMethod(postMethod);
//
//            //获得登陆后的 Cookie
//            Cookie[] cookies=httpClient.getState().getCookies();
//            System.out.println(cookies);
//            System.out.println("============================================================================");
//            String tmpcookies= "";
//            for(Cookie c:cookies){
//                tmpcookies += c.toString()+";";
//            }


        try {
            client.executeMethod(postMethod);
            Cookie[] cookies = client.getState().getCookies();
            return cookies;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            postMethod.releaseConnection();
        }
        return null;
    }


    public static Cookie[] getCookies(String url) {
        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        GetMethod method = new GetMethod(url);

        PostMethod postMethod = new PostMethod(url);

        NameValuePair[] data = {
                new NameValuePair("__VIEWSTATE", "/wEPDwULLTE1MzYzODg2NzZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQtjaGtSZW1lbWJlcm1QYDyKKI9af4b67Mzq2xFaL9Bt"),
                new NameValuePair("__EVENTVALIDATION", "/wEdAAUyDI6H/s9f+ZALqNAA4PyUhI6Xi65hwcQ8/QoQCF8JIahXufbhIqPmwKf992GTkd0wq1PKp6+/1yNGng6H71Uxop4oRunf14dz2Zt2+QKDEIYpifFQj3yQiLk3eeHVQqcjiaAP"),
                new NameValuePair("tbUserName", "yourusername"),
                new NameValuePair("tbPassword", "yourpassword"),
                new NameValuePair("txtReturnUrl", "http://home.cnblogs.com/"),
                new NameValuePair("btnLogin", "登  录")
        };

        postMethod.setRequestBody(data);


//        try {
//            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//            httpClient.executeMethod(postMethod);
//
//            //获得登陆后的 Cookie
//            Cookie[] cookies=httpClient.getState().getCookies();
//            System.out.println(cookies);
//            System.out.println("============================================================================");
//            String tmpcookies= "";
//            for(Cookie c:cookies){
//                tmpcookies += c.toString()+";";
//            }


//        GetMethod getMethod = new GetMethod(dataUrl);
//
//        //每次访问需授权的网址时需带上前面的 cookie 作为通行证
//        getMethod.setRequestHeader("cookie",tmpcookies);
//
//        //标明来自哪里的链接，以及UA
//        postMethod.setRequestHeader("Referer", "http://unmi.cc");
//        postMethod.setRequestHeader("User-Agent","Unmi Spot");
//
//        httpClient.executeMethod(getMethod);
//
//        //打印出返回数据，检验一下是否成功
//        String text = getMethod.getResponseBodyAsString();
//        System.out.println(text);

        try {
            client.executeMethod(method);
            Cookie[] cookies = client.getState().getCookies();
            return cookies;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public static void main(String args[]) throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        GetMethod method = new GetMethod("http://localhost:8080/");
        try {
            client.executeMethod(method);
            Cookie[] cookies = client.getState().getCookies();
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                System.err.println("Cookie: " + cookie.getName() + ", Value: "
                        + cookie.getValue() + ", IsPersistent?: "
                        + cookie.isPersistent() + ", Expiry Date: "
                        + cookie.getExpiryDate() + ", Comment: "
                        + cookie.getComment());
                cookie.setValue("My own value");
            }
            client.executeMethod(method);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            method.releaseConnection();
        }
    }
}