package com.yidon.www.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yidon.www.pojo.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:33
 */
@Repository
public interface TicketMapper extends BaseMapper<Ticket> {

}
