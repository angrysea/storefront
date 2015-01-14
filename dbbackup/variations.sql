/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;

DROP TABLE IF EXISTS `packingslipitem`;
CREATE TABLE `packingslipitem` (
  `id` int(11) NOT NULL auto_increment,
  `packingslipid` int(11) default '0',
  `qtyshipped` int(11) default NULL,
  `salesorderitemid` int(11) default '0',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `packingslipitem` VALUES (24,35,1,101),(23,34,1,102),(25,36,2,103),(26,37,1,100),(27,37,1,99);

DROP TABLE IF EXISTS `salesorder`;
CREATE TABLE `salesorder` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `billingaddress` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `description` longtext,
  `totalcost` double default NULL,
  `taxes` double default '0',
  `taxesdesc` varchar(100) default '',
  `status` varchar(25) default NULL,
  `shipping` double default NULL,
  `shippingmethodid` int(11) default '0',
  `shippingweight` double default '0',
  `total` double default '0',
  `authorizationcode` varchar(100) default '',
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
INSERT INTO `salesorder` VALUES (77,'225',206,207,NULL,35,2.1,'NJ Sales Tax 6%','new',3.81,1,1,40.91,NULL,20040413170403),(78,'225',206,207,NULL,121.4,7.284,'NJ Sales Tax 6%','new',3.81,1,1,132.494,NULL,20040413170711),(79,'225',209,210,NULL,35,2.1,'NJ Sales Tax 6%','new',4.26,1,1,41.36,'010101',20040413173307),(81,'227',208,211,NULL,35,2.1,'NJ Sales Tax 6%','new',4.26,1,1,41.36,'010101',20040413174557),(82,'225',214,215,NULL,86.8,5.208,'NJ Sales Tax 6%','new',4.13,1,1,96.138,'010101',20040413175856),(83,'250',240,241,NULL,70,4.2,'NJ Sales Tax 6%','new',4.13,1,1,78.33,'010101',20040414173410),(84,'249',242,243,NULL,32,1.92,'NJ Sales Tax 6%','new',3.81,1,1,37.73,'010101',20040414173418),(85,'225',219,218,NULL,32,1.92,'NJ Sales Tax 6%','new',3.81,1,1,37.73,'010101',20040414173753),(86,'252',244,245,NULL,70,0,NULL,'new',4.81,1,1,74.81,'010101',20040414173859);

DROP TABLE IF EXISTS `salesorderitem`;
CREATE TABLE `salesorderitem` (
  `id` int(11) NOT NULL auto_increment,
  `salesorderid` int(11) default NULL,
  `trxtype` char(2) default '',
  `itemnumber` int(11) default NULL,
  `quantity` int(11) default '0',
  `shipped` int(11) default '0',
  `unitPrice` double default '0',
  `status` varchar(100) default '',
  `couponcode` varchar(100) default '',
  `exchangeid` varchar(100) default '',
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `salesorderid` (`salesorderid`)
) TYPE=MyISAM;
INSERT INTO `salesorderitem` VALUES (92,77,NULL,118,1,0,35,'',NULL,NULL,0),(93,78,NULL,94,1,0,121.4,'',NULL,NULL,0),(94,79,NULL,118,1,0,35,'',NULL,NULL,0),(96,81,NULL,118,1,0,35,'',NULL,NULL,0),(97,82,NULL,77,1,0,60.2,'',NULL,NULL,0),(98,82,NULL,69,1,0,26.6,'',NULL,NULL,0),(99,83,NULL,118,1,1,35,'shipped',NULL,NULL,0),(100,83,NULL,118,1,1,35,'shipped',NULL,NULL,0),(101,84,NULL,82,1,1,32,'shipped',NULL,NULL,0),(102,85,NULL,82,1,1,32,'shipped',NULL,NULL,0),(103,86,NULL,118,2,2,35,'shipped',NULL,NULL,0);

DROP TABLE IF EXISTS `variations`;
CREATE TABLE `variations` (
  `id` int(11) NOT NULL auto_increment,
  `quantity` int(11) default '-1',
  `clothingsize` varchar(100) default NULL,
  `clothingcolor` varchar(100) default NULL,
  `fabric` varchar(100) default '',
  `price` varchar(100) default NULL,
  `saleprice` varchar(100) default NULL,
  `shipdate` varchar(100) default NULL,
  `availability` varchar(100) default NULL,
  `multimerchant` varchar(100) default NULL,
  `merchantsku` varchar(100) default NULL,
  `imageurl1` varchar(100) default NULL,
  `imageurl2` varchar(100) default NULL,
  `imageurl3` varchar(100) default NULL,
  `imageurl4` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

DROP TABLE IF EXISTS `packingslip`;
CREATE TABLE `packingslip` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `trackingnumber` varchar(28) default NULL,
  `carriername` varchar(100) default '',
  `creationdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `packingslip` VALUES (36,252,245,NULL,NULL,'2004-04-19 16:28:37'),(35,249,243,NULL,NULL,'2004-04-19 16:24:05'),(34,225,218,NULL,NULL,'2004-04-19 16:21:07'),(33,225,218,NULL,NULL,'2004-04-19 16:18:49'),(32,225,218,NULL,NULL,'2004-04-19 16:06:14'),(31,249,243,NULL,NULL,'2004-04-19 15:54:29'),(30,250,241,NULL,NULL,'2004-04-19 15:44:32'),(29,252,0,'987987987987','UPS Ground','2004-04-14 18:27:16'),(37,250,241,'3413241234','UPS Ground','2004-04-19 16:46:13');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
