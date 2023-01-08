package com.yidon.www.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author wuLinTao
 * 时间：2022-11-21 15:59
 */
@Component
public class MybatisPlusObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", java.util.Date.class, new java.util.Date());
        this.strictInsertFill(metaObject, "updateTime", java.util.Date.class, new java.util.Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", java.util.Date.class, new java.util.Date());
    }
}
