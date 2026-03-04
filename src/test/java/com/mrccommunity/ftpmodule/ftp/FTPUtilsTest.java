package com.mrccommunity.ftpmodule.ftp;

import com.mrccommunity.ftpmodule.FtpFactory;
import com.mrccommunity.ftpmodule.config.FtpConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FTPUtilsTest {


    private FakeFtpServer fakeFtpServer;

    IFtpClient ftpClient;

    @BeforeEach
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        FtpConfig ftpConfig = FtpConfig.builder()
                .server("localhost")
                .port(fakeFtpServer.getServerControlPort())
                .user("user")
                .password("password")
                .build();
        FtpFactory factory = new FtpFactory(ftpConfig);

        ftpClient = factory.getClient();
    }

    @AfterEach
    public void teardown() throws IOException {
        fakeFtpServer.stop();
        ftpClient = null;
    }

    @org.junit.jupiter.api.Test
    void upFTP() {
        assertDoesNotThrow(() -> ftpClient.upFTP());
        ftpClient.downFTP();
    }

    @org.junit.jupiter.api.Test
    void downFTP() {
        ftpClient.upFTP();
        assertDoesNotThrow(() -> ftpClient.downFTP());
    }


    @org.junit.jupiter.api.Test
    void uploadingFile_does_not_throw_IO_Exception() throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("baz.txt")).toURI());
        ftpClient.uploadingFile(file, "/data/buz.txt");
        assertTrue(fakeFtpServer.getFileSystem().exists("/data/buz.txt"));
    }
    @org.junit.jupiter.api.Test
    void uploadingFile_does_throw_IO_Exception() throws IOException, URISyntaxException {

        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("baz.txt")).toURI());
        assertThrowsExactly(IOException.class, () -> ftpClient.uploadingFile(file, "/"));
    }
}