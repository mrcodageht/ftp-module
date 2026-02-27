package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import com.mrccommunity.ftpmodule.ftp.FTPUtils;
import com.mrccommunity.ftpmodule.ftp.FtpClient;
import com.mrccommunity.ftpmodule.ftp.IFtpClient;
import org.apache.commons.net.ftp.FTPClient;

public record FtpFactory(FtpConfig ftpConfig) {
    public IFtpClient getClient(){
        FtpClient ftpClient = FtpClient.builder()
                .ftpConfig(ftpConfig)
                .ftp(new FTPClient())
                .isLogActivate(true)
                .build();
        return new FTPUtils(ftpClient);
    }
}
