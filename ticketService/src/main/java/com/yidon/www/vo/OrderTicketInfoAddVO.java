package com.yidon.www.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName order_ticket_info
 */
@TableName(value ="order_ticket_info")
@Data
public class OrderTicketInfoAddVO implements Serializable {

    /**
     * 车次id
     */
    private Integer train_id;

    /**
     * 用户id
     */
    private Integer user_id;

    /**
     * 序列化id
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}