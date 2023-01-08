package com.yidon.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yidon.www.pojo.TrainNo;

import java.util.List;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:55
 */
public interface TrainNoService extends IService<TrainNo> {
    void add(TrainNo trainNo);

    void deleteById(Integer id);

    boolean updateById(TrainNo trainNo);

    TrainNo selectById(Integer id);

    List<TrainNo> selectAll();
}
