package com.yidon.www.controller;

import com.yidon.www.common.Result;
import com.yidon.www.dto.UserDto;
import com.yidon.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:59
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册接口
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){

        return userService.register(userDto);
    }



    // 登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }

    //获取用户信息接口
    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.getUserInfoByToken(token);
    }

    // 登出接口
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return userService.logout(token);
    }

    @GetMapping("/getUserById/{uid}")
    public Result getUserById(@PathVariable("uid")Long uid ){
        return userService.getUserById(uid);
    }


}
