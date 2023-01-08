package com.yidon.www.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:40
 */
@Data
@TableName("product_train_info")
public class Ticket {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //TODO: 添加其他字段

    @TableField(fill = FieldFill.INSERT)
    private java.util.Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.util.Date updateTime;

    @TableLogic
    private Integer isDeleted;

    private String lpNum;

    private String dpStation;

    private String stopStation;

    private String arrStation;

    private Integer cap;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date dpTime;

    private String frequeny;

    private Integer price;

    private Integer ticketCount;

}
