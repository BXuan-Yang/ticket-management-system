package com.yidon.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yidon.www.mapper.TrainNoMapper;
import com.yidon.www.pojo.TrainNo;
import com.yidon.www.service.TrainNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:56
 */
@Service
public class TrainNoServiceImpl extends ServiceImpl<TrainNoMapper, TrainNo> implements TrainNoService {

    @Autowired
    private TrainNoMapper trainNoMapper;

    public void add(TrainNo trainNo) {
        trainNoMapper.insert(trainNo);
    }

    public void deleteById(Integer id) {
        trainNoMapper.deleteById(id);
    }

    public boolean updateById(TrainNo trainNo) {
        int result = trainNoMapper.updateById(trainNo);
        return result > 0;
    }

    public TrainNo selectById(Integer id) {
        return trainNoMapper.selectById(id);
    }

    public List<TrainNo> selectAll() {
        return trainNoMapper.selectList(null);
    }
}
