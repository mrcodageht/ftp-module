# FTP Module – Java Utility Library

Ce projet est un module Java réutilisable permettant de gérer facilement les connexions et opérations FTP sans avoir à réimplémenter toute la logique de communication.

Il encapsule les fonctionnalités offertes par la bibliothèque **Apache Commons Net** et fournit une API simple, intuitive et sécurisée pour effectuer des transferts, des listings de fichiers, des téléchargements et des uploads.

---

## Objectif du projet

L’objectif du **FTP Module** est de :

- Centraliser la gestion des connexions FTP.
- Simplifier l’interaction avec les serveurs FTP dans vos applications Java.
- Éviter la duplication de code entre plusieurs projets nécessitant des échanges FTP.
- Offrir une interface propre et modulaire, facilement intégrable dans n’importe quelle application (Spring, Spring Boot, console, desktop, etc.).

---

## Fonctionnalités principales

- Connexion automatique à un serveur FTP à partir de paramètres configurables.
- Méthodes prêtes à l’emploi pour :
    - `listFiles()` : Lister les fichiers distants.
    - `uploadFile()` : Envoyer un fichier sur le serveur.
    - `downloadFile()` : Télécharger un fichier distant.
- Gestion automatique de la connexion (ouverture, validation, fermeture).
- Support des environnements configurables (`.env`, variables Docker, Spring Config, etc.).
- Logging et gestion des erreurs intégrés.

---

## Installation

### Via Maven

```xml
<!-- Ajouter le depot github pour le package dans votre pom.xml -->

<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/mrcodageht/ftp-module</url>
    </repository>
</repositories>

<!-- Ajouter la dependance comme ceci -->

<dependency>
    <groupId>com.mrccommunity.ftpmodule</groupId>
    <artifactId>ftp-module</artifactId>
    <version>${version}</version>
</dependency>
```

## Configuration

Exemple d’utilisation avec variables d’environnement ou fichier de configuration :

```bash
FTP_HOST=ftp.example.com
FTP_PORT=port
FTP_USERNAME=youruser
FTP_PASSWORD=yourpassword
```

## Utilisation & Exemples de code

### Exemple simple (Avec le context spring dans votre pom.xml)

```java
package com.mrccommunity.ftpmodule;

import com.mrccommunity.ftpmodule.config.AppConfig;
import com.mrccommunity.ftpmodule.config.FtpConfig;
import com.mrccommunity.ftpmodule.ftp.IFtpClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        
        var ftpConfig = FtpConfig.builder()
                .server("localhost")
                .port(21)
                .user("myuser")
                .password("myverystrongpassword")
                .build();

				// Creation du factory pour recuperer une instance de client ftp pour acceder au differentes methodes
        FtpFactory factory = new FtpFactory(ftpConfig);
        IFtpClient ftpClient = factory.getClient();
        
        // Testons notre server ftp avec le module
				
        // Test 1 : Recuperation des fichiers du server ftp
        var list = ftpClient.listFiles("/");

        list.forEach(file -> {
            System.out.printf("filename : %s \n",file);
        });

        // Test 2 : Sauvegarder un fichier local sur le server ftp
        // Dans ce test nous utilisons class [Resource] fournie par le context spring pour manipuler les fichiers plus proprement.
        Resource resource = new ClassPathResource("lego_scrum_week_1.md");

        if(resource.exists() && resource.isFile()){
            File file = resource.getFile();
            ftpClient.uploadingFile(file, file.getName());
        }else{
            System.out.println("Erreur lors de la recuperation du fichier dans le classpath");
        }
        
        // Fermeture du context spring apres les tests
        context.close();
    }
}

```

### Exemple simple (Avec spring boot)

- Creation d’un component pour charger tes variables d’environnement

```java
package com.mrccommunity.api_docs.component;

import com.mrccommunity.ftpmodule.config.FtpConfig;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class FtpComponent{

    @Value("${ftp.server}")
    private  String server;

    @Value("${ftp.port}")
    private  Integer port;

    @Value("${ftp.user}")
    private  String user;

    @Value("${ftp.password}")
    private  String password;

    public FtpConfig getConfig() {
        return FtpConfig.builder()
                .server("localhost")
                .port(21)
                .user("myuser")
                .password("myverystrongpassword")
                .build();
    }
}
```

- Dans une classe de service ou d’autres

```java
import com.mrccommunity.ftpmodule.FtpFactory;

@Service
public class FtpService {

    @Autowired FtpComponent ftpComponent;
    private FtpFactory factory;
    private IFtpClient ftpClient;
    
    public FtpService(){
	    this.factory = new FtpFactory(ftpComponent.getConfig());
	    this.ftpClient = this.factory.getClient();
    }

    public void list() {
			var list = ftpClient.listFiles("/");

      list.forEach(file -> {
          System.out.printf("filename : %s \n",file);
      });
    }
    
    // vous pouvez ajouter d'autres methodes pour manipuler le ftp
    // ...
 }
```

# Credits

_**Autheur :**_ Wesner Philogene
