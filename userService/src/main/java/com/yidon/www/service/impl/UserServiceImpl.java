package com.yidon.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidon.www.common.Result;
import com.yidon.www.constant.HttpConstant;
import com.yidon.www.dto.UserDto;
import com.yidon.www.mapper.UserMapper;
import com.yidon.www.pojo.User;
import com.yidon.www.service.UserService;
import com.yidon.www.utils.JWTUtils;
import com.yidon.www.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private static final String slat = "123456";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(UserDto userDto) {
        String loginName = userDto.getLoginName();
        String password = userDto.getPassword();
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
            return Result.fail(HttpConstant.HTTP_FAIL, "输入参数不合法");
        }
        String pwd = DigestUtils.md5Hex(password + slat);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getLoginName, loginName);
        lambdaQueryWrapper.eq(User::getPassword, pwd);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null){
            return Result.fail(HttpConstant.HTTP_ERROR, "用户不存在");
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(user.getUserId(), user.getLoginName(), user.getUserIdentity());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result getUserInfoByToken(String token) {
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(HttpConstant.HTTP_NO_LOGIN, "未登录");
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(HttpConstant.HTTP_NO_LOGIN, "未登录");
        }
        User user = JSON.parseObject(userJson, User.class);
        UserVo loginUserVo = new UserVo();
        loginUserVo.setLoginName(user.getLoginName());
        loginUserVo.setUserId(user.getUserId());
        loginUserVo.setUserName(user.getUserName());
        return Result.success(loginUserVo);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(UserDto userDto) {
        String loginName = userDto.getLoginName();
        String password = userDto.getPassword();
        String userName = userDto.getUserName();
        if (StringUtils.isBlank(loginName)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(userName)
        ){
            return Result.fail(HttpConstant.HTTP_FAIL,"输入参数有误");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getLoginName, loginName);
        User sysUser = this.userMapper.selectOne(lambdaQueryWrapper);
        if (sysUser != null){
            return Result.fail(HttpConstant.HTTP_FAIL, "用户登录名已存在");
        }
        sysUser = new User();
        sysUser.setLoginName(loginName);
        sysUser.setUserName(userName);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUserIdentity(0); //0为用户
        sysUser.setDeleted(0); //
        this.userMapper.insert(sysUser);

        //token
        String token = JWTUtils.createToken(sysUser.getUserId(), sysUser.getLoginName(), sysUser.getUserIdentity());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result getUserById(Long uid) {
        User user = userMapper.selectById(uid);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.fail("用户不存在");
        }
    }

    @Override
    public Result editor(UserDto userDto) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        User updateUser = new User();
        updateUser.setUserId(userDto.getUserId());
        User user = userMapper.selectById(userDto.getUserId());
        //输入登录名把不为空
        if(StringUtils.isNotBlank(userDto.getLoginName())){
            // 获取登录名
            String loginName = userDto.getLoginName();
            // 登录名和原来的登录名不一致
            if(!loginName.equals(user.getLoginName())){
                LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(User::getLoginName, loginName);
                User user1 = userMapper.selectOne(lambdaQueryWrapper);
                if(user1 != null){
                    return Result.fail(HttpConstant.HTTP_HAS_LOGINNAME, "登录名已存在");
                }
                lambdaUpdateWrapper.eq(User::getLoginName, user.getLoginName());
                updateUser.setLoginName(loginName);
            }

        }
        // 输入username不为空
        if(StringUtils.isNotBlank(userDto.getUserName())){
            lambdaUpdateWrapper.eq(User::getUserName, user.getUserName());
            updateUser.setUserName(userDto.getUserName());
        }
        // 输入密码不为空
        if(StringUtils.isNotBlank(userDto.getPassword())){
            lambdaUpdateWrapper.eq(User::getPassword, user.getPassword());
            updateUser.setPassword(DigestUtils.md5Hex(userDto.getPassword()+slat));
        }
        userMapper.update(updateUser, lambdaUpdateWrapper);

        return Result.success("更新成功");
    }

}
