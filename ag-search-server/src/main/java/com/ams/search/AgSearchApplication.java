package com.ams.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.ams.search.dao.*")
public class AgSearchApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AgSearchApplication.class, args);
    }
        @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
            return builder.sources(AgSearchApplication.class);
        }

}
