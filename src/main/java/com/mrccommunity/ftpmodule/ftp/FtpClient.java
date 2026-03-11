package com.mrccommunity.ftpmodule.ftp;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import lombok.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


@Getter
@Builder
@ToString
public class FtpClient {

    private static final Logger log = LogManager.getLogger(FtpClient.class);

    private FtpConfig ftpConfig;

    private FTPClient ftp;

    private boolean isLogActivate;

    public void open() throws IOException {

        this.ftp.connect(this.ftpConfig.getServer(),this.ftpConfig.getPort());
        int reply = this.ftp.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new IOException("Erreur lors de la connexion avec le server ftp");
        }

        if(isLogActivate){
            ftp.addProtocolCommandListener(
                new ProtocolCommandListener(){

                    @Override
                    public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
                        System.out.printf("[%s][%d] Command sent : [%s]-%s", Thread.currentThread().getName(),
                                System.currentTimeMillis(), protocolCommandEvent.getCommand(),
                                protocolCommandEvent.getMessage());
                    }

                    @Override
                    public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
                        System.out.printf("[%s][%d] Reply received : %s", Thread.currentThread().getName(),
                                System.currentTimeMillis(), protocolCommandEvent.getMessage());
                    }
                }
            );

        }

        String user = this.ftpConfig.getUser();
        String password = this.ftpConfig.getPassword();

        if(!this.ftp.login(user, password))
            log.error("==> La connexion avec le serveur ftp {} n'a pas ete etablie.",this.ftpConfig.getServer());
        this.ftp.enterLocalPassiveMode();
    }

    public void close() throws IOException {
        this.ftp.logout();
        ftp.disconnect();
    }

}
