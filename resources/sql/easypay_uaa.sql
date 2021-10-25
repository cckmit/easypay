-- MySQL dump 10.13  Distrib 5.7.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: easypay_uaa
-- ------------------------------------------------------
-- Server version	5.7.34

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
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` longtext,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `archived` tinyint(4) DEFAULT NULL,
  `trusted` tinyint(4) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接入客户端信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('507f38136d4443f3a07f07d3ce5f9a05','huimin-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2020-11-13 00:18:32',NULL,0,NULL),('5fe423628cd14143a94d05575323555d','huimin-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2020-11-13 03:00:29',NULL,0,NULL),('8016770e53ab495cb88bacb626930ceb','huimin-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2020-11-13 00:16:27',NULL,0,NULL),('e366cfc9373e440abbdbe5818aa6c91b','huimin-resource','','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2020-11-13 02:02:01',NULL,0,NULL),('huimin-resource','huimin-resource','suiyixie','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_API',7200,259200,NULL,'2020-10-23 18:29:38',NULL,0,NULL),('merchant-platform','huimin-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_MERCHANT,ROLE_USER',31536000,259200,NULL,'2020-11-21 20:49:31',0,0,'false'),('operation-platform','huimin-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_OPERATION',7200,259200,NULL,'2020-09-26 02:10:07',0,0,'false'),('portal-site','huimin-resource','123456','read','client_credentials,password,authorization_code,implicit,refresh_token',NULL,'ROLE_PORTAL',7200,259200,NULL,'2020-09-26 02:10:18',0,0,'false');
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `code` varchar(255) NOT NULL COMMENT '授权码',
  `authentication` longblob COMMENT '认证信息',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='授权码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--

LOCK TABLES `oauth_code` WRITE;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-25 14:50:51
