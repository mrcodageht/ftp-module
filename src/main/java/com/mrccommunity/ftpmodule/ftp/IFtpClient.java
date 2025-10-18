package com.mrccommunity.ftpmodule.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IFtpClient {

    void upFTP();
    void downFTP();
    Collection<String> listFiles(String path)throws IOException;
    void downloadFile(String f_source, String f_destination)throws IOException;
    void uploadingFile(File file, String path)throws IOException;

    boolean createFolder(String folderName) throws IOException;

    boolean doesFtpDirectoryExist(String directoryPath) throws IOException;
}
