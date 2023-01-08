package com.yidon.www.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String jwtToken = "123456";

    public static String createToken(Long userId, String loginName, Integer userIdentity){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("loginName", loginName);
        claims.put("userIdentity", userIdentity);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
//        Map<String, Object> map = checkToken("eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbk5hbWUiOiJ0ZXN0MyIsInVzZXJJZGVudGl0eSI6MCwiZXhwIjoxNjczOTY5OTg3LCJ1c2VySWQiOjMsImlhdCI6MTY3MzA4MDk1NH0.WVbtNCs_W_4XbJcSmqGOkuBT8PvEPOoBVPScnjF44fA");
//        System.out.println(map.get("loginName"));

        String str3 = "/user/test";
        String[] split = str3.split("\\/");
        String result = split[1];
        System.out.println("result:"+result);
    }

}