package com.yidon.www.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yidon.www.common.Result;
import com.yidon.www.pojo.OrderTicketInfo;
import com.yidon.www.pojo.ProductTrainInfo;
import com.yidon.www.pojo.SysUserInfo;
import com.yidon.www.service.OrderTicketInfoService;
import com.yidon.www.service.ProductTrainInfoService;
import com.yidon.www.service.SysUserInfoService;
import com.yidon.www.vo.OrderTicketInfoAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static com.yidon.www.constant.HttpConstant.HTTP_ERROR;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:34
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private OrderTicketInfoService orderTicketInfoService;
    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private ProductTrainInfoService productTrainInfoService;

    // 查询用户车票
    @GetMapping("/searchTicket/{id}")
    private Result searchTicket(@PathVariable Integer id){
        QueryWrapper<OrderTicketInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<OrderTicketInfo> list = orderTicketInfoService.list(queryWrapper);
        return Result.success(list);
    }

    // 购买车票
    @PostMapping("/addTicket")
    private Result addTicket(@RequestBody OrderTicketInfoAddVO orderTicketInfoAddVO) throws ParseException {
        OrderTicketInfo orderTicketInfo = new OrderTicketInfo();
        // 检查用户是否为有效用户
        Integer user_id = orderTicketInfoAddVO.getUser_id();
        SysUserInfo sysUserInfo = sysUserInfoService.getById(user_id);
        if (sysUserInfo == null){
            return Result.fail("用户无效");
        }
        // 检查用户是否已经购买该车票
        QueryWrapper<OrderTicketInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_id", orderTicketInfoAddVO.getTrain_id());
        queryWrapper.eq("user_id", orderTicketInfoAddVO.getUser_id());
        OrderTicketInfo one = orderTicketInfoService.getOne(queryWrapper, false);
        if (one != null){
            return Result.fail("该用户已购买该车票！");
        }
        // 用户有效，设置用户信息到orderTicketInfo里面
        orderTicketInfo.setUserId(sysUserInfo.getUserId());
        orderTicketInfo.setUserName(sysUserInfo.getUserName());
        // 检查车次信息是否有效
        Integer train_id = orderTicketInfoAddVO.getTrain_id();
        ProductTrainInfo productTrainInfo = productTrainInfoService.getById(train_id);
        if (productTrainInfo == null){
            return Result.fail("车次不存在");
        }
        // 车次有效，将车次信息设置到orderTicketInfo里面
        orderTicketInfo.setArrStation(productTrainInfo.getArrStation());
        orderTicketInfo.setDpStation(productTrainInfo.getDpStation());
        orderTicketInfo.setLpNum(productTrainInfo.getLpNum());
        orderTicketInfo.setTrainId(productTrainInfo.getId());
        orderTicketInfo.setPrice(productTrainInfo.getPrice());
        // 设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date date = new Date(1592409600000L);
        String d1= simpleDateFormat.format(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date res = format.parse(d1);
        orderTicketInfo.setCreateTime(res);
        orderTicketInfo.setUpdateTime(res);
        orderTicketInfo.setIs_deleted(0);

        // 检查是否还有剩余车票
        if (productTrainInfo.getCap() > 0){
            productTrainInfo.setCap(productTrainInfo.getCap() - 1);
            productTrainInfoService.updateById(productTrainInfo);
            orderTicketInfoService.save(orderTicketInfo);
            return Result.success("成功购票");
        }else {
            return Result.fail("票数不足！");
        }
    }

    // 退回车票
    @PostMapping("/deleteTicket/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteTicket(@PathVariable("id") Integer id) {
        OrderTicketInfo orderTicketInfo = orderTicketInfoService.getById(id);
        if (orderTicketInfo == null) {
            return Result.fail("没有当前车票信息，请刷新重新查看");
        }
        //TODO 用户校验
        Integer userId = orderTicketInfo.getUserId();

        Integer trainId = orderTicketInfo.getTrainId();
        ProductTrainInfo productTrainInfo = productTrainInfoService.getById(trainId);
        if (productTrainInfo == null) {
            return Result.fail("没有当前车次信息，请重试");
        }
        productTrainInfo.setTicketCount(productTrainInfo.getTicketCount() + 1);
        orderTicketInfo.setUpdateTime(new Date());
        boolean result = productTrainInfoService.updateById(productTrainInfo);
        result &= orderTicketInfoService.removeById(orderTicketInfo);
        if (!result) {
            return Result.fail(HTTP_ERROR, "数据库操作失败，请联系管理员");
        }
        return Result.success("退票成功");
    }
}
