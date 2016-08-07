package com.crazyacking.cnblogs.login;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Mengyu on 2016/8/7.
 */
public class LoginTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection.Response login = Jsoup.connect("https://passport.cnblogs.com/user/signin")
                .data("tbUserName", "crazyacking")
                .data("tbPassword", "passwd")
                .method(Connection.Method.POST)
                .execute();
        Thread.sleep(1000);

        Document document = Jsoup.connect("http://i.cnblogs.com/")
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
