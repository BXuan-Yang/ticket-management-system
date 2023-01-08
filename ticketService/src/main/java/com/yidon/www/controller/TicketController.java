package com.yidon.www.controller;

import com.yidon.www.common.Result;
import com.yidon.www.constant.HttpConstant;
import com.yidon.www.pojo.Ticket;
import com.yidon.www.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:34
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/add")
    public Result add(@RequestBody Ticket ticket) {
        try {
            ticketService.add(ticket);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(HttpConstant.HTTP_FAIL, "新增失败");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id) throws RuntimeException{
        Ticket ticket = ticketService.selectById(id);
        if (ticket != null) {
            ticketService.deleteById(id);
            return Result.success(ticket);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "删除失败,该用户不存在");
        }
    }

    @PutMapping("/updateById/{id}")
    public Result updateById(@RequestBody Ticket ticket) throws RuntimeException{
        boolean res = ticketService.updateById(ticket);
        if (res != false) {
            return Result.success(ticket);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "更新失败，该用户不存在");
        }
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable(value = "id") Integer id) throws RuntimeException {
        Ticket ticket = ticketService.selectById(id);
        if (ticket != null) {
            return Result.success(ticket);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "查询失败，该用户不存在");
        }
    }

    @GetMapping("/selectAll")
    public Result selectAll() throws RuntimeException {
        List<Ticket> list = ticketService.selectAll();
        if (list != null) {
            return Result.success(list);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "查询失败，系统无数据");
        }
    }
}
