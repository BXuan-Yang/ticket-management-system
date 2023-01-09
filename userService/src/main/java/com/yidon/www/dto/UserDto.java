package com.yidon.www.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhongxuetao
 * @Description
 * @date
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;

    private String loginName;

    private String userName;

    private String password;
}
