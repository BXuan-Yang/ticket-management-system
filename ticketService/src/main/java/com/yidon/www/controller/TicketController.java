package com.yidon.www.controller;

import com.yidon.www.common.Result;
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
            return Result.fail("新增失败");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id) {
        try {
            ticketService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }

    @PutMapping("/updateById/{id}")
    public Result updateById(@RequestBody Ticket ticket) {
        try {
            ticketService.updateById(ticket);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新失败");
        }
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable(value = "id") Integer id) {
        try {
            Ticket ticket = ticketService.selectById(id);
            return Result.success(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询用户数据失败");
        }
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        try {
            List<Ticket> list = ticketService.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }
}
