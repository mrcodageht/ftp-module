package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import com.mrccommunity.ftpmodule.ftp.FTPUtils;
import com.mrccommunity.ftpmodule.ftp.FtpClient;
import com.mrccommunity.ftpmodule.ftp.IFtpClient;
import org.apache.commons.net.ftp.FTPClient;

public record FtpFactory(FtpConfig ftpConfig) {

    /**
     * @return : An instance of the FTP utility that will allow you to manipulate the FTP server via predefined
     */
    public IFtpClient getClient(){
        return this.getClient(false);
    }

    /**
     * @param isLogActivate specifies if you want to log the operations done during an open connection
     * @return : An instance of the FTP utility that will allow you to manipulate the FTP server via predefined
     */
    public IFtpClient getClient(boolean isLogActivate){
        FtpClient ftpClient = FtpClient.builder()
                .ftpConfig(ftpConfig)
                .ftp(new FTPClient())
                .isLogActivate(isLogActivate)
                .build();
        return new FTPUtils(ftpClient);
    }
}
