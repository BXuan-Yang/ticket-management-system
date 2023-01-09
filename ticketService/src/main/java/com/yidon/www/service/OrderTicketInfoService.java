package com.yidon.www.service;

import com.yidon.www.common.Result;
import com.yidon.www.pojo.OrderTicketInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yidon.www.vo.OrderTicketInfoSearchVO;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
* @author BXuan
*/
public interface OrderTicketInfoService extends IService<OrderTicketInfo> {

    /**
     * 购买车票
     * @param token
     * @param ticketId
     * @return
     */
    public Result addTicket(String token, Integer ticketId);

    /**
     * 退回车票
     * @param token
     * @param ticketId
     * @return
     */
    public Result deleteTicket(String token, Integer ticketId);

    /**
     * 查询车票
     * @param token
     * @return
     */
    public Result searchTicket(String token);

    /**
     * Redis获取锁
     * @param ticketId
     * @return boolean
     */
    public boolean getLock(Integer ticketId);

    /**
     * 释放锁
     * 释放锁就等于将对应的锁key 在缓存中删除即可
     * @return
     */
    public void deleteLock(Integer je);
}
