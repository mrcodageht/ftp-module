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
public class FtpTempConfig implements FTPConfigInterface<FtpTempConfig> {

    @Value("${ftp.temp.server}")
    private String server;

    @Value("${ftp.temp.port}")
    private Integer port;

    @Value("${ftp.temp.user}")
    private String user;

    @Value("${ftp.temp.password}")
    private String password;

    @Override
    public FtpTempConfig getInstance() {
        return this;
    }
}
