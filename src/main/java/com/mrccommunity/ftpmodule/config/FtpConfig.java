package com.mrccommunity.ftpmodule.config;

import lombok.*;
import org.jspecify.annotations.Nullable;

@Builder
@Data
public class FtpConfig{
         private String server;

         private Integer port;

         private String user;

         private String password;

         @Nullable private Boolean isLogActivate;
}
