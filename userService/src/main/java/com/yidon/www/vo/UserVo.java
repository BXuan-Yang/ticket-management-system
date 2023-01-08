package com.yidon.www.vo;

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
public class UserVo {
    private Long userId;

    private String loginName;

    private String userName;

    private Integer userIdentity;
}
