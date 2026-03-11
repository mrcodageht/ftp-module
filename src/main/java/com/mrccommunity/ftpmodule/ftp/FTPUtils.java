package com.mrccommunity.ftpmodule.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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

    @Override
    public Collection<FtpFile> listFiles(String path) throws IOException {
        try {
            upFTP();
            FTPFile[] files = ftp.getFtp().listFiles(path);
            return Arrays.stream(files)
                    .map(FtpFile::mapToFtpFile)
                    .toList();
        } finally {
            downFTP();
        }
    }

    @Override
    public void downloadFile(String f_source, String f_destination) throws IOException {
        upFTP();
        try(FileOutputStream fos = new FileOutputStream(f_destination)) {
            this.ftp.getFtp().retrieveFile(f_source, fos);
        }finally {
            downFTP();
        }
    }


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



    @Override
    public boolean createFolder(String folderName) throws IOException {
        upFTP();
        String newDirectoryPath = "/";
        boolean isCreated = this.ftp.getFtp()
                .makeDirectory(newDirectoryPath + File.separator + folderName);
        downFTP();
        return isCreated;
    }


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

    @Override
    public boolean removeFile(String filename) {
        return false;
    }
}
