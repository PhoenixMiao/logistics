package com.phoenix.logistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {


    @Value("${filePath.company.logo}")
    private String logoPath;

    @Value("${filePath.company.logoUrl}")
    private String logoUrl;

    @Value("${filePath.company.contentFile}")
    private String contentFilePath;

    @Value("${filePath.company.contentFileUrl}")
    private String contentFilePathUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler(logoUrl + "/**")
                .addResourceLocations("file:" + logoPath + "/");
        registry.addResourceHandler(contentFilePathUrl + "/**")
                .addResourceLocations("file:" + contentFilePath + "/");
    }

}
