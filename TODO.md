## TODO
La liste des fonctionnalites et des bugs a geres

### Bugs *(a gerer)*
- [ ] Eviter de relancer l'exception `IOException` lancer par le dependances apache lors de la sauvegarde d'un fichier sur le ftp si le nom du fichier est manquant : **indice :** c'est d'utiliser le nom du fichier fournir si le nom n'est pas inclus dans le chemin de destination
- [ ] Lors de l'appele d'une methode dans la classe utilitaire a la fontion `upFTP()` ouvre une connexion mais la fonction `downFTP()` ne ferme pas cette connexion et si une autre connexion se cree les logs se dupliquent, autant de duplication refletent le nombre de connexions qui ont ou qui avaient sur le serveur ftp

### Tests
Ajout des tests unitaires pour les methodes : 
- [ ] `downloadFile`
- [ ] `uploadFile`
- [ ] `doesFtpDirectoryExist` 
- [ ] `createFolder`
- [ ] `listFiles`
- [ ] `removeFile`

### Docs
- [ ] Enrichir le wiki sur le pourquoi des differentes methodes utilitaires
- [ ] Donner des sections codes prets a l'emploi
- [ ] L'evolution du module

### Features
