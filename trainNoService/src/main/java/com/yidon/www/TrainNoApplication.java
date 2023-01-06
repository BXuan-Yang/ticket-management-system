package com.yidon.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:45
 */
@SpringBootApplication
@MapperScan("com.yidon.www.mapper")
public class TrainNoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainNoApplication.class,args);
    }

}
