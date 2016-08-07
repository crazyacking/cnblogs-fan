package com.crazyacking.cnblogs.login;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Mengyu on 2016/8/7.
 */
public class SinaLogin {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection.Response login = Jsoup.connect("http://weibo.com/login.php#_loginLayer_1470552643721")
                .data("tbUserName", "11301655@qq.com")
                .data("tbPassword", "passwd")
                .method(Connection.Method.POST)
                .execute();
        Thread.sleep(1000);

        Document document = Jsoup.connect("http://weibo.com/3736544454/profile?rightmod=1&wvr=6&mod=personinfo&is_all=1")
                .cookies(login.cookies())
                .get();

//        Document document1 = Jsoup.connect("http://home.cnblogs.com/set/account/")
//                .cookies(login.cookies())
//                .get();

        System.out.println(document);

//        System.out.println("==============================================");
//        System.out.println(document1);

    }
}
