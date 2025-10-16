package com.mrccommunity.ftpmodule.ftp;

import lombok.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FtpClient {
    private static final Logger log = LogManager.getLogger(FtpClient.class);
    @Getter
    @Setter
    private String server;
    @Getter
    @Setter
    private Integer port;
    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private FTPClient ftp;

    public void open() throws IOException {
        this.ftp = new FTPClient();
        ftp.addProtocolCommandListener(
                new PrintCommandListener(
                        new PrintWriter(System.out)
                )
        );
        log.info("Informations de login : {}",this);

        this.ftp.connect(this.server,this.port);
        int reply = this.ftp.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new IOException("Erreur lors de la connexion avec le server ftp");
        }

        if(!this.ftp.login(this.user,this.password)) log.error("==> La connexion avec le serveur ftp {} n'a pas ete etablie.",this.server);
        this.ftp.enterLocalPassiveMode();
    }

    public void close() throws IOException {
        ftp.disconnect();
    }
}
