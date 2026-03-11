package com.mrccommunity.ftpmodule.ftp;

import org.apache.commons.net.ftp.FTPFile;

public record FtpFile(
        String name,
        Long size,
        boolean isFile
) {

    static FtpFile mapToFtpFile(FTPFile ftpFile){
        return new FtpFile(
                ftpFile.getName(),
                ftpFile.getSize(),
                ftpFile.isFile()
        );
    }
}
