package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.component.FtpComponent;
import com.mrccommunity.ftpmodule.config.AppConfig;
import com.mrccommunity.ftpmodule.config.FtpConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        var ftpConfig = context.getBean(FtpConfig.class);
        LOGGER.log(Level.ERROR,"Ftp config : {}",ftpConfig);
        var ftpComp = new FtpComponent(ftpConfig);
        LOGGER.error("Appelle de methode upFTP() du ftp component");
        ftpComp.upFTP();

        context.close();
    }
}