package com.slxy.terweb.config;

/**
 * @author: sherlock
 * @description:
 * @date: 2020/1/14 16:10
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // super.addViewControllers(registry);
//        //浏览器发送 /atguigu 请求来到 success
//        registry.addViewController("/atguigu").setViewName("success");
//    }

    //所有的WebMvcConfigurer组件都会一起起作用
    @Bean //将组件注册在容器
    public WebMvcConfigurer WebMvcConfigurer() {
        WebMvcConfigurer adapter = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }
        };
        return adapter;
    }

//    @Bean
//    public LocaleResolver localeResolver(){
//        return new MyLocaleResolver();
//    }
}

