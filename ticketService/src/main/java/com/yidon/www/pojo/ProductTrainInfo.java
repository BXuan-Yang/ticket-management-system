package com.yidon.www.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName product_train_info
 */
@TableName(value ="product_train_info")
@Data
public class ProductTrainInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("lp_num")
    private String lpNum;

    /**
     * 
     */
    @TableField("dp_station")
    private String dpStation;

    /**
     * 
     */
    @TableField("stop_station")
    private String stopStation;

    /**
     * 
     */
    @TableField("arr_station")
    private String arrStation;

    /**
     * 
     */
    @TableField("cap")
    private Integer cap;

    /**
     * 
     */
    @TableField("dp_time")
    private Date dpTime;

    /**
     * 
     */
    @TableField("frequeny")
    private String frequeny;

    /**
     * 
     */
    @TableField("price")
    private Integer price;

    /**
     * 
     */
    @TableField("ticket_count")
    private Integer ticketCount;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_Time")
    private Date updateTime;

    /**
     * 
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}