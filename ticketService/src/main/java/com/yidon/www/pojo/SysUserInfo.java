package com.yidon.www.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_user_info
 */
@TableName(value ="sys_user_info")
@Data
public class SysUserInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @TableField("user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 
     */
    @TableField("password")
    private String password;

    /**
     * 
     */
    @TableField("user_name")
    private String userName;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField("user_identity")
    private Integer userIdentity;

    /**
     * 
     */
    @TableLogic
    private Integer is_deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}