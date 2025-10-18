package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import com.mrccommunity.ftpmodule.ftp.FTPUtils;
import com.mrccommunity.ftpmodule.ftp.FtpClient;
import com.mrccommunity.ftpmodule.ftp.IFtpClient;

public record FtpFactory(FtpConfig ftpConfig) {
    public IFtpClient getClient(){
        FtpClient ftpClient = new FtpClient(this.ftpConfig);
        return new FTPUtils(ftpClient);
    }
}
