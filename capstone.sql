-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: capstone
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_control`
--

DROP TABLE IF EXISTS `access_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_control` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `file_id` int DEFAULT NULL,
  `permission` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_control`
--

LOCK TABLES `access_control` WRITE;
/*!40000 ALTER TABLE `access_control` DISABLE KEYS */;
INSERT INTO `access_control` VALUES (1,'user1',1,'EDIT'),(2,'user1',2,'EDIT'),(3,'user1',3,'EDIT'),(4,'user1',4,'EDIT'),(5,'k',1,'VIEW'),(6,'k',2,'DOWNLOAD'),(7,'k',3,'EDIT'),(8,'k',4,'VIEW'),(9,'admin',5,'EDIT');
/*!40000 ALTER TABLE `access_control` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `id` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(100) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `hash` varchar(256) DEFAULT NULL,
  `owner` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (1,'IMG-20230804-WA0002.jpeg','files/enc_IMG-20230804-WA0002.jpeg','b25405e87cfb9b7eec65767d8ec4b25749d0bac4d8f00408c6b1985faff2c71a','user1'),(2,'empl.docx','files/enc_empl.docx','930205bf9499aad8075f716157186374e46f56f57d3599e45b9079f1d6bd81bf','user1'),(3,'lab.docx','files/enc_lab.docx','999620cd9a915b51b4c1fc6a4e07bc76fe8ef029895667a37b279a83a60f92d7','user1'),(4,'connection game.pptx','files/enc_connection game.pptx','069ef17abb154ef67da15ae04daf16c8dac062ce0632ccfe0726bf68245fc58c','user1'),(5,'Doc1.docx','files/enc_Doc1.docx','bc2303e084ae80c95fed33c8fcb4aa8d8574688d9e8d0409cd1843600ade37fe','admin');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(50) DEFAULT NULL,
  `action` varchar(200) DEFAULT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,'user1','Uploaded (encrypted) file: IMG-20230804-WA0002.jpeg','2026-04-14 16:26:14'),(2,'user1','Uploaded (encrypted) file: empl.docx','2026-04-14 16:26:24'),(3,'user1','Uploaded (encrypted) file: lab.docx','2026-04-14 16:26:31'),(4,'user1','Uploaded (encrypted) file: connection game.pptx','2026-04-14 16:26:38'),(5,'k','Opened file: IMG-20230804-WA0002.jpeg','2026-04-14 16:28:19'),(6,'k','Opened file: empl.docx','2026-04-14 16:28:29'),(7,'k','Opened file: empl.docx','2026-04-14 16:28:44'),(8,'k','Opened file: lab.docx','2026-04-14 16:29:02'),(9,'k','Opened file: connection game.pptx','2026-04-14 16:29:07'),(10,'k','Opened file: connection game.pptx','2026-04-14 16:29:48'),(11,'admin','Uploaded (encrypted) file: Doc1.docx','2026-05-11 13:54:25'),(12,'admin','Opened file: IMG-20230804-WA0002.jpeg','2026-05-11 13:55:00'),(13,'admin','Opened file: connection game.pptx','2026-05-11 13:55:12'),(14,'admin','Opened file: lab.docx','2026-05-11 13:55:19'),(15,'admin','Opened file: empl.docx','2026-05-11 13:55:24'),(16,'admin','Opened file: connection game.pptx','2026-05-25 19:33:18');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `file_id` int DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1,'k',1,'APPROVED'),(2,'k',2,'APPROVED'),(3,'k',3,'APPROVED'),(4,'k',4,'APPROVED'),(5,'1',4,'PENDING'),(6,'1',1,'PENDING'),(7,'5',4,'PENDING');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user1','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','USER'),(2,'admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','ADMIN'),(3,'k','6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b','USER'),(4,'1','6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b','USER'),(5,'5','ef2d127de37b942baad06145e54b0c619a1f22327b2ebbcfbec78f5564afe39d','USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-21 11:28:10
