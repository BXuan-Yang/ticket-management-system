package com.yidon.www.service.impl;


import com.yidon.www.common.Result;
import com.yidon.www.exception.GlobalException;
import com.yidon.www.mapper.TicketMapper;
import com.yidon.www.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:33
 */
@Service
public class TicketServiceImpl  implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;
}
