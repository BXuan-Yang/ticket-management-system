package com.yidon.www.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yidon.www.common.Result;
import com.yidon.www.constant.HttpConstant;
import com.yidon.www.pojo.OrderTicketInfo;
import com.yidon.www.pojo.ProductTrainInfo;
import com.yidon.www.pojo.SysUserInfo;
import com.yidon.www.pojo.User;
import com.yidon.www.service.OrderTicketInfoService;
import com.yidon.www.service.ProductTrainInfoService;
import com.yidon.www.service.SysUserInfoService;
import com.yidon.www.utils.JWTUtils;
import com.yidon.www.vo.OrderTicketInfoAddVO;
import com.yidon.www.vo.OrderTicketInfoSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private static Long unLoginFail = 0L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 查询用户车票
    @GetMapping("/searchTicket")
    public Result searchTicket(@RequestHeader("Authorization") String token){
        // 进行登录校验
        Long id = getUserId(token);
        if (id == unLoginFail){
            return Result.fail(HttpConstant.HTTP_NO_LOGIN, "用户未登录！");
        }
        // 查询当前登录用户的车票 用户id + 未过期
        QueryWrapper<OrderTicketInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        queryWrapper.eq("is_deleted", 0);
        // 查询数据库
        List<OrderTicketInfo> list = orderTicketInfoService.list(queryWrapper);

        return Result.success(list);
    }

    // 购买车票
    @PostMapping("/addTicket/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result addTicket(@RequestHeader("Authorization") String token,
                            @PathVariable("id") Integer ticketId) throws ParseException {
        // 进行用户校验
        Long user_id = getUserId(token);
        if (user_id == unLoginFail){
            return Result.fail(HttpConstant.HTTP_NO_LOGIN, "用户未登录！");
        }
        // 检查用户是否为有效用户
        SysUserInfo sysUserInfo = sysUserInfoService.getById(user_id);
        if (sysUserInfo == null){
            return Result.fail("用户无效");
        }
        // 检查用户是否已经购买该车票
        QueryWrapper<OrderTicketInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("train_id", ticketId);
        queryWrapper.eq("user_id", user_id);
        OrderTicketInfo one = orderTicketInfoService.getOne(queryWrapper, false);
        if (one != null){
            return Result.fail("该用户已购买该车票！");
        }
        // 新建OrderTicketInfo进行存储
        OrderTicketInfo orderTicketInfo = new OrderTicketInfo();
        // 用户有效，设置用户信息到orderTicketInfo里面
        orderTicketInfo.setUserId(sysUserInfo.getUserId());
        orderTicketInfo.setUserName(sysUserInfo.getUserName());
        // 检查车次信息是否有效
        ProductTrainInfo productTrainInfo = productTrainInfoService.getById(ticketId);
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
        Date res = nowTime();
        orderTicketInfo.setCreateTime(res);
        orderTicketInfo.setUpdateTime(res);
        // 设置逻辑删除
        orderTicketInfo.setIs_deleted(0);
        // 检查是否还有剩余车票
        if (productTrainInfo.getTicketCount() > 0){
            // 对车票库存进行减操作
            productTrainInfo.setTicketCount(productTrainInfo.getTicketCount() - 1);
            // 对车票库进行更新操作
            productTrainInfoService.updateById(productTrainInfo);
            // 对订单进行保存
            orderTicketInfoService.save(orderTicketInfo);
            return Result.success("成功购票");
        }else {
            return Result.fail("票数不足！");
        }
    }

    // 退回车票
    @PostMapping("/deleteTicket/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteTicket(@RequestHeader("Authorization") String token,
                               @PathVariable("id") Integer ticketId) throws ParseException {
        // 检查当前车票信息是否有效
        OrderTicketInfo orderTicketInfo = orderTicketInfoService.getById(ticketId);
        if (orderTicketInfo == null) {
            // 车票信息无效
            return Result.fail(HttpConstant.HTTP_FAIL, "没有当前车票信息，请刷新重新查看");
        }
        //登录校验
        Long userId = getUserId(token);
        if (userId == 0L){
            // 用户未登录
            return Result.fail(HttpConstant.HTTP_NO_LOGIN, "用户未登录！");
        }
        // 对当前车次信息进行校验
        Integer trainId = orderTicketInfo.getTrainId();
        ProductTrainInfo productTrainInfo = productTrainInfoService.getById(trainId);
        if (productTrainInfo == null) {
            // 车次信息无效
            return Result.fail("没有当前车次信息，请重试");
        }
        // 车次信息有效 对数据库进行更新操作
        productTrainInfo.setTicketCount(productTrainInfo.getTicketCount() + 1);
        // 对订单进行时间上的更新
        Date date = nowTime();
        orderTicketInfo.setUpdateTime(date);
        // 对车票余数进行更新
        boolean result = productTrainInfoService.updateById(productTrainInfo);
        // 对车票进行逻辑删除操作
        result &= orderTicketInfoService.removeById(orderTicketInfo);
        if (!result) {
            return Result.fail(HttpConstant.HTTP_FAIL, "数据库操作失败，请联系管理员");
        }
        return Result.success("退票成功");
    }

    // 进行登录检验
    private Long getUserId(String token){
        // 登录校验
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return unLoginFail;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return unLoginFail;
        }
        // 从userJSON中获得User对象
        User user = JSON.parseObject(userJson, User.class);
        // 获取当前登录的用户id
        Long user_id = user.getUserId();
        return user_id;
    }

    // 格式化当前时间
    private Date nowTime() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date date = new Date(1592409600000L);
        String d1= simpleDateFormat.format(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date res = format.parse(d1);
        return res;
    }
}
