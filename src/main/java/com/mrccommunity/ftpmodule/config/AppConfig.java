package com.mrccommunity.ftpmodule.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:custom-config.properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ComponentScan(basePackages = "com.mrccommunity")
public class AppConfig {
    private String fullName;
    @Value("${app.name}")
    private String name;
    private String version;

//    @Bean
//    public FtpConfig getFtpConfig(){
//        return new FtpConfig();
//    }
}
