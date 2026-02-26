package com.mrccommunity.ftpmodule.ftp;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import lombok.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


@Getter
@AllArgsConstructor
@ToString
public class FtpClient {
    private static final Logger log = LogManager.getLogger(FtpClient.class);

    private FtpConfig ftpConfig;

    private FTPClient ftp;

    private boolean isLogActivate = true;

    public FtpClient(FtpConfig ftpConfig){
        this.ftpConfig = ftpConfig;
        this.ftp = new FTPClient();
    }

    public void open() throws IOException {

        this.ftp.connect(this.ftpConfig.getServer(),this.ftpConfig.getPort());
        int reply = this.ftp.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new IOException("Erreur lors de la connexion avec le server ftp");
        }

        if(isLogActivate){
            ftp.addProtocolCommandListener(
                    new PrintCommandListener(
                            new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)), true));

        }

        if(!this.ftp.login(this.ftpConfig.getUser(), this.ftpConfig.getPassword()))
            log.error("==> La connexion avec le serveur ftp {} n'a pas ete etablie.",this.ftpConfig.getServer());
        this.ftp.enterLocalPassiveMode();
    }

    public void close() throws IOException {
        ftp.disconnect();
    }

}
