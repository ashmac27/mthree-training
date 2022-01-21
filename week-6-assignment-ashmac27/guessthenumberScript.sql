CREATE DATABASE  IF NOT EXISTS `guessthenumber` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `guessthenumber`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: guessthenumber
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `gameId` int NOT NULL AUTO_INCREMENT,
  `answer` varchar(10) NOT NULL,
  `isFinished` varchar(10) NOT NULL,
  PRIMARY KEY (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'3290','0'),(2,'9807','1'),(3,'1290','1'),(4,'4352','1'),(5,'9876','1'),(6,'1254','0'),(10,'4621','0'),(11,'5268','0'),(12,'8359','0'),(13,'5846','0'),(14,'6548','1'),(15,'aaaa','1'),(16,'4793','1');
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `round`
--

DROP TABLE IF EXISTS `round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `round` (
  `roundId` int NOT NULL AUTO_INCREMENT,
  `gameId` int NOT NULL,
  `guessTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `guess` varchar(10) NOT NULL,
  `result` varchar(10) NOT NULL,
  PRIMARY KEY (`roundId`),
  KEY `fk_gameId` (`gameId`),
  CONSTRAINT `fk_gameId` FOREIGN KEY (`gameId`) REFERENCES `game` (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `round`
--

LOCK TABLES `round` WRITE;
/*!40000 ALTER TABLE `round` DISABLE KEYS */;
INSERT INTO `round` VALUES (1,1,'2021-08-10 10:20:11','1254','e:4:p:0'),(2,2,'2021-04-11 05:47:31','5181','e:0:p:0'),(3,3,'2021-05-15 01:36:59','9876','e:4:p:0'),(4,4,'2021-01-31 07:03:01','1254','e:4:p:0'),(5,5,'2021-10-01 14:55:02','1259','e:1:p:1'),(6,6,'2021-08-04 07:20:49','4352','e:4:p:0'),(10,10,'2021-12-04 08:20:49','4621','e:4:p:0'),(11,11,'2021-12-05 08:20:49','1324','e:0:p:0'),(12,12,'2021-12-07 11:37:42','2343','e:1:p:0'),(13,13,'2021-12-07 11:40:42','1234','e:0:p:0'),(14,14,'2021-12-07 11:44:03','7452','e:0:p:2'),(15,14,'2021-12-07 11:44:21','7451','e:0:p:2'),(16,14,'2021-12-07 11:44:44','7453','e:0:p:1'),(17,14,'2021-12-07 11:44:50','7451','e:0:p:2'),(18,14,'2021-12-07 11:44:57','7421','e:1:p:2'),(19,14,'2021-12-07 11:45:32','1721','e:3:p:0'),(20,14,'2021-12-07 11:45:38','1729','e:4:p:0'),(21,15,'2021-12-07 11:52:12','2346','e:1:p:2'),(22,15,'2021-12-07 11:53:07','5346','e:2:p:2'),(23,15,'2021-12-07 11:53:24','5346','e:2:p:2'),(24,15,'2021-12-07 11:53:42','5364','e:4:p:0'),(25,16,'2021-12-07 12:00:02','1234','e:0:p:2'),(26,16,'2021-12-07 12:00:28','4791','e:3:p:0'),(27,16,'2021-12-07 12:00:37','4793','e:4:p:0');
/*!40000 ALTER TABLE `round` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-07 14:29:30
