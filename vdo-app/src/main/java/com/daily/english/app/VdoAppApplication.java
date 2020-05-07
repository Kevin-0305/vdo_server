package com.daily.english.app;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Slf4j
@EnableAsync
@ServletComponentScan
@MapperScan("com.daily.english.app.mapper")
@SpringBootApplication(scanBasePackages = "com.daily.english.app")
@ImportResource("classpath:transaction.xml")
public class VdoAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VdoAppApplication.class);
        log.warn("API Service completed startup!");
    }

    /**
     * 实现SpringBootServletInitializer可以让spring-boot项目在web容器中运行
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        SpringApplicationBuilder springApplicationBuilder = builder.sources(this.getClass());
        return springApplicationBuilder;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setPath("/");
        super.onStartup(servletContext);
    }
}
