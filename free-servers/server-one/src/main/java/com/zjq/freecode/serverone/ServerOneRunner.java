package com.zjq.freecode.serverone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.zjq.freecode.serverone.dao")
@SpringBootApplication
@ComponentScan({"com.zjq.freecode.common","com.zjq.freecode.serverone"})
public class ServerOneRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServerOneRunner.class,args);
    }

}
