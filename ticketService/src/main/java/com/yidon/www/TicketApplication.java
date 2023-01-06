package com.yidon.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuLinTao
 * 时间：2023-01-06 12:32
 */
@SpringBootApplication
@MapperScan("com.yidon.www.mapper")
public class TicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketApplication.class,args);
    }
}
