-- MySQL dump 10.13  Distrib 5.7.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: easypay_transaction
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
-- Table structure for table `app_platform_channel`
--

DROP TABLE IF EXISTS `app_platform_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_platform_channel` (
  `ID` bigint(20) NOT NULL,
  `APP_ID` varchar(50) DEFAULT NULL COMMENT '应用id',
  `PLATFORM_CHANNEL` varchar(50) DEFAULT NULL COMMENT '平台支付渠道',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='说明了应用选择了平台中的哪些支付渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_platform_channel`
--

LOCK TABLES `app_platform_channel` WRITE;
/*!40000 ALTER TABLE `app_platform_channel` DISABLE KEYS */;
INSERT INTO `app_platform_channel` VALUES (1448501135431581697,'1448499931171434497','huimin_b2c'),(1450007448886661121,'87a2702d0df64669aa73924dd13bd969','huimin_b2c'),(1450008695572221953,'87a2702d0df64669aa73924dd13bd969','wx_native'),(1450031985309208578,'87a2702d0df64669aa73924dd13bd969','alipay_wap'),(1450331650885902337,'87a2702d0df64669aa73924dd13bd969','huimin_c2b');
/*!40000 ALTER TABLE `app_platform_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_channel`
--

DROP TABLE IF EXISTS `pay_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_channel` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '原始支付渠道名称',
  `CHANNEL_CODE` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_channel`
--

LOCK TABLES `pay_channel` WRITE;
/*!40000 ALTER TABLE `pay_channel` DISABLE KEYS */;
INSERT INTO `pay_channel` VALUES (1,'微信JSAPI','WX_JSAPI'),(2,'支付宝手机网站支付','ALIPAY_WAP'),(3,'支付宝条码支付','ALIPAY_BAR_CODE'),(4,'微信付款码支付','WX_MICROPAY'),(5,'微信native支付','WX_NATIVE');
/*!40000 ALTER TABLE `pay_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_channel_param`
--

DROP TABLE IF EXISTS `pay_channel_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_channel_param` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '配置名称',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '商户ID',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PARAM` text COMMENT '支付参数',
  `APP_PLATFORM_CHANNEL_ID` bigint(20) DEFAULT NULL COMMENT '应用与支付渠道绑定ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='某商户针对某一种原始支付渠道的配置参数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_channel_param`
--

LOCK TABLES `pay_channel_param` WRITE;
/*!40000 ALTER TABLE `pay_channel_param` DISABLE KEYS */;
INSERT INTO `pay_channel_param` VALUES (1450016007590465537,'peizhi',1449955397829902338,'ALIPAY_BAR_CODE','{\"appId\":\";ljkasdf,ml;k\",\"rsaPrivateKey\":\";ljasdf\",\"notifyUrl\":\";lk;lk\",\"returnUrl\":\"1;lk;lksjdl;fkj\",\"url\":\"https://openapi.alipaydev.com/gateway.do\",\"chareset\":\"UTF-8\",\"format\":\"json\",\"alipayPublicKey\":\"gongyao\",\"signtype\":\"RSA2\"}',1450007448886661121),(1450379329158430722,'支付宝wap扫码支付',1449955397829902338,'ALIPAY_WAP','{\"appId\":\"2021000118635333\",\"rsaPrivateKey\":\"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCW49k8p3WSAGEjAzwmedrrr8c1P6KM69JomxFHzy2lzzji4gp5GuIEV8vYYDdwG24ofd1UdjmXtfNkQ70w8wywLvIC/DZ7VlBU6MO83jAncfbGBEePBuYe1/XWWLdpiVp9DbsAz5jdOlbiPC1drjxN/qCzgfuFlUzEZvYwwrpy1Nq6VMxVphuxzXSpDzCiNIJ+uNJasTxI3T3D9YaQ/eJOmTvmVEgizi6Tj/CK00W2FPUJkC4JizxdASSEM7Ql6HhYSHEnNfxX9dLiHtuILQEtjSSdmIFV7HSa/2Apw3k3NAoJHaHhZSD2zWhKu/gEpmk4Ml6MAIQ2/wmS9z9szZnlAgMBAAECggEARlBvT0CKHTtoCLTdNuDaD7KeFkZNLXZHWs/IQbpd/0KNs8y6LK1GXde7+lz7+JYPw3bDyTQ+ql/zx+l5JHJem0uFI7b8fIVAchD2BfFUZ01IPgaIgjIo0n645OX6sod7T4MyfbI//ffNvSdlzJ4BJCdgI8aSuzFT0MJaP2a/E/s8n9J6/pPgFcax72WkxT3mIiUJTIasFDcAo3+tKycEivENlOJplbeT1Kq+9PqDWpICAQuVuaSp5Rqqj8OMhd3P7fOHprPp4/wXOVz61Dx/QDfp/nis6nY3cckN4kAIs3c6ATTSjc6A+UwPbV0YYrwjaBHA9MK4iSrWZNvzzOS/gQKBgQDbHA9645AW2ple0w4rKHsoTsKNVJ0np5k+tHeOMfJBO248ZshXFb8W7L7uAfZdktOWKh//UZn5n2MY8iJGojPhmKXZqwy5WMrGBbef+VufhPR7kfm0OfpDPdswPre3OkOmDYKB/hLoNY3CXqOrjvFxuA29uX0/PkGDuUFjgmnkjQKBgQCwS20YKvqRuBCDOeSNnaVF71tmcJpULaEIy/Xd/j5Ll0um1EbIT6xEYNoIVmv5w+sxt1LA7oJH7ElASuZwsBzij7m/VU3FS11ilZ7BoiL/T0eq1gRvZo6ge0fMcOoRsUvhjF0p25m3qrU7JsSGzZWIC7fIKkvmnqaowesIImEwuQKBgGWzIZBFnbSt5DA6QEJR+NAdtnnJA2TLivqq4y8yCaKzhhgtEOIy6s4mNvzcFlEafHEfvlhQtOpDS8jhYn1sfi5BHwHoPfCtwPim4LM1F4z0NlJA0CkSHBIn3NwI0FcQiSPC84eIuZfke5bzPH69y12gM2iTdwnqyJfuXeZlOudlAoGBAKqqGg6PbMqsea/P16YQuvKc6Mqer4hWM9KkQHyLqBrdeGskjPFVz9rSN8BiiVzdgU47vmzveEfQQolu+O6WTyA1KwhCuGsKrnjFh07Ee0TVpveG//woOGK7daJAnRhLnr3WeEoRQRpAs/lzakVluCBf16Z668dyRryQHcteHOBhAoGAXX+7YaCOplGTslgKVo8Mbg8rGeqMNNCNTqwsY9gbI8dybepnh4PjrVc7hHgByiRqK+hHZDPK7T0euVv03IRr2Whc+CYIRUUdvnx1NFcXnDquNLJjpyy6iAclQR42lazbOwmW3R6oQD2OCdm4L/qcndB4aOLN73cSl8IGzTBdnUk=\",\"notifyUrl\":\"--\",\"returnUrl\":\"https://www.baidu.com\",\"url\":\"https://openapi.alipaydev.com/gateway.do\",\"chareset\":\"UTF-8\",\"format\":\"json\",\"alipayPublicKey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmnLC7aHotACmFcnJuaRxrPp0n/Ules5p67eSjgKtPlk9z5eMA+Ej9fF9bMkes1EBskDN1cEzifc4tPv7hQi8dhfggN3MVUvcL921dW22ODwdZ1pyExaXK1thX75IbdMTOMhowb3UOiE+K7Vbz/3DFO8gClIdX6OETPPadSFeqiqjo6jUBdBeoLogqBgxWfxeb/j7+qOKyLlqCqIGS8CR5Iq4vPR+39eortJBcJc2soKxCXtBzA4AWl5mXTNevkfuuqG0fUH4MfgRu0xpWCfUMRk/O0Y78/m998wLw3vkvXnUnKxpHA5d/qjoT6/dSXx/Ac064ugnEU9pLe5rRsAlxQIDAQAB\",\"signtype\":\"RSA2\"}',1450331650885902337);
/*!40000 ALTER TABLE `pay_channel_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_order`
--

DROP TABLE IF EXISTS `pay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_order` (
  `ID` bigint(20) NOT NULL,
  `TRADE_NO` varchar(50) NOT NULL COMMENT '聚合支付订单号',
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT '所属商户',
  `STORE_ID` bigint(20) DEFAULT NULL COMMENT '商户下门店',
  `APP_ID` varchar(50) NOT NULL COMMENT '所属应用',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PAY_CHANNEL_MCH_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户id',
  `PAY_CHANNEL_MCH_APP_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户应用id',
  `PAY_CHANNEL_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道订单号',
  `CHANNEL` varchar(50) DEFAULT NULL COMMENT '聚合支付的渠道',
  `OUT_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `SUBJECT` varchar(50) DEFAULT NULL COMMENT '商品标题',
  `BODY` varchar(256) DEFAULT NULL COMMENT '订单描述',
  `CURRENCY` varchar(50) DEFAULT NULL COMMENT '币种CNY',
  `TOTAL_AMOUNT` int(11) DEFAULT NULL COMMENT '订单总金额，单位为分',
  `OPTIONAL` varchar(256) DEFAULT NULL COMMENT '用户自定义的参数,商户自定义数据',
  `ANALYSIS` varchar(256) DEFAULT NULL COMMENT '用于统计分析的数据,用户自定义',
  `EXTRA` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `TRADE_STATE` varchar(50) DEFAULT NULL COMMENT '交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成,4-关闭',
  `ERROR_CODE` varchar(50) DEFAULT NULL COMMENT '渠道支付错误码',
  `ERROR_MSG` varchar(256) DEFAULT NULL COMMENT '渠道支付错误信息',
  `DEVICE` varchar(50) DEFAULT NULL COMMENT '设备',
  `CLIENT_IP` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '订单过期时间',
  `PAY_SUCCESS_TIME` datetime DEFAULT NULL COMMENT '支付成功时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `unique_TRADE_NO` (`TRADE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_order`
--

LOCK TABLES `pay_order` WRITE;
/*!40000 ALTER TABLE `pay_order` DISABLE KEYS */;
INSERT INTO `pay_order` VALUES (1451843670235926530,'HM1451843670241669120',1449955397829902338,1449955397829902339,'87a2702d0df64669aa73924dd13bd969','ALIPAY_WAP',NULL,NULL,'2021102322001439050501239603','huimin_c2b',NULL,'企业名称商品','向企业名称付款','CNY',99900,NULL,NULL,NULL,'2',NULL,NULL,NULL,'172.16.42.122','2021-10-23 17:31:20',NULL,'2021-10-23 18:01:20','2021-10-25 13:30:33'),(1451845184530665474,'HM1451845184528019456',1449955397829902338,1449955397829902339,'87a2702d0df64669aa73924dd13bd969','ALIPAY_WAP',NULL,NULL,'2021102322001439050501237660','huimin_c2b',NULL,'企业名称商品','向企业名称付款','CNY',998800,NULL,NULL,NULL,'2',NULL,NULL,NULL,'172.16.42.122','2021-10-23 17:37:21',NULL,'2021-10-23 18:07:21','2021-10-25 13:30:33'),(1451845569286754305,'HM1451845569271525376',1449955397829902338,1449955397829902339,'87a2702d0df64669aa73924dd13bd969','ALIPAY_WAP',NULL,NULL,'2021102322001439050501237811','huimin_c2b',NULL,'企业名称商品','向企业名称付款','CNY',88800,NULL,NULL,NULL,'2',NULL,NULL,NULL,'172.16.42.122','2021-10-23 17:38:52',NULL,'2021-10-23 18:08:52','2021-10-25 13:30:33'),(1452503987622199297,'HM1452503987622895616',1449955397829902338,1449955397829902339,'87a2702d0df64669aa73924dd13bd969','ALIPAY_WAP',NULL,NULL,'2021102522001439050501242800','huimin_c2b',NULL,'企业名称商品','向企业名称付款','CNY',2200,NULL,NULL,NULL,'2',NULL,NULL,NULL,'172.16.42.122','2021-10-25 13:15:12',NULL,'2021-10-25 13:45:12','2021-10-25 13:28:54'),(1452504588779208705,'HM1452504588775710720',1449955397829902338,1449955397829902339,'87a2702d0df64669aa73924dd13bd969','ALIPAY_WAP',NULL,NULL,'2021102522001439050501242688','huimin_c2b',NULL,'企业名称商品','向企业名称付款','CNY',99800,NULL,NULL,NULL,'2',NULL,NULL,NULL,'172.16.42.122','2021-10-25 13:17:35',NULL,'2021-10-25 13:47:35','2021-10-25 13:28:23');
/*!40000 ALTER TABLE `pay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_bill`
--

DROP TABLE IF EXISTS `payment_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_bill` (
  `id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `merchant_name` varchar(60) NOT NULL COMMENT '商户名称',
  `merchant_app_id` bigint(20) NOT NULL COMMENT '商户应用Id',
  `merchant_order_no` varchar(60) NOT NULL COMMENT '商户订单号',
  `channel_order_no` varchar(60) NOT NULL COMMENT '渠道订单号',
  `product_name` varchar(255) NOT NULL COMMENT '商品名称',
  `create_time` varchar(60) DEFAULT NULL COMMENT '创建时间',
  `pos_time` varchar(60) NOT NULL COMMENT '交易时间',
  `equipment_no` varchar(60) DEFAULT NULL COMMENT '终端号',
  `user_account` varchar(60) DEFAULT NULL COMMENT '用户账号/标识信息',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `trade_amount` decimal(10,2) NOT NULL COMMENT '实际交易金额',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '折扣金额',
  `service_fee` decimal(10,4) DEFAULT NULL COMMENT '手续费',
  `refund_order_no` varchar(60) DEFAULT NULL COMMENT '退款单号',
  `refund_money` decimal(10,2) DEFAULT NULL,
  `platform_channel` varchar(50) NOT NULL COMMENT '平台支付渠道',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_bill`
--

LOCK TABLES `payment_bill` WRITE;
/*!40000 ALTER TABLE `payment_bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platform_channel`
--

DROP TABLE IF EXISTS `platform_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_channel` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_NAME` varchar(50) DEFAULT NULL COMMENT '平台支付渠道名称',
  `CHANNEL_CODE` varchar(50) DEFAULT NULL COMMENT '平台支付渠道编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platform_channel`
--

LOCK TABLES `platform_channel` WRITE;
/*!40000 ALTER TABLE `platform_channel` DISABLE KEYS */;
INSERT INTO `platform_channel` VALUES (1,'惠民B扫C','huimin_b2c'),(2,'惠民C扫B','huimin_c2b'),(3,'微信Native支付','wx_native'),(4,'支付宝手机网站支付','alipay_wap');
/*!40000 ALTER TABLE `platform_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platform_pay_channel`
--

DROP TABLE IF EXISTS `platform_pay_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_pay_channel` (
  `ID` bigint(20) NOT NULL,
  `PLATFORM_CHANNEL` varchar(20) DEFAULT NULL COMMENT '平台支付渠道编码',
  `PAY_CHANNEL` varchar(20) DEFAULT NULL COMMENT '原始支付渠道名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platform_pay_channel`
--

LOCK TABLES `platform_pay_channel` WRITE;
/*!40000 ALTER TABLE `platform_pay_channel` DISABLE KEYS */;
INSERT INTO `platform_pay_channel` VALUES (1,'huimin_b2c','WX_MICROPAY'),(2,'huimin_b2c','ALIPAY_BAR_CODE'),(3,'wx_native','WX_NATIVE'),(4,'alipay_wap','ALIPAY_WAP'),(5,'huimin_c2b','WX_JSAPI'),(6,'huimin_c2b','ALIPAY_WAP');
/*!40000 ALTER TABLE `platform_pay_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_order`
--

DROP TABLE IF EXISTS `refund_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_order` (
  `ID` bigint(20) NOT NULL,
  `REFUND_NO` varchar(50) DEFAULT NULL COMMENT '聚合支付退款订单号',
  `TRADE_NO` varchar(50) DEFAULT NULL COMMENT '聚合支付订单号',
  `MERCHANT_ID` bigint(20) DEFAULT NULL COMMENT '所属商户',
  `APP_ID` varchar(50) DEFAULT NULL COMMENT '所属应用',
  `PAY_CHANNEL` varchar(50) DEFAULT NULL COMMENT '原始支付渠道编码',
  `PAY_CHANNEL_MCH_ID` varchar(50) DEFAULT NULL COMMENT '原始渠道商户id',
  `PAY_CHANNEL_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道订单号',
  `PAY_CHANNEL_REFUND_NO` varchar(50) DEFAULT NULL COMMENT '原始渠道退款订单号',
  `CHANNEL` varchar(50) DEFAULT NULL COMMENT '聚合支付的渠道',
  `OUT_TRADE_NO` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `OUT_REFUND_NO` varchar(50) DEFAULT NULL COMMENT '商户退款订单号',
  `PAY_CHANNEL_USER` varchar(50) DEFAULT NULL COMMENT '原始渠道用户标识,如微信openId,支付宝账号',
  `PAY_CHANNEL_USERNAME` varchar(50) DEFAULT NULL COMMENT '原始渠道用户姓名',
  `CURRENCY` varchar(50) DEFAULT NULL COMMENT '币种CNY',
  `TOTAL_AMOUNT` int(11) DEFAULT NULL COMMENT '订单总金额，单位为分',
  `REFUND_AMOUNT` int(11) DEFAULT NULL COMMENT '退款金额,单位分',
  `OPTIONAL` varchar(256) DEFAULT NULL COMMENT '用户自定义的参数,商户自定义数据',
  `ANALYSIS` varchar(256) DEFAULT NULL COMMENT '用于统计分析的数据,用户自定义',
  `EXTRA` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `REFUND_STATE` varchar(50) DEFAULT NULL COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
  `REFUND_RESULT` varchar(50) DEFAULT NULL COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `ERROR_CODE` varchar(50) DEFAULT NULL COMMENT '渠道支付错误码',
  `ERROR_MSG` varchar(256) DEFAULT NULL COMMENT '渠道支付错误信息',
  `DEVICE` varchar(50) DEFAULT NULL COMMENT '设备',
  `CLIENT_IP` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '订单过期时间',
  `REFUND_SUCCESS_TIME` datetime DEFAULT NULL COMMENT '退款成功时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_order`
--

LOCK TABLES `refund_order` WRITE;
/*!40000 ALTER TABLE `refund_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-25 14:50:39
