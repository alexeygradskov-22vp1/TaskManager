package ru.psu.fvt.moipevm.taskmanager.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNullApi;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/admin/blockUser/{userID}").setViewName("blockUser");
        registry.addViewController("/admin/deleteUser/{userID}").setViewName("deleteUser");
        registry.addViewController("/boards/create").setViewName("createBoard");
        registry.addViewController("/boards/{boardID}").setViewName("boardPage");
        registry.addViewController("/pageNotFound").setViewName("pageNotFound");
        registry.addViewController("/boards/delete/{boardID}").setViewName("deleteBoard");
        registry.addViewController("/deleteAccount/{userID}").setViewName("deleteAccount");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/js/**").
                addResourceLocations("classpath:/static/js/");
        registry.
                addResourceHandler("/css/**").
                addResourceLocations("classpath:/static/css/css/");
    }
}
