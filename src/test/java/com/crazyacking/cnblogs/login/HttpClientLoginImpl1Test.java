package com.crazyacking.cnblogs.login;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Mengyu on 2016/8/6.
 */
public class HttpClientLoginImpl1Test {
    @org.junit.Test
    public void fun() throws Exception {
        Connection.Response res = Jsoup.connect("http://passport.cnblogs.com/user/signin")
                .data("username", "crazyacking", "password", "passwd")
                .method(Connection.Method.POST)
                .execute();

        Document doc = res.parse();
//这儿的SESSIONID需要根据要登录的目标网站设置的session Cookie名字而定
        String sessionId = res.cookie("Cookie");
        String Cookie = " OUTFOX_SEARCH_USER_ID_NCOO=547952848.570331; lzstat_uv=23294081293993242079|2817822; Hm_lvt_cc17b07fc9529e3d80b4482c9ce09086=1468976541,1469602805; __utma=226521935.986747537.1467087607.1469782638.1470460991.4; __utmz=226521935.1469782638.3.3.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _ga=GA1.2.986747537.1467087607; SERVERID=9ffd301069c1081a14d128e0c97deda8|1470549115|1470546665";


        Document objectDoc = Jsoup.connect("http://i.cnblogs.com/")
                .cookie(Cookie, sessionId)
                .get();
        System.out.println(objectDoc.toString());

    }

}