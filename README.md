# Start Kit Android

Ceci est l'application blanche (ou marque blanche) d'Android.

Elle est embarque déjà une multitude d'outils tels que Fabric (Crashlytics), Google Analytics etc...

## Environnement

- Android Studio 3.1.X
- Gradle 3.1.4
- MinSDK 15
- TargetSDK 28
- JAVA 8

## Comment l'utiliser étape par étape

1. Tout d'abord, commencer par cloner et/ou forker le repository.
2. S'assurer que l'environnement de travail corresponde à ce qui est spécifier plus haut.
3. Changer le nom du projet dans le fichier `res/values/strings.xml` avec la propriété `app_name`
4. Changer le nom du package dans les fichiers suivants :
    - Dans le fichier `AndroidManifest.xml`, changer l'attribut `package`
    - Dans le fichier `app/build.gradle`, changer l'attribut `applicationId` dans `android` puis `defaultConfig`
    - Dans tous les fichiers java et .xml 
    - Changer le nom des dossiers des packages
5. Changer l'icône de l'application par la vôtre. Celle-ci est à mettre dans le dossier mipmap prévue à cet effet
6. OPTIONNEL : Changer les clés Fabric et Google Analytics dans le fichier `gradle.properties`
7. OPTIONNEL : Remplacer le fichier `google-services.json` par le votre
    
    `Attention : Vous pouvez garder la même clé que celle du projet, mais celle-ci correspond au compte du SC Mobilité, assurez-vous d'y avoir accès avant de l'utiliser. Auquel cas, merci de le changer en créant votre propre compte Fabric, Google Analytics et Firebase`

## Documentation
[Comment générer une clé pour Fabric](https://docs.fabric.io/android/fabric/settings/api-keys.html)

[Comment générer une clép pour Google Analytics](https://support.google.com/analytics/answer/2587086?hl=fr)

[Comment ajouter Firebase à son projet](https://firebase.google.com/docs/android/setup)

`ATTENTION : Le seul besoin ici est de générer votre propre fichier google-services.json et le remplacer par l'existant, les dépendances existent déjà`

## Contact en cas de besoin

Merci d'envoyer un mail à [mobile.maroc@sqli.com](mailto:mobile.maroc@sqli.com) ou à [obennouna@sqli.com](mailto:obennouna@sqli.com) en cas de questions, problèmes, suggestions etc...