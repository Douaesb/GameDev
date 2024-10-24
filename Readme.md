# Gestion des Tournois de Jeux Vidéo

## Description du projet
Ce projet est une application de gestion dédiée aux tournois de jeux vidéo. Elle permet de gérer les équipes, les joueurs, les équipes et les tournois. L'application facilite l'organisation des tournois en fournissant des outils pour la création, la modification et le suivi des événements.

## Objectif général de l'application
L'objectif principal de cette application est de fournir une solution efficace pour l'organisation et la gestion des tournois de jeux vidéo. Elle permet de gérer les équipes et les joueurs, de planifier des tournois et de suivre les résultats, tout en offrant une expérience utilisateur fluide pour consulter les informations sur les tournois.

## Technologies utilisées
- **Langage** : Java 8
- **Base de données** : H2 (pour le développement) 
- **ORM** : Hibernate JPA
- **Système de gestion des dépendances** : Maven
- **Testing** : JUnit, Mockito
- **Framework** : Spring Core (sans annotations)
- **Code Coverage** : JaCoCo


## Structure du projet
Le projet est organisé selon une architecture MVC stricte et en couches, avec les principales sections suivantes :

- **Entity** : Classes Java représentant les entités (Player, Team, Tournament, Game) avec leurs attributs.
- **Service** : Contient la logique métier, y compris les services pour la gestion des jeux,  joueurs, équipes et tournois, et coordonne les opérations entre le contrôleur et la couche DAO.
- **Dao** : Gère l'accès aux données, en effectuant des opérations CRUD sur la base de données via Hibernate.

## Description brève de l'architecture adoptée
L'application suit le modèle d'architecture MVC (Model-View-Controller) avec une séparation claire entre les différentes couches de l'application :
- **Entity** : Géré par Hibernate JPA, représentant les entités comme `Player`, `Team`, `Tournament`, `Game`.

L'accès à la base de données est effectué via des DAO (Data Access Object), et l'injection de dépendance permet une gestion claire des services et des DAO via fichier de configuration applicationContext.xml

## Instructions d'installation et d'utilisation
### Prérequis
- Java 8
- Maven

### Étapes d'installation
1. Clonez le dépôt du projet :
   ```bash
   git clone https://github.com/Douaesb/GameDev.git


2. Accédez au répertoire du projet :
    ```bash
    cd gameDev

3. Configurez les dépendances Maven :
   ```bash
    mvn clean install

4. Configurez votre base de données (voir ci-dessous).

**Étapes pour configurer la base de données**
- Créez une base de données  :

CREATE DATABASE marketplace_db;

- Modifiez le fichier persistence.xml pour correspondre à vos paramètres PostgreSQL (nom d'utilisateur, mot de passe, URL de la base de données) :
  xml
    ```bash
    <property name="hibernate.connection.url" value="jdbc:postgresql://localhost:5432/marketplace_db"/>
    <property name="hibernate.connection.username" value="votre_nom_utilisateur"/>
    <property name="hibernate.connection.password" value="votre_mot_de_passe"/>


## Comment lancer l'application
Pour lancer l'application, utilisez le menu console qui permet d'interagir avec les différentes fonctionnalités de gestion des tournois. Exécutez la classe principale et suivez les instructions affichées.
### Génération du fichier JAR
Pour générer un fichier JAR exécutable, suivez ces étapes :
Construisez le projet avec Maven :

      mvn clean package

### Exécution du fichier JAR
Pour exécuter le fichier JAR, utilisez la commande suivante dans le terminal :

    java -jar target/gameDev-1.0-SNAPSHOT.jar

### Exécution des tests de l'application
Pour executer les tests :
- L'application contient des tests unitaires en utilisant Junit et Mockito, ainsi que des tests d'intégration en utilisant une base de donnée H2 en mémoire pour tester les interactions de la couche service et la couche dao avec une vrai base de donnée. Pour exécuter ces tests veuillez utiliser la commande suivante dans le terminal :

      mvn test

## Améliorations futures possibles
Amélioration de l'interface utilisateur : Ajouter plus de fonctionnalités AJAX pour éviter les rechargements de page complets lors des actions CRUD.
Amélioration de la sécurité : Mettre en œuvre une authentification plus robuste avec JWT ou OAuth 2.0.
Fonctionnalités supplémentaires : Ajouter des statistiques avancées pour les tournois et les équipes.
Internationalisation : Support de plusieurs langues pour atteindre un public plus large.
## Idées pour étendre ou améliorer le projet
Ajouter une fonctionnalité de filtrage avancé des tournois (par date, type de jeu).
Implémenter un système de gestion des résultats des matchs et des classements des équipes.
Ajouter une fonctionnalité de génération de rapports pour les administrateurs afin de suivre les statistiques des tournois et des joueurs.
## Auteur et contact

Nom: [Sebti Douae]
GitHub: https://github.com/Douaesb


Merci d'avoir consulté ce projet. Pour toute question ou suggestion, n'hésitez pas à me contacter!
