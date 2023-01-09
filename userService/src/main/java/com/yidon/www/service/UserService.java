package com.yidon.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yidon.www.common.Result;
import com.yidon.www.dto.UserDto;
import com.yidon.www.pojo.User;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:57
 */
public interface UserService  extends IService<User> {

    Result login(UserDto userDto);

    Result getUserInfoByToken(String token);

    Result logout(String token);

    Result register(UserDto userDto);

    Result getUserById(Long uid);
}
