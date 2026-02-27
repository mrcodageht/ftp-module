package com.mrccommunity.ftpmodule.config;

import lombok.*;
import org.jspecify.annotations.NullMarked;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FtpConfig{
         private String server;

         private Integer port;

         private String user;

         private String password;
}
