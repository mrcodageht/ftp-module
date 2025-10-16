package com.mrccommunity.ftpmodule.config;

import com.mrccommunity.ftpmodule.ftp.FTPConfigInterface;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:custom-config.properties")
@Getter
@ToString
public class FtpConfig implements FTPConfigInterface<FtpConfig> {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    @Override
    public FtpConfig getInstance() {
        return this;
    }
}
