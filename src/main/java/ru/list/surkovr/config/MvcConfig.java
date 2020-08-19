package ru.list.surkovr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("loginPage");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Все запросы на img буду перенаправлены по протоколу файла на путь аплоадПас
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/");
        // Classpath означает что ресурсы ищутся не в файловой системе, а в дереве проекта от корня
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}