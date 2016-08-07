package com.crazyacking.cnblogs.Login12306;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpDoPostUtils.java Create on 2012-9-7下午3:08:18
 * <p>
 * <p>
 * Copyright (c) 2012 by crazyacking.
 *
 * @author lmeteor
 * @version 1.0
 * @Email crazyacking@gmail.com
 * @description 模拟HTTP发送请求得到报文
 */
@SuppressWarnings("deprecation")
public class HttpDoPostUtils {


    private static HttpClient httpClient = null;

    static {
        //指定协议名称和默认的端口号
        Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
        //注册刚才创建的https 协议对象
        Protocol.registerProtocol("https", myhttps);
        httpClient = new HttpClient();
    }

    /**
     * 发送请求报文，得到响应报文
     *
     * @param url   登录请求URL
     * @param pList 是否包含请求参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String doRequestToString(String url, List<NameValuePair> pList) throws UnsupportedEncodingException {
        //获得postMethod对象
        PostMethod pmethod = getPostMethod(url);
        pmethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        //判断是否包含参数
        if (null != pList && pList.size() > 0) {
            pmethod.setRequestBody(pList.toArray(new NameValuePair[pList.size()]));
        }
        String value = "";
        try {
            httpClient.executeMethod(pmethod);
            value = pmethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 获得12306网站的登录验证码
     *
     * @param url      请求URL
     * @param filePath 验证码保存路径 如:e:\\login.jpg
     * @return
     */
    public static File doGetFile(String url, String filePath) {
        PostMethod pmethod = getPostMethod(url);
        pmethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        try {
            httpClient.executeMethod(pmethod);
            //得到响应中的流对象
            InputStream in = pmethod.getResponseBodyAsStream();
            //包装 并读出流信息
            BufferedInputStream bis = new BufferedInputStream(in);
            File file = new File(filePath);
            FileOutputStream fs = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len = bis.read(buf);
            if (len == -1 || len == 0) {
                file.delete();
                file = null;
            }
            while (len != -1) {
                fs.write(buf, 0, len);
                len = bis.read(buf);
            }
            fs.flush();
            fs.close();
            return file;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<NameValuePair> createNameValuePair(String params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != params && !params.trim().equals("")) {
            String[] _params = params.split("&");
            // userCookieList = new AttributeList();
            for (int i = 0; i < _params.length; i++) {
                int _i = _params[i].indexOf("=");
                if (_i != -1) {
                    String name = _params[i].substring(0, _i);
                    String value = _params[i].substring(_i + 1);
                    nvps.add(new NameValuePair(name, value));
                }
            }
        }
        return nvps;
    }


    public static PostMethod getPostMethod(String url) {
        PostMethod pmethod = new PostMethod(url);
        //设置响应头信息
        pmethod.addRequestHeader("Connection", "keep-alive");
        pmethod.addRequestHeader("Cache-Control", "max-age=0");
        pmethod.addRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        pmethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        return pmethod;
    }

}