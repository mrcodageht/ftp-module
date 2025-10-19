package com.mrccommunity.ftpmodule.ftp;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import lombok.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;


@Getter
@AllArgsConstructor
@ToString
public class FtpClient {
    private static final Logger log = LogManager.getLogger(FtpClient.class);

    private FtpConfig ftpConfig;

    private FTPClient ftp;

    public FtpClient(FtpConfig ftpConfig){
        this.ftpConfig = ftpConfig;
        this.ftp = new FTPClient();
    }

    public void open() throws IOException {

        this.ftp.connect(this.ftpConfig.server(),this.ftpConfig.port());
        int reply = this.ftp.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new IOException("Erreur lors de la connexion avec le server ftp");
        }

        if(!this.ftp.login(this.ftpConfig.user(), this.ftpConfig.password())) log.error("==> La connexion avec le serveur ftp {} n'a pas ete etablie.",this.ftpConfig.server());
        this.ftp.enterLocalPassiveMode();
    }

    public void close() throws IOException {
        ftp.disconnect();
    }

}
