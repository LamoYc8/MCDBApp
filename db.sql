-- MySQL dump 10.13  Distrib 5.5.53, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: PRE
-- ------------------------------------------------------
-- Server version	5.5.53-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Admin` (
  `idAdmin` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint(11) NOT NULL,
  PRIMARY KEY (`idAdmin`),
  KEY `utilisateur_id` (`utilisateur_id`),
  CONSTRAINT `FK_Administarteur_utilisateur_id` FOREIGN KEY (`utilisateur_id`) REFERENCES `Utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES (1,1),(2,2),(3,9);
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DonneeAdministrative`
--

DROP TABLE IF EXISTS `DonneeAdministrative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DonneeAdministrative` (
  `numDossier` bigint(50) NOT NULL AUTO_INCREMENT,
  `NomDonne` char(50) DEFAULT NULL,
  `PrenomDonne` char(50) DEFAULT NULL,
  `DateNaissance` date DEFAULT NULL,
  `PersonneAcontacter` char(50) DEFAULT NULL,
  `CodePostal` int(50) DEFAULT NULL,
  `Ville` char(100) DEFAULT NULL,
  `Adresse` varchar(150) DEFAULT NULL,
  `Pays` char(70) DEFAULT NULL,
  `Civilite` enum('Madame','Monsieur') DEFAULT NULL,
  `LieuNaissance` char(200) DEFAULT NULL,
  `NumTel` varchar(14) DEFAULT NULL,
  `Mail` char(70) DEFAULT NULL,
  PRIMARY KEY (`numDossier`)
) ENGINE=InnoDB AUTO_INCREMENT=298036954237982 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DonneeAdministrative`
--

LOCK TABLES `DonneeAdministrative` WRITE;
/*!40000 ALTER TABLE `DonneeAdministrative` DISABLE KEYS */;
INSERT INTO `DonneeAdministrative` VALUES (1,'Cateroz','Lucas','2017-03-13','cousin',56811,'Paris','55 boulevards des marguerittes','France','Monsieur','Caraibes','06 54 78 98 28','lucas.casteroz@gmail.com'),(2,'Nillya','Zoé','2000-02-15','amis',78900,'Marseille','22 rue des limoges','France','Madame','Marseille','06 55 77 88 11','zoe.nilya@gmail.com'),(3,'Georges','Lucas','1999-07-14','père',95200,'Lille','02 tirroir du trou','Etoile Noir','Madame','Nabune','08 99 77 88 11','georges.lucas@gmail.com'),(4,'Dumoule','Sarah','1990-09-21','mari',45600,'Lorient','55 quartier du ciel','France','Madame','Hopital de Rennes','06 55 44 88 33','sarah.dumoule@gmail.com'),(5,'Chevalier','Adam','1880-09-20','copain',36788,'Nice','01 rue du dialbe','France','Madame','Hopital de Nice','06 99 22 77 55','Adam.chevalier@gmail.com'),(6,'Polita','Clara','1009-03-07','proche',89677,'Lyon','boulevard des souvenirs','France','Madame','Bourg en brest','06 33 55 77 88','clara.polita@gmail.com'),(7,'Etranis','Alice','1950-03-16','soeur',25877,'Nantes','62 rue du clocher','France','Madame','Hopital de Nantes','06 77 99 55 44','alice.Etranis@gmail.com'),(8,'Dimulo','Léa','1998-03-15','parent',45699,'Paris','52 promenade des fleurs','France','Madame','Paris','06 58 84 47 75','lea.Dimulo@gmail.com'),(117035698746821,'Cateroz','Lucas','2017-03-13','cousin',56811,'Paris','55 boulevards des marguerittes','France','Monsieur','Caraibes','06 54 78 98 28','lucas.casteroz@gmail.com'),(180099638721549,'Chevalier','Adam','1880-09-20','copain',36788,'Nice','01 rue du dialbe','France','Madame','Hopital de Nice','06 99 22 77 55','Adam.chevalier@gmail.com'),(199076932485674,'Georges','Lucas','1999-07-14','père',95200,'Lille','02 tirroir du trou','Etoile Noir','Madame','Nabune','08 99 77 88 11','georges.lucas@gmail.com'),(200025698246583,'Nillya','Zoé','2000-02-15','amis',78900,'Marseille','22 rue des limoges','France','Madame','Marseille','06 55 77 88 11','zoe.nilya@gmail.com'),(209039875123459,'Polita','Clara','1009-03-07','proche',89677,'Lyon','boulevard des souvenirs','France','Madame','Bourg en brest','06 33 55 77 88','clara.polita@gmail.com'),(250039456287316,'Etranis','Alice','1950-03-16','soeur',25877,'Nantes','62 rue du clocher','France','Madame','Hopital de Nantes','06 77 99 55 44','alice.Etranis@gmail.com'),(290092168135792,'Dumoule','Sarah','1990-09-21','mari',45600,'Lorient','55 quartier du ciel','France','Madame','Hopital de Rennes','06 55 44 88 33','sarah.dumoule@gmail.com'),(298036954237981,'Dimulo','Léa','1998-03-15','parent',45699,'Paris','52 promenade des fleurs','France','Madame','Paris','06 58 84 47 75','lea.Dimulo@gmail.com');
/*!40000 ALTER TABLE `DonneeAdministrative` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Dossier`
--

DROP TABLE IF EXISTS `Dossier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Dossier` (
  `numDossier` bigint(50) NOT NULL AUTO_INCREMENT,
  `NumHopital` bigint(50) NOT NULL,
  PRIMARY KEY (`numDossier`),
  KEY `NumUniteH` (`NumHopital`),
  CONSTRAINT `Dossier_ibfk_1` FOREIGN KEY (`NumHopital`) REFERENCES `Hopital` (`numHopital`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Dossier_ibfk_2` FOREIGN KEY (`numDossier`) REFERENCES `DonneeAdministrative` (`numDossier`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=298036954237982 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dossier`
--

LOCK TABLES `Dossier` WRITE;
/*!40000 ALTER TABLE `Dossier` DISABLE KEYS */;
INSERT INTO `Dossier` VALUES (117035698746821,1),(180099638721549,1),(199076932485674,1),(200025698246583,1),(209039875123459,1),(250039456287316,1),(290092168135792,1),(298036954237981,2);
/*!40000 ALTER TABLE `Dossier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Element`
--

DROP TABLE IF EXISTS `Element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Element` (
  `numElement` bigint(50) NOT NULL AUTO_INCREMENT,
  `numDossier` bigint(50) DEFAULT NULL,
  PRIMARY KEY (`numElement`),
  KEY `FK_Element_numDossier` (`numDossier`),
  CONSTRAINT `FK_Element_numDossier` FOREIGN KEY (`numDossier`) REFERENCES `Dossier` (`numDossier`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Element`
--

LOCK TABLES `Element` WRITE;
/*!40000 ALTER TABLE `Element` DISABLE KEYS */;
INSERT INTO `Element` VALUES (1,117035698746821),(2,117035698746821),(3,117035698746821),(4,117035698746821),(5,117035698746821),(6,117035698746821),(7,117035698746821),(8,117035698746821),(9,117035698746821),(10,117035698746821);
/*!40000 ALTER TABLE `Element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Examen`
--

DROP TABLE IF EXISTS `Examen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Examen` (
  `numElement` bigint(50) NOT NULL,
  `typeExam` char(50) DEFAULT NULL,
  `intitule` char(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `dateExam` date DEFAULT NULL,
  PRIMARY KEY (`numElement`),
  KEY `FK_Examen_numElement` (`numElement`),
  CONSTRAINT `FK_Examen_numElement` FOREIGN KEY (`numElement`) REFERENCES `Element` (`numElement`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Examen`
--

LOCK TABLES `Examen` WRITE;
/*!40000 ALTER TABLE `Examen` DISABLE KEYS */;
INSERT INTO `Examen` VALUES (8,'Recherche biométrique','Salle d\'opération','Recherche biométrique de cancer dans une Salle d\'opération','2016-12-12'),(9,'Vérification Sanguine','Salle d\'examen','Vérification Sanguine a l\'aide d\'une prise de sang','2017-01-23'),(10,'Recherche de cancer','Salle d\'opération','Recherche de cancer dans une Salle d\'opération','2017-03-15');
/*!40000 ALTER TABLE `Examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hopital`
--

DROP TABLE IF EXISTS `Hopital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hopital` (
  `numHopital` bigint(50) NOT NULL AUTO_INCREMENT,
  `nomHopital` varchar(50) NOT NULL,
  `numUniteH` bigint(50) NOT NULL,
  PRIMARY KEY (`numHopital`),
  KEY `numUniteH` (`numUniteH`),
  CONSTRAINT `Hopital_ibfk_1` FOREIGN KEY (`numUniteH`) REFERENCES `UniteHospitaliere` (`numUniteH`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hopital`
--

LOCK TABLES `Hopital` WRITE;
/*!40000 ALTER TABLE `Hopital` DISABLE KEYS */;
INSERT INTO `Hopital` VALUES (1,'Hopital de Paris',1),(2,'Hopital de Marseille',2);
/*!40000 ALTER TABLE `Hopital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medecin`
--

DROP TABLE IF EXISTS `Medecin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Medecin` (
  `idMedecin` bigint(50) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint(50) NOT NULL,
  `numPole` bigint(50) NOT NULL,
  PRIMARY KEY (`idMedecin`),
  KEY `FK_Medecin_utilisateur_id` (`utilisateur_id`),
  KEY `FK_Medecin_numPole` (`numPole`),
  CONSTRAINT `FK_Medecin_numPole` FOREIGN KEY (`numPole`) REFERENCES `Pole` (`numPole`),
  CONSTRAINT `FK_Medecin_utilisateur_id` FOREIGN KEY (`utilisateur_id`) REFERENCES `Utilisateur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medecin`
--

LOCK TABLES `Medecin` WRITE;
/*!40000 ALTER TABLE `Medecin` DISABLE KEYS */;
INSERT INTO `Medecin` VALUES (1,4,1),(4,5,2),(5,6,2),(6,7,1);
/*!40000 ALTER TABLE `Medecin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pole`
--

DROP TABLE IF EXISTS `Pole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pole` (
  `numPole` bigint(50) NOT NULL AUTO_INCREMENT,
  `nomPole` char(50) DEFAULT NULL,
  `numService` bigint(50) NOT NULL,
  PRIMARY KEY (`numPole`),
  KEY `FK_Pole_numService` (`numService`),
  CONSTRAINT `FK_Pole_numService` FOREIGN KEY (`numService`) REFERENCES `Service` (`numService`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pole`
--

LOCK TABLES `Pole` WRITE;
/*!40000 ALTER TABLE `Pole` DISABLE KEYS */;
INSERT INTO `Pole` VALUES (1,'Veterinaire',1),(2,'Consultation',2);
/*!40000 ALTER TABLE `Pole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RapportMedicaux`
--

DROP TABLE IF EXISTS `RapportMedicaux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RapportMedicaux` (
  `idRapport` bigint(50) NOT NULL AUTO_INCREMENT,
  `Intitule` char(50) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Rapport` char(255) DEFAULT NULL,
  `Prescription` varchar(150) NOT NULL,
  `numElement` bigint(50) NOT NULL,
  `isValide` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idRapport`),
  KEY `FK_RapportMedicaux_numElement` (`numElement`),
  CONSTRAINT `FK_RapportMedicaux_numElement` FOREIGN KEY (`numElement`) REFERENCES `Element` (`numElement`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RapportMedicaux`
--

LOCK TABLES `RapportMedicaux` WRITE;
/*!40000 ALTER TABLE `RapportMedicaux` DISABLE KEYS */;
INSERT INTO `RapportMedicaux` VALUES (1,'Malade','1999-01-01','rhume','antidouleur',4,1),(2,'Guéri','2017-03-09','fièvre','antibiotique',5,1),(3,'Malade','2017-03-06','grippe','medoc',6,1),(4,'Malade','2016-10-03','fièvre','amputation du bras',7,1);
/*!40000 ALTER TABLE `RapportMedicaux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Secretaire`
--

DROP TABLE IF EXISTS `Secretaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Secretaire` (
  `idSecretaire` bigint(50) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint(50) NOT NULL,
  `numPole` bigint(50) NOT NULL,
  PRIMARY KEY (`idSecretaire`),
  KEY `FK_Secretaire_utilisateur_id` (`utilisateur_id`),
  KEY `FK_Secretaire_numPole` (`numPole`),
  CONSTRAINT `FK_Secretaire_numPole` FOREIGN KEY (`numPole`) REFERENCES `Pole` (`numPole`),
  CONSTRAINT `FK_Secretaire_utilisateur_id` FOREIGN KEY (`utilisateur_id`) REFERENCES `Utilisateur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Secretaire`
--

LOCK TABLES `Secretaire` WRITE;
/*!40000 ALTER TABLE `Secretaire` DISABLE KEYS */;
INSERT INTO `Secretaire` VALUES (2,3,2),(3,8,1);
/*!40000 ALTER TABLE `Secretaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Service` (
  `numService` bigint(50) NOT NULL AUTO_INCREMENT,
  `nomService` char(50) DEFAULT NULL,
  `numUniteF` bigint(50) NOT NULL,
  PRIMARY KEY (`numService`),
  KEY `FK_Service_numUniteF` (`numUniteF`),
  CONSTRAINT `FK_Service_numUniteF` FOREIGN KEY (`numUniteF`) REFERENCES `UniteFonctionnelle` (`numUniteF`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
INSERT INTO `Service` VALUES (1,'Service UHSIF',1),(2,'Service ALLIF',2);
/*!40000 ALTER TABLE `Service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Soigner`
--

DROP TABLE IF EXISTS `Soigner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Soigner` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `numDossier` bigint(50) NOT NULL,
  PRIMARY KEY (`id`,`numDossier`),
  KEY `FK_appartient_numDossier` (`numDossier`),
  CONSTRAINT `FK_appartient_id` FOREIGN KEY (`id`) REFERENCES `Utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_appartient_numDossier` FOREIGN KEY (`numDossier`) REFERENCES `Dossier` (`numDossier`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Soigner`
--

LOCK TABLES `Soigner` WRITE;
/*!40000 ALTER TABLE `Soigner` DISABLE KEYS */;
INSERT INTO `Soigner` VALUES (7,117035698746821),(8,117035698746821),(7,180099638721549),(8,180099638721549),(7,199076932485674),(8,199076932485674),(7,200025698246583),(8,200025698246583),(7,209039875123459),(8,209039875123459),(7,250039456287316),(8,250039456287316),(7,290092168135792),(8,290092168135792),(7,298036954237981),(8,298036954237981);
/*!40000 ALTER TABLE `Soigner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UniteFonctionnelle`
--

DROP TABLE IF EXISTS `UniteFonctionnelle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UniteFonctionnelle` (
  `numUniteF` bigint(50) NOT NULL AUTO_INCREMENT,
  `nomUniteF` char(50) DEFAULT NULL,
  `numUniteH` bigint(50) NOT NULL,
  PRIMARY KEY (`numUniteF`),
  KEY `FK_UniteFonctionnelle_numUniteH` (`numUniteH`),
  CONSTRAINT `FK_UniteFonctionnelle_numUniteH` FOREIGN KEY (`numUniteH`) REFERENCES `UniteHospitaliere` (`numUniteH`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UniteFonctionnelle`
--

LOCK TABLES `UniteFonctionnelle` WRITE;
/*!40000 ALTER TABLE `UniteFonctionnelle` DISABLE KEYS */;
INSERT INTO `UniteFonctionnelle` VALUES (1,'UHSIF',1),(2,'ALLIF',2);
/*!40000 ALTER TABLE `UniteFonctionnelle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UniteHospitaliere`
--

DROP TABLE IF EXISTS `UniteHospitaliere`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UniteHospitaliere` (
  `numUniteH` bigint(50) NOT NULL AUTO_INCREMENT,
  `nomUniteH` char(50) DEFAULT NULL,
  PRIMARY KEY (`numUniteH`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UniteHospitaliere`
--

LOCK TABLES `UniteHospitaliere` WRITE;
/*!40000 ALTER TABLE `UniteHospitaliere` DISABLE KEYS */;
INSERT INTO `UniteHospitaliere` VALUES (1,'UHSI'),(2,'ALLI');
/*!40000 ALTER TABLE `UniteHospitaliere` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Utilisateur`
--

DROP TABLE IF EXISTS `Utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Utilisateur` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `Nom` char(50) DEFAULT NULL,
  `Prenom` char(50) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `password` char(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utilisateur`
--

LOCK TABLES `Utilisateur` WRITE;
/*!40000 ALTER TABLE `Utilisateur` DISABLE KEYS */;
INSERT INTO `Utilisateur` VALUES (1,'Ganz','Jack','gjack@mail.fr','gjack'),(2,'Lipmin','Noémi','lnoemi@mail.fr','lnoemi'),(3,'Mouse','Mickey','mmickey@mail.fr','mmickey'),(4,'Duck','Donald','ddonald@mail.fr','ddonald'),(5,'DR','Vegapunk','dvegapunk@mail.fr','dvegapunk'),(6,'Horus','Lucifer','hlucifer@mail.fr','hlucifer'),(7,'Mario','Kart','mario@mail.com','mario'),(8,'Celine','Hope','celine@mail.com','celine'),(9,'David','Loup','david@mail.com','david');
/*!40000 ALTER TABLE `Utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-12 20:14:46
