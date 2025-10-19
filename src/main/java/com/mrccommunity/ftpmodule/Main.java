package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.config.AppConfig;
import com.mrccommunity.ftpmodule.config.FtpConfig;
import com.mrccommunity.ftpmodule.ftp.IFtpClient;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        var ftpConfig = new FtpConfig(
                dotenv.get("FTP_SERVER"),
                Integer.parseInt(dotenv.get("FTP_PORT")),
                dotenv.get("FTP_USER"),
                dotenv.get("FTP_PASSWORD")
        );

        LOGGER.log(Level.ERROR,"Ftp config : {}",ftpConfig);

        FtpFactory factory = new FtpFactory(ftpConfig);

        IFtpClient ftpClient = factory.getClient();


        LOGGER.error("Appelle de methode upFTP() du client ftp component");
        ftpClient.listFiles("/");
        context.close();
    }
}