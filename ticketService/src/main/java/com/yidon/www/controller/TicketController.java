package com.yidon.www.controller;

import com.yidon.www.common.Result;
import com.yidon.www.service.OrderTicketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author BXuan
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private OrderTicketInfoService orderTicketInfoService;

    // 查询用户车票
    @GetMapping("/searchTicket")
    public Result searchTicket(@RequestHeader("Authorization") String token){
        return orderTicketInfoService.searchTicket(token);
    }

    // 购买车票
    @PostMapping("/addTicket/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result addTicket(@RequestHeader("Authorization") String token,
                            @PathVariable("id") Integer ticketId) {
        return orderTicketInfoService.addTicket(token, ticketId);
    }

    // 退回车票
    @PostMapping("/deleteTicket/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteTicket(@RequestHeader("Authorization") String token,
                               @PathVariable("id") Integer ticketId) {
        return orderTicketInfoService.deleteTicket(token, ticketId);
    }
}
