-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db_tp_order_management
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (2,'PaperPlus Corporation','paperplus@example.com','456 Elm Street','9876543210'),(5,'PaperGenius Co.','papergenius@example.com','654 Maple Street','8765432109'),(6,'PaperSolutions LLC','papersolutions@example.com','987 Cedar Street','1098765432'),(7,'PaperTech Industries','papertech@example.com','210 Birch Street','9871234560'),(9,'PaperWorld Inc.','paperworld@example.com','876 Spruce Street','3210987654'),(10,'PaperPros Corporation','paperpros@example.com','135 Pineapple Street','1234567890'),(12,'PaperPeople Co.','paperpeople@example.com','246 Cherry Street','9876543210'),(13,'PaperDreams Ltd.','paperdreams@example.com','579 Mango Street','1357924680'),(19,'DunderMiflin Inc','dunder@email.com','Scranton 12','0984976123');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `totalPrice` double DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `placedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (3,1299,'PaperDreams Ltd.','Staplers','2024-05-12 21:18:37'),(4,10990,'DunderMiflin Inc','Paper Rims','2024-05-12 22:37:24'),(5,1967.21,'DunderMiflin Inc','Paper Rims','2024-05-12 22:56:33'),(6,604.45,'DunderMiflin Inc','Paper Rims','2024-05-13 10:49:43'),(7,1048.5,'PaperPlus Corporation','Markers','2024-05-13 10:50:39'),(8,1048.5,'PaperTech Industries','Markers','2024-05-13 11:37:20'),(9,8396,'PaperPros Corporation','Binders','2024-05-13 11:38:59'),(10,1948.5,'PaperTech Industries','Staplers','2024-05-13 11:59:16'),(11,19995,'PaperWorld Inc.','Binders 2xPack','2024-05-13 12:00:05');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `client` bigint DEFAULT NULL,
  `product` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client` (`client`),
  KEY `product` (`product`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`client`) REFERENCES `client` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (11,100,1299,7,16),(13,100,1299,13,16),(14,1000,10990,19,9),(17,150,1048.5,2,12),(18,150,1048.5,7,12),(19,400,8396,10,17),(20,150,1948.5,7,16),(21,500,19995,9,19);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (9,1945,'Paper Rims',10.99),(12,1223,'Markers',6.99),(15,1292,'Scissors',5.99),(16,1150,'Staplers',12.99),(17,1073,'Binders',20.99),(19,736,'Binders 2xPack',39.99),(27,1247,'Chair',30.99);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'benipintea','$2a$10$Y322ScRirCZ1gE6IJx.17.TAPMDTmUYoseYMDya/JDl6kmuXIo2d2'),(2,'john13','$2a$10$subLNgERy2xNJtQEAiRICerrPiFiCzjin6JZ.bP52uy.ynUA7SBS2'),(3,'david','$2a$10$D5UODDbQacHiKRLdSYnOMOkKPjf8P66qdjKgbOflO1FiDi.wbpdKi');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-14 13:13:21
