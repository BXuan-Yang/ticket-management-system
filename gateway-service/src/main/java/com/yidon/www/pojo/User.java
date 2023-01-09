package com.yidon.www.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:44
 */
@Data
@TableName("sys_user_info")
public class User {

    @TableId(type = IdType.AUTO)
    private Long userId;

    @TableField(value = "login_name")
    private String loginName;

    @TableField(value = "password")
    private String password;


    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_identity")
    private Integer userIdentity;


    //TODO: 添加其他字段

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic()
    @TableField(value = "is_deleted")
    private Integer deleted;
}
