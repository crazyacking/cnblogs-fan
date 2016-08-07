package com.crazyacking.cnblogs.login.impl;


import sun.net.www.protocol.http.HttpURLConnection;

import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * Created by Mengyu on 2016/8/6.
 */
public class Session {
    public static void main(String[] args) throws Exception {
        String surl = "http://passport.cnblogs.com/login.aspx";
        URL url = new URL(surl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream());
        out.write("memberName=crazyacking&password=passwd");
        out.flush();
        out.close();

        // 取得cookie，相当于记录了身份，供下次访问时使用
        final String cookieVal = connection.getHeaderField("Set-Cookie");

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    String s = "http://oa.zzti.edu.cn/main.php";
                    //重新打开一个连接
                    try {
                        URL url = new URL(s);
                        HttpURLConnection resumeConnection = (HttpURLConnection) url
                                .openConnection();
                        if (cookieVal != null) {
                            //发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
                            resumeConnection.setRequestProperty("Cookie", cookieVal);
                        }
                        resumeConnection.connect();

                        Thread.currentThread().sleep(1000);
                        System.out.println(cookieVal);
                    } catch (Exception ex) {
                    }
                }

            }
        }).start();
    }
}
