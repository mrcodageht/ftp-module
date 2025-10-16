package com.mrccommunity.ftpmodule.component;



import com.mrccommunity.ftpmodule.config.FtpTempConfig;
import com.mrccommunity.ftpmodule.ftp.FTPInterface;
import com.mrccommunity.ftpmodule.ftp.FTPUtils;
import com.mrccommunity.ftpmodule.ftp.FtpClient;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FtpTempComponent implements FTPInterface {
    private final FTPUtils utils;

    public FtpTempComponent(FtpTempConfig ftpConfig){
        FtpClient ftp = new FtpClient();
        ftp.setServer(ftpConfig.getServer());
        ftp.setPort(ftpConfig.getPort());
        ftp.setUser(ftpConfig.getUser());
        ftp.setPassword(ftpConfig.getPassword());
        this.utils = new FTPUtils(ftp);
    }

    @Override
    public void upFTP(){
        utils.upFTP();
    }
    @Override
    public void downFTP(){
        this.utils.downFTP();
    }

    @Override
    public Collection<String> listFiles(String path) throws IOException {
        return this.utils.listFiles(path);
    }
    @Override
    public void downloadFile(String f_source, String f_destination) throws IOException {
        this.utils.downloadFile(f_source, f_destination);
    }
    @Override
    public void uploadingFile(File file, String path) throws IOException {
        this.utils.uploadingFile(file,path);
    }

    @Override
    public boolean createFolder(String folderName) throws IOException {
        return utils.createFolder(folderName);
    }

    @Override
    public boolean doesFtpDirectoryExist(String directoryPath) throws IOException {
        return utils.doesFtpDirectoryExist(directoryPath);
    }

}
