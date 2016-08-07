//package com.crazyacking.cnblogs.Login12306;
//
//
//import com.mchange.v2.util.PropertiesUtils;
//import net.sf.json.JSONObject;
//import org.study.meteor.ticket.domain.LoginBeforeValidatior;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
///**
// * Login.java.java Create on 2012-9-26下午1:48:42
// * <p>
// * <p>
// * Copyright (c) 2012 by crazyacking.
// *
// * @author lmeteor
// * @version 1.0
// * @Email crazyacking@gmail.com
// * @description
// */
//public class Login {
//    /**
//     * 获取验证码
//     *
//     * @param filePath
//     * @return
//     */
//    public static String getRandCode(String filePath) {
//        String randCode = "";
//        /** 获取验证码 */
//        HttpDoPostUtils.doGetFile(PropertiesUtils.newInstance().getPropertiesValue("loginCode"), filePath);
//        randCode = readString("请输入登录验证码：");
//        return randCode;
//    }
//
//
//    /**
//     * 实现登录操作
//     *
//     * @throws UnsupportedEncodingException
//     */
//    public static void doLogin() throws UnsupportedEncodingException {
//        String randCode = getRandCode("e:\\login.jpg");
//        /** 登录前 提交得到报文 */
//        String loginBeforeVal = HttpDoPostUtils.doRequestToString(PropertiesUtils.newInstance().getPropertiesValue("loginBeforeValidatiorUrl"), null);
//        //将返回的JSON报文转换成指定的对象
//        JSONObject jsonObj = JSONObject.fromObject(loginBeforeVal);
//        LoginBeforeValidatior loginBefore = new LoginBeforeValidatior();
//        loginBefore = (LoginBeforeValidatior) JSONObject.toBean(jsonObj, LoginBeforeValidatior.class);
//        //拼接参数
//        StringBuffer params = new StringBuffer();
//        params.append("loginRand=" + loginBefore.getLoginRand()).append("&")
//                .append("refundLogin=N").append("&")
//                .append("refundFlag=Y").append("&")
//                .append("loginUser.user_name=" + PropertiesUtils.newInstance().getPropertiesValue("username")).append("&")
//                .append("nameErrorFocus=&")
//                .append("user.password=" + PropertiesUtils.newInstance().getPropertiesValue("password")).append("&")
//                .append("passwordErrorFocus=&")
//                .append("randCode=" + randCode).append("&")
//                .append("randErrorFocus=");
//        //像服务器发送登录请求 并返回对应的报文
//        String loginResponseText = HttpDoPostUtils.doRequestToString(PropertiesUtils.newInstance().getPropertiesValue("loginUrl"), HttpDoPostUtils.createNameValuePair(params.toString()));
//        System.out.println(loginResponseText);
//
//    }
//
//
//    /**
//     * 多控制台读取验证码
//     *
//     * @param msg
//     * @return
//     * @throws Exception
//     */
//    private static String readString(String msg) {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            System.out.print(msg + ": ");
//            return bufferedReader.readLine();
//        } catch (Exception e) {
//        }
//        return "1245";
//    }
//
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        //Login login = new Login();
//        //login.doLogin();
//        Login.doLogin();
//    }
//}