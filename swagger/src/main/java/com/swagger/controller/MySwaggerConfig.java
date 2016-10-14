package com.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableWebMvc
@EnableSwagger
public class MySwaggerConfig extends WebMvcConfigurerAdapter {
	private SpringSwaggerConfig springSwaggerConfig;
	 
    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
        this.springSwaggerConfig = springSwaggerConfig;
    }
 
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
       return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
             .apiInfo(apiInfo())
             .includePatterns(".*")
             .apiVersion("0.0.1")
             .swaggerGroup("user");
    }
     private ApiInfo apiInfo() {
       ApiInfo apiInfo = new ApiInfo(
               "bugkillers-back API",
               "bugkillers 后台API文档",
               "",
               "bugkillers@163.com",
               "My License",
               "My Apps API License URL"
         );
       return apiInfo;
     }
     @Override
     public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
       configurer.enable();
     }
}
