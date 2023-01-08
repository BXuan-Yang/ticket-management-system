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
public class OrderTicketInfoSearchVO implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 到达站
     */
    @TableField("arr_station")
    private String arrStation;

    /**
     * 始发站
     */
    @TableField("dp_station")
    private String dpStation;

    /**
     * 车牌号
     */
    @TableField("lp_num")
    private String lpNum;

    /**
     * 车次id
     */
    @TableField("train_id")
    private Integer trainId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 票价
     */
    @TableField("price")
    private Integer price;

    /**
     * 序列化id
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}