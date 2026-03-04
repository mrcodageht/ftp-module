package com.mrccommunity.ftpmodule.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public record FTPUtils(FtpClient ftp) implements IFtpClient {
    private static final Logger log = LogManager.getLogger(FTPUtils.class);

    @Override
    public void upFTP() {
        try {
            this.ftp.open();
            this.ftp.getFtp().setFileType(FTP.BINARY_FILE_TYPE);
            log.info("Connexion avec le serveur ftp");
        } catch (IOException e) {
            log.error("e: ", e);
            log.error("Erreur lors de la connexion : {}", e.getMessage());
        }
    }

    @Override
    public void downFTP() {
        try {
            this.ftp.close();
            log.info("Deconnexion avec le serveur ftp");
        } catch (IOException e) {
            log.error("Error lors de la deconnexion : {}", e.getMessage());
        }
    }

    /**
     *
     * @param path : The path of files to list
     * @return collection with the filename and it sizes
     * @throws IOException
     */
    @Override
    public Collection<Map<String, String>> listFiles(String path) throws IOException {
        upFTP();
        FTPFile[] files = this.ftp.getFtp().listFiles(path);
        downFTP();
        return Arrays.stream(files)
                .filter(FTPFile::isFile)
                .map(f -> Map.of("name", f.getName(), "size", String.valueOf(f.getSize())))
                .toList();
    }

    /**
     *
     * @param f_source The path of the file on the ftp server
     * @param f_destination The path where you want to put the file to download
     * @throws IOException
     */
    @Override
    public void downloadFile(String f_source, String f_destination) throws IOException {
        upFTP();
        try(FileOutputStream fos = new FileOutputStream(f_destination)) {
            this.ftp.getFtp().retrieveFile(f_source, fos);
        }finally {
            downFTP();
        }
    }

    /**
     *
     * @param file : The file to save
     * @param path The path where you want to save file
     * @throws IOException
     */
    @Override
    public void uploadingFile(File file, String path) throws IOException {
        log.info("Taille fichier à envoyer : {}", file.length());
        upFTP();

        try (FileInputStream fis = new FileInputStream(file)) {
            boolean result = this.ftp.getFtp().storeFile(path, fis);
            if (!result) {
                throw new IOException("Échec du transfert FTP du fichier : " + file.getName());
            }
            log.info("Fichier FTP envoyé avec succès : {} ({} bytes)", file.getName(), file.length());
        } catch (IOException e) {
            log.error("Erreur lors de l'envoi du fichier {} : {}", file.getName(), e.getMessage());
            throw e;
        } finally {
            downFTP();
        }
    }

    /**
     *
     * @param folderName the foldername to create
     * @return true if the folder has created successfully
     * @throws IOException
     */
    @Override
    public boolean createFolder(String folderName) throws IOException {
        upFTP();
        String newDirectoryPath = "/";
        boolean isCreated = this.ftp.getFtp()
                .makeDirectory(newDirectoryPath + File.separator + folderName);
        downFTP();
        return isCreated;
    }

    /**
     *
     * @param directoryPath The dictory path to check
     * @return return a boolean trur or false
     * @throws IOException
     */
    @Override
    public boolean doesFtpDirectoryExist(String directoryPath) throws IOException {

        upFTP();
        if (this.ftp.getFtp().changeWorkingDirectory(directoryPath)) {
            this.ftp.getFtp().changeWorkingDirectory("/");
            downFTP();
            return true;
        }
        downFTP();
        return false;
    }
}
