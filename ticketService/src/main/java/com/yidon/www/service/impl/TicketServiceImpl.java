//package com.yidon.www.service.impl;
//
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.yidon.www.mapper.TicketMapper;
//import com.yidon.www.pojo.Ticket;
//import com.yidon.www.service.TicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author wuLinTao
// * 时间：2023-01-06 12:33
// */
//@Service
//@Transactional
//public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {
//
//    @Autowired
//    private TicketMapper ticketMapper;
//
//    @Override
//    public void add(Ticket ticket) {
//        ticketMapper.insert(ticket);
//    }
//
//    @Override
//    public void deleteById(Integer id) {
//        ticketMapper.deleteById(id);
//    }
//    @Override
//    public boolean updateById(Ticket ticket) {
//        int result = ticketMapper.updateById(ticket);
//        return result > 0;
//    }
//
//    @Override
//    public Ticket selectById(Integer id) {
//        return ticketMapper.selectById(id);
//    }
//
//    public List<Ticket> selectAll() {
//        return ticketMapper.selectList(null);
//    }
//}
