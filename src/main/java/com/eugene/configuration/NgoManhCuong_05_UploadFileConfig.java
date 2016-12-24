package com.eugene.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Ngô Mạnh Cường on 12/2/2016.
 */
/*Cấu hình cho việc upload các hình*/
@Configuration
@ComponentScan
public class NgoManhCuong_05_UploadFileConfig {
  /*Cấu hình dung lượng tối đa của file upload lên*/
  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setMaxFileSize("1MB");
    factory.setMaxRequestSize("1MB");
    return factory.createMultipartConfig();
  }
}
