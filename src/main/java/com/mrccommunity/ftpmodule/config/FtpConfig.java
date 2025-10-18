package com.mrccommunity.ftpmodule.config;

public record FtpConfig(
         String server,

         Integer port,

         String user,

         String password
) { }
