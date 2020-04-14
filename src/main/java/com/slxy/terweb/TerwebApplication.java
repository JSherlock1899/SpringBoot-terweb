package com.slxy.terweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
@MapperScan(basePackages = "com.slxy.terweb.mapper")
public class TerwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerwebApplication.class, args);
    }

}
