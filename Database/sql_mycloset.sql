-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: myCloset
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `clothes`
--

DROP TABLE IF EXISTS `clothes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clothes` (
  `clothID` int(11) NOT NULL AUTO_INCREMENT,
  `clothImg` varchar(255) DEFAULT NULL,
  `clothCategory` varchar(255) DEFAULT NULL,
  `clothColor` varchar(255) DEFAULT NULL,
  `clothColorLabel` varchar(225) DEFAULT NULL,
  `clothR` int(11) DEFAULT NULL,
  `clothG` int(11) DEFAULT NULL,
  `clothB` int(11) DEFAULT NULL,
  `clothTexture` varchar(255) DEFAULT NULL,
  `clothTags` varchar(255) DEFAULT NULL,
  `addDate` Date DEFAULT NULL,
  `checkDate` Date DEFAULT NULL,
  `thinkDate` Date DEFAULT NULL,
  `checkTimes` int(11) DEFAULT NULL,
  `likeTimes` int(11) DEFAULT NULL,
  PRIMARY KEY (`clothID`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clothes`
--

LOCK TABLES `clothes` WRITE;
/*!40000 ALTER TABLE `clothes` DISABLE KEYS */;
INSERT INTO `clothes` VALUES (253,'file:///storage/emulated/0/test/1509655015181.jpg','Skirts','','',231,237,227,'',', ','2017-11-02',NULL,NULL,0,0),(254,'file:///storage/emulated/0/test/1509655190871.jpg','Tops','','',153,128,97,'',', ','2017-11-02',NULL,NULL,0,0),(255,'file:///storage/emulated/0/test/1509655211636.jpg','Tops','','',110,88,67,'',', ','2017-11-02','2017-11-02',NULL,0,0);
/*!40000 ALTER TABLE `clothes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-04 17:39:23
