package com.yidon.www.controller;

import com.yidon.www.service.TrainNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:56
 */
@RestController
@RequestMapping("/trainNo")
public class TrainNoController {

    @Autowired
    private TrainNoService trainNoService;
}
