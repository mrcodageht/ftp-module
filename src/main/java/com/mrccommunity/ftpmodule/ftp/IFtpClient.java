package com.mrccommunity.ftpmodule.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public sealed interface IFtpClient permits FTPUtils {

    void upFTP();


    void downFTP();

    /**
     *
     * @param path : The path of files to list
     * @return collection with the filename and it sizes
     * @throws IOException
     */
    Collection<Map<String, String>> listFiles(String path)throws IOException;

    /**
     *
     * @param f_source The path of the file on the ftp server
     * @param f_destination The path where you want to put the file to download
     * @throws IOException
     */
    void downloadFile(String f_source, String f_destination)throws IOException;

    /**
     *
     * @param file : The file to save
     * @param path The path where you want to save file
     * @throws IOException
     */
    void uploadingFile(File file, String path)throws IOException;

    /**
     *
     * @param folderName the foldername to create
     * @return true if the folder has created successfully
     * @throws IOException
     */
    boolean createFolder(String folderName) throws IOException;

    /**
     *
     * @param directoryPath The dictory path to check
     * @return return a boolean true or false
     * @throws IOException
     */
    boolean doesFtpDirectoryExist(String directoryPath) throws IOException;

    /**
     *
     * @param filename
     * @return
     */
    boolean removeFile(String filename);
}
