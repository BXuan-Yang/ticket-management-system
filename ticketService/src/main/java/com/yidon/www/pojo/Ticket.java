package com.yidon.www.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:40
 */
@Data
public class Ticket {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //TODO: 添加其他字段

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;

}
