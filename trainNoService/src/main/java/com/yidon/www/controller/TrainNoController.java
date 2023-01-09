package com.yidon.www.controller;

import com.yidon.www.common.Result;
import com.yidon.www.constant.HttpConstant;
import com.yidon.www.pojo.TrainNo;
import com.yidon.www.service.TrainNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:56
 */
@RestController
@RequestMapping("/trainNo")
public class TrainNoController {

    @Autowired
    private TrainNoService trainNoService;

    @PostMapping("/add")
    public Result add(@RequestBody TrainNo trainNo) {
        try {
            trainNoService.add(trainNo);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(HttpConstant.HTTP_FAIL, "新增失败");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id){
        TrainNo trainNo = trainNoService.selectById(id);
        if (trainNo != null) {
            trainNoService.deleteById(id);
            return Result.success();
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "删除失败");
        }
    }

    @PutMapping("/updateById/{id}")
    public Result updateById(@RequestBody TrainNo trainNo){
        boolean res = trainNoService.updateById(trainNo);
        if (res) {
            return Result.success(trainNo);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "更新失败");
        }
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable(value = "id") Integer id){
        TrainNo trainNo = trainNoService.selectById(id);
        if (trainNo != null) {
            return Result.success(trainNo);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "查询失败");
        }
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<TrainNo> list = trainNoService.selectAll();
        if (list != null) {
            return Result.success(list);
        } else {
            return Result.fail(HttpConstant.HTTP_FAIL, "查询失败，系统无数据");
        }
    }
}
