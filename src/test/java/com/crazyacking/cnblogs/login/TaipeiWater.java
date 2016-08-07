package com.crazyacking.cnblogs.login;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TaipeiWater {

    final static String requestUrl = "https://passport.cnblogs.com/user/signin";

    public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        String tbUserName = "crazyacking";
        String tbPassword = "passwd";

        enableSSLSocket();

        Connection.Response response = Jsoup.connect(requestUrl)
                .data("tbUserName", tbUserName)
                .data("tbPassword", tbPassword)
                .userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
                .method(Connection.Method.POST)
                .execute();

        System.out.println(response.toString());

        Document document = Jsoup.connect("http://i.cnblogs.com/")
                .cookies(response.cookies())
                .get();

        Document document2 = Jsoup.connect("http://i.cnblogs.com/Preferences.aspx")
                .cookies(response.cookies())
                .get();
        System.out.println(document2);

//        System.out.println(document);

        switch (response.statusCode()) {
            case 200:
                doProcess(response.parse());
                break;
            default:

                break;
        }
    }

    public static void doProcess(Document document) {
//        System.out.println(document);
        // do something...
    }
}