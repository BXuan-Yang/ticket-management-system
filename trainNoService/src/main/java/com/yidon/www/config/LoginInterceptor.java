package com.yidon.www.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yidon.www.common.Result;
import com.yidon.www.pojo.User;
import com.yidon.www.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhongxuetao
 * @Description
 * @date
 **/
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final String AUTH_TOKEN_URL = "/user/login";
    private static final String REGISTER_URL = "/user/register";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");


        if(AUTH_TOKEN_URL.equals(requestURI) || REGISTER_URL.equals(requestURI)){
            return true;
        }
        if (token == null){
            Result result = Result.fail( "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        boolean b = checkToken(token);


        if (checkToken(token) == false){
            Result result = Result.fail( "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        // 权限校验
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        Integer userIdentity = (Integer) stringObjectMap.get("userIdentity");
        if(userIdentity == 0){
            String[] split = requestURI.split("\\/");
            if("trainNo".equals(split[1]) ){
                Result result = Result.fail( "没有管理员权限");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JSON.toJSONString(result));
                return false;
            }
        }

        //是登录状态，放行
        return true;
    }

    private boolean checkToken(String token) {

        if(StringUtils.isBlank(token)){
            return false;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return false;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if(StringUtils.isBlank(userJson)){
            return false;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user != null;
    }
}