package com.yidon.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidon.www.mapper.UserMapper;
import com.yidon.www.pojo.User;
import com.yidon.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


}
