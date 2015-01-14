/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` int(11) default '0',
  `type` varchar(64) default NULL,
  `first` varchar(100) default '',
  `last` varchar(100) default '',
  `mi` varchar(100) default '',
  `salutation` varchar(100) default '',
  `suffix` varchar(100) default '',
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(100) default '',
  `company` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
INSERT INTO `address` (`id`,`customerid`,`type`,`first`,`last`,`mi`,`salutation`,`suffix`,`address1`,`address2`,`address3`,`city`,`state`,`zip`,`country`,`phone`,`company`) VALUES (168,170,'billing','Todd','Fearn',NULL,NULL,NULL,'44 Miller Rd','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(169,170,'currentshipping','Todd','Fearn',NULL,NULL,NULL,'44 Miller Rd','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(170,173,'currentbilling','Cliff','Fearn',NULL,NULL,NULL,'5198 Lime Rd','',NULL,'Galion','OH','44833','USA','419-222-2222',''),(171,173,'currentshipping','Cliff','Fearn',NULL,NULL,NULL,'5198 Lime Rd','',NULL,'Galion','OH','44833','USA','419-222-2222',''),(172,175,'currentbilling','Gina','Fearn',NULL,NULL,NULL,'44 Miller Rd','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(173,175,'currentshipping','Gina','Fearn',NULL,NULL,NULL,'44 Miller Rd','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(174,170,'billing','Todd','Fearn',NULL,'MR',NULL,'44 Miller Rd','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(175,180,'currentbilling','Mark','Hall',NULL,NULL,NULL,'Bucks Palace','',NULL,'London','AR','10101','USA','555-555-1212','');
INSERT INTO `address` (`id`,`customerid`,`type`,`first`,`last`,`mi`,`salutation`,`suffix`,`address1`,`address2`,`address3`,`city`,`state`,`zip`,`country`,`phone`,`company`) VALUES (176,180,'currentshipping','Mark','Hall',NULL,NULL,NULL,'Bucks Palace','',NULL,'London','AR','10101','USA','555-555-1212',''),(177,187,'currentbilling','Anthony','Graffeo',NULL,NULL,NULL,'3 Hasemann Court','',NULL,'West Caldwell','NJ','07006','USA','973-464-4028',''),(178,187,'currentshipping','Anthony','Graffeo',NULL,NULL,NULL,'3 Hasemann Court','',NULL,'West Caldwell','NJ','07006','USA','973-464-4028',''),(179,170,'currentbilling','Todd','Fearn',NULL,NULL,NULL,'44 Miller Road','',NULL,'Morristown','NJ','07960','USA','973-644-0974',''),(180,193,'currentbilling','Bill','Carson',NULL,NULL,NULL,'123 Anywhere','',NULL,'Carson City','NV','93933','USA','933-333-3333','');
CREATE TABLE `catalog` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(100) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `category` (
  `id` int(11) NOT NULL auto_increment,
  `groupid` int(11) NOT NULL default '1',
  `name` varchar(100) default '',
  `description` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `category` (`id`,`groupid`,`name`,`description`) VALUES (1,2,'Fixed Blade','Fixed blade knife\n'),(2,2,'Folding Knives','Folding Knives'),(3,2,'Gut Hook','Gut Hook'),(4,2,'Fillet Knives','Fillet Knives'),(5,2,'Scissors','Scissors'),(6,2,'Pruners','Pruners'),(7,2,'Saws','Saws'),(8,2,'Single Blade','Single Blade'),(9,2,'Pocket Knives','Pocket Knives'),(22,1,'Fishing','Fishing'),(11,2,'Traditional','Traditional'),(12,2,'High Tech','High Tech'),(13,2,'Multi-use Knives','Multi-use Knives'),(15,2,'Axes','Axes'),(16,1,'Everyday','Everyday'),(17,1,'Hunting','Hunting'),(18,1,'Outdoor','Outdoor'),(19,1,'Tactical','Tactical'),(20,1,'Limited Edition','Limited Edition'),(21,1,'Camping','Camping');
CREATE TABLE `categoryitem` (
  `id` int(11) NOT NULL auto_increment,
  `category` int(11) default '0',
  `itemnumber` int(11) default '0',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `categoryitem` (`id`,`category`,`itemnumber`) VALUES (49,17,50),(110,1,63),(48,13,50),(82,13,50),(109,22,62),(108,18,62),(107,17,62),(106,2,62),(91,17,59),(90,8,59),(105,22,61),(104,18,61),(103,17,61),(102,2,61),(101,22,60),(100,18,60),(99,17,60),(98,1,60),(97,22,60),(96,18,60),(95,17,60),(94,1,60),(93,22,59),(92,18,59),(89,1,59),(88,22,59),(87,18,59),(86,17,59),(85,8,59),(84,1,59),(83,17,50),(111,17,63),(112,18,63),(113,21,63),(114,22,63),(115,1,63),(116,17,63),(117,18,63),(118,21,63),(119,22,63),(120,1,64),(121,17,64),(122,18,64),(123,21,64),(124,22,64),(125,1,63),(126,17,63),(127,18,63),(128,21,63),(129,22,63),(130,1,63),(131,17,63),(132,18,63),(133,21,63),(134,22,63),(135,1,65),(136,17,65),(137,18,65),(138,21,65),(139,22,65),(140,1,66),(141,17,66),(142,18,66),(143,21,66),(144,22,66),(145,13,50),(146,17,50),(147,13,50),(148,17,50),(149,13,50),(150,17,50),(151,13,50),(152,17,50),(153,13,50),(154,17,50),(155,13,50),(156,17,50),(157,1,63),(158,17,63),(159,18,63),(160,21,63),(161,22,63);
INSERT INTO `categoryitem` (`id`,`category`,`itemnumber`) VALUES (162,13,50),(163,17,50),(164,1,59),(165,8,59),(166,17,59),(167,18,59),(168,22,59),(169,1,59),(170,8,59),(171,17,59),(172,18,59),(173,22,59),(174,1,60),(175,17,60),(176,18,60),(177,22,60),(178,1,60),(179,17,60),(180,18,60),(181,22,60),(182,1,66),(183,17,66),(184,18,66),(185,21,66),(186,22,66),(187,1,66),(188,17,66),(189,18,66),(190,21,66),(191,22,66),(192,1,66),(193,17,66),(194,18,66),(195,21,66),(196,22,66),(197,1,66),(198,17,66),(199,18,66),(200,21,66),(201,22,66),(202,1,66),(203,17,66),(204,18,66),(205,21,66),(206,22,66),(207,1,66),(208,17,66),(209,18,66),(210,21,66),(211,22,66),(212,1,59),(213,8,59),(214,17,59),(215,18,59),(216,22,59),(217,1,59),(218,8,59),(219,17,59),(220,18,59),(221,21,59),(222,22,59),(223,1,66),(224,17,66),(225,18,66),(226,21,66),(227,22,66),(228,1,66),(229,17,66),(230,18,66),(231,21,66),(232,22,66),(233,1,66),(234,17,66),(235,18,66),(236,21,66),(237,22,66),(238,1,65),(239,17,65),(240,18,65),(241,21,65),(242,22,65);
INSERT INTO `categoryitem` (`id`,`category`,`itemnumber`) VALUES (243,1,64),(244,17,64),(245,18,64),(246,21,64),(247,22,64),(248,1,63),(249,17,63),(250,18,63),(251,21,63),(252,22,63),(253,13,50),(254,17,50),(255,1,60),(256,17,60),(257,18,60),(258,22,60),(259,2,61),(260,17,61),(261,18,61),(262,22,61),(263,2,62),(264,17,62),(265,18,62),(266,22,62),(267,13,50),(268,17,50),(269,1,59),(270,8,59),(271,17,59),(272,18,59),(273,21,59),(274,22,59),(275,1,60),(276,17,60),(277,18,60),(278,22,60),(279,2,61),(280,17,61),(281,18,61),(282,22,61),(283,2,62),(284,17,62),(285,18,62),(286,22,62),(287,1,63),(288,17,63),(289,18,63),(290,21,63),(291,22,63),(292,1,64),(293,17,64),(294,18,64),(295,21,64),(296,22,64),(297,1,65),(298,17,65),(299,18,65),(300,21,65),(301,22,65),(302,1,66),(303,17,66),(304,18,66),(305,21,66),(306,22,66),(307,1,67),(308,8,67),(309,17,67),(310,19,67),(311,21,67),(312,1,67),(313,8,67),(314,17,67),(315,19,67),(316,21,67);
CREATE TABLE `comments` (
  `id` int(11) NOT NULL auto_increment,
  `comment` varchar(255) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `company` (
  `id` int(11) NOT NULL auto_increment,
  `company` varchar(128) default NULL,
  `description` longtext,
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(100) default '',
  `customerservice` varchar(100) default '',
  `support` varchar(100) default '',
  `fax` varchar(100) default '',
  `email1` varchar(100) default '',
  `email2` varchar(100) default '',
  `email3` varchar(100) default '',
  `pw` varchar(100) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `company` (`id`,`company`,`description`,`address1`,`address2`,`address3`,`city`,`state`,`zip`,`country`,`phone`,`customerservice`,`support`,`fax`,`email1`,`email2`,`email3`,`pw`) VALUES (1,'Fern Knives, a division of iDATA Corporation','<b>Fern Knives</b> is the foremost online retailer of quality hunting, fishing, tactial and recreational knives.  The company carries only the best brands like Buck, SOG, Bench Mark, Camillus and many others. ','248 Columbia Turnpike','Suite 102',NULL,'Florham Park','NJ','07932','USA','973-360-0086','973-360-0088','973-360-0089','973-360-0086','info@fernknives.com','','','joe6878');
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(100) default '',
  `itemid` int(11) default '0',
  `description` longtext,
  `quantitylimit` int(11) default '0',
  `discount` double default '0',
  `discounttype` int(11) default '0',
  `redemptiondate` datetime default '0000-00-00 00:00:00',
  `experationdate` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL default '0',
  `type` varchar(30) default NULL,
  `number` varchar(28) default NULL,
  `expmonth` char(2) default NULL,
  `expyear` varchar(4) default '',
  `cardholder` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `creditcard` (`id`,`type`,`number`,`expmonth`,`expyear`,`cardholder`) VALUES (168,NULL,'373273418891000','09','2004',NULL),(169,NULL,NULL,NULL,NULL,NULL),(170,NULL,'373273418891000','09','2004',NULL),(171,NULL,NULL,NULL,NULL,NULL),(172,NULL,'373273418891000','09','2004',NULL),(173,NULL,NULL,NULL,NULL,NULL),(174,NULL,'4828641180120019','01','2006',NULL),(175,NULL,'','0','0',NULL),(176,NULL,NULL,NULL,NULL,NULL),(177,NULL,'373273418892040','02','2010',NULL),(178,NULL,NULL,NULL,NULL,NULL),(179,NULL,'373273418891000','09','2004',NULL),(180,NULL,'','0','0',NULL);
CREATE TABLE `customer` (
  `id` int(11) NOT NULL default '0',
  `first` varchar(40) default NULL,
  `last` varchar(40) default NULL,
  `mi` char(1) default NULL,
  `salutation` varchar(6) default NULL,
  `suffix` varchar(8) default NULL,
  `fullname` varchar(128) default NULL,
  `nickname` varchar(128) default NULL,
  `home` varchar(14) default NULL,
  `mobil` varchar(14) default NULL,
  `work` varchar(14) default NULL,
  `fax` varchar(14) default NULL,
  `email1` varchar(128) default NULL,
  `email2` varchar(128) default NULL,
  `email3` varchar(128) default NULL,
  `birthdaymonth` char(2) default NULL,
  `birthdayyear` char(2) default NULL,
  `ccflag` tinyint(4) default '0',
  `url` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `customer` (`id`,`first`,`last`,`mi`,`salutation`,`suffix`,`fullname`,`nickname`,`home`,`mobil`,`work`,`fax`,`email1`,`email2`,`email3`,`birthdaymonth`,`birthdayyear`,`ccflag`,`url`) VALUES (170,'Todd','Fearn',NULL,'MR',NULL,'MR. Todd Fearn',NULL,NULL,NULL,NULL,NULL,'tfearn@yahoo.com',NULL,NULL,NULL,NULL,0,NULL),(173,'Cliff','Fearn',NULL,NULL,NULL,' Cliff Fearn',NULL,NULL,NULL,NULL,NULL,'cfearn@yahoo.com',NULL,NULL,NULL,NULL,0,NULL),(175,'Gina','Fearn',NULL,NULL,NULL,' Gina Fearn',NULL,NULL,NULL,NULL,NULL,'ginafearn@yahoo.com',NULL,NULL,NULL,NULL,0,NULL),(180,'Mark','Hall',NULL,NULL,NULL,' Mark Hall',NULL,NULL,NULL,NULL,NULL,'mhall@patiolife.com',NULL,NULL,NULL,NULL,0,NULL),(193,'Bill','Carson',NULL,NULL,NULL,' Bill Carson',NULL,NULL,NULL,NULL,NULL,'bcarson@yahoo.com',NULL,NULL,NULL,NULL,0,NULL),(187,'Anthony','Graffeo',NULL,NULL,NULL,' Anthony Graffeo',NULL,NULL,NULL,NULL,NULL,'amg@141.com',NULL,NULL,NULL,NULL,0,NULL);
CREATE TABLE `customerreview` (
  `id` int(11) NOT NULL auto_increment,
  `reviewid` int(11) default NULL,
  `rating` varchar(128) default NULL,
  `reviewdate` timestamp(14) NOT NULL,
  `summary` varchar(128) default NULL,
  `comment` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `reviewid` (`reviewid`)
) TYPE=MyISAM;
CREATE TABLE `details` (
  `itemnumber` int(11) NOT NULL default '0',
  `manufactureritemnumber` varchar(100) default '',
  `distributoritemnumber` varchar(100) default '',
  `description` longtext,
  `catalog` varchar(128) default NULL,
  `imageurlsmall` varchar(128) default NULL,
  `imageurlmedium` varchar(128) default NULL,
  `imageurllarge` varchar(128) default NULL,
  `salesrank` int(11) default NULL,
  `availability` varchar(128) default NULL,
  `shippingweight` double default '0',
  `url` varchar(128) default NULL,
  PRIMARY KEY  (`itemnumber`)
) TYPE=MyISAM;
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (64,'1218','KB-1218','The most famous knife in the World gets updated with a serrated edge to better cut synthetic and looped materials.',NULL,'./images/KB-1218small.jpg','./images/KB-1218medium.jpg','./images/KB-1218large.jpg',0,NULL,1,NULL),(50,'815BK','BU-1679','Tough shears for home, shop or game. Integrated screwdriver and bottle opener--pry open lids & bottles, use notched jaws to open screw-top containers. Easy-clean, take-apart design. \r\n',NULL,'./images/BU-1679small.jpg','./images/BU-1679medium.jpg','./images/BU-1679large.jpg',0,NULL,1,NULL),(67,'S1','SOG-S1','SOG\'s flagship is a replica of the original knife used by the Vietnam War Era\'s 5th Special Forces Group known as the Studies and Observation Group (SOG). Secretly developed and used in covert missions, the SOG Bowie was a steadfast companion in one of the harshest environments in the world. <br>\r\n<br>\r\nMade in the same factory as the original, the SOG Bowie has become a classic knife for enthusiasts around the world and is unparalleled in authenticity and quality. Like the original, it has a gun-blued carbon steel blade and an exquisite epoxied, stacked leather washer handle. The thick blade, deep hollow grind and faceted reinforced tip make this an appealing field knife to be used for a variety of tough chores and missions. It is also one of the most collected pieces by knife enthusiasts around the world. It carries in a high-quality leather scabbard equipped with a special sharpening stone and also comes with a classic leather lanyard. \r\n\r\n \r\n',NULL,'./images/SOG-S1small.gif','./images/SOG-S1medium.gif','./images/SOG-S1large.gif',0,NULL,1,NULL),(63,'1217','KB-1217','The most famous fixed blade knife in the World - \"the KA-BAR\" - was designed to serve our troops during World War II and is still doing its job, with honors, more than 50 years later.\r\n',NULL,'./images/KB-1217small.jpg','./images/KB-1217medium.jpg','./images/KB-1217large.jpg',0,NULL,1,NULL);
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (62,'112FG','BU-2539','Ranger with finger grooves to look and feel just right. Brass bolsters, liners and rivets. Classic wood handle. \r\n\r\n ',NULL,'./images/BU-2539small.jpg','./images/BU-2539medium.jpg','./images/BU-2539large.jpg',0,NULL,1,NULL),(61,'110FG','BU-2538','Famous Folding Hunter with finger grooves to look and feel just right. Brass bolsters, liners and rivets. Classic wood handle. \r\n\r\n ',NULL,'./images/BU-2538small.jpg','./images/BU-2538medium.jpg','./images/BU-2538large.jpg',0,NULL,1,NULL),(60,'105','BU-2535','Medium size fixed-blade hunting knife--great for general outdoor use. Polished aluminum butt and guard. \r\n\r\n* Weight: 4.5 oz. (127 g.)\r\n* Handle Material: Phenolic\r\n* Carry System: Black leather sheath\r\n* Blade Steel: 420HC\r\n* Length Overall: 9 1/8\" (23.2 cm.)\r\n* Blade Length: 5\" (12.7 cm.)\r\n* Blade Shape: Modified clip\r\n ',NULL,'./images/BU-2535small.jpg','./images/BU-2535medium.jpg','./images/BU-2535large.jpg',0,NULL,1,NULL),(59,'102BR','BU-2534','Smaller fixed-blade hunting knife is perfect for small game, as well as fishing or camping activities. Polished brass butt and guard. Cocobola handle. ',NULL,'./images/BU-2534small.jpg','./images/BU-2534medium.jpg','./images/BU-2534large.jpg',0,NULL,1,NULL);
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (65,'1211','KB-1211','A practical, all-purpose utility knife featuring ergonomically designed slip resistant handle. Serves every task perfectly. \r\n\r\n',NULL,'./images/KB-1211small.jpg','./images/KB-1211medium.jpg','./images/KB-1211large.jpg',0,NULL,1,NULL),(66,'1212','KB-1212','A partially serrated edge makes cutting looped and synthetic material a breeze. Blade marked 1211. \r\n',NULL,'./images/KB-1212small.jpg','./images/KB-1212medium.jpg','./images/KB-1212large.jpg',0,NULL,1,NULL);
CREATE TABLE `distributor` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `distributor` (`id`,`name`,`description`) VALUES (4,'National Knife','Wholesaler and distributor of pocket knives, tactical knives, automatic knives, swords, collectibles, and Zippo lighters.');
CREATE TABLE `featuredproducts` (
  `id` int(11) NOT NULL auto_increment,
  `sortorder` int(11) default '0',
  `itemnumber` int(11) default NULL,
  `type` varchar(25) default '',
  `heading` varchar(255) default '',
  `comments` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
INSERT INTO `featuredproducts` (`id`,`sortorder`,`itemnumber`,`type`,`heading`,`comments`) VALUES (1,1,64,'group1','KA-BAR Fighting Knives','The most famous knife in the World gets updated with a serrated edge to better cut synthetic and looped materials.'),(2,2,65,'group1','',''),(6,1,61,'group3','Buck\'s New Folding Knives','Check out the new folding knives from Buck!'),(3,3,63,'group1','',''),(5,1,50,'group2','Buck Introduces Utility Shears','These utility shears kick ass!!!!!'),(7,2,59,'group3',NULL,NULL),(4,4,66,'group1',NULL,NULL),(8,3,62,'group3',NULL,NULL);
CREATE TABLE `grouptype` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL default '',
  `description` longtext NOT NULL,
  `image` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `grouptype` (`id`,`name`,`description`,`image`) VALUES (1,'Use','',''),(2,'Type','','');
CREATE TABLE `invoice` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `salesorderid` int(11) default NULL,
  `taxes` double default NULL,
  `shippingcost` double default NULL,
  `totalcost` double default NULL,
  `paymentmethod` double default NULL,
  `status` varchar(40) default NULL,
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`),
  KEY `salesorderid` (`salesorderid`)
) TYPE=MyISAM;
CREATE TABLE `invoiceitem` (
  `id` int(11) NOT NULL auto_increment,
  `invoiceid` int(11) unsigned zerofill NOT NULL default '00000000001',
  `unitprice` double default NULL,
  `totalprice` double default NULL,
  `itemnumber` varchar(12) default NULL,
  `exchangeid` varchar(12) default NULL,
  `quantity` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `invoiceid` (`invoiceid`)
) TYPE=MyISAM;
CREATE TABLE `item` (
  `id` int(11) NOT NULL auto_increment,
  `isin` varchar(128) default NULL,
  `productname` varchar(100) default NULL,
  `manufacturer` int(11) default '0',
  `distributor` varchar(100) default '',
  `quantity` int(11) default NULL,
  `status` int(11) default '0',
  `listprice` double default NULL,
  `ourprice` double default '0',
  `ourcost` double default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `item` (`id`,`isin`,`productname`,`manufacturer`,`distributor`,`quantity`,`status`,`listprice`,`ourprice`,`ourcost`) VALUES (64,'KB-1218','USMC Fighting Knife, Serrated Edge',10,'4',0,1,67.4,39,31.7),(65,'KB-1211','Fighting Knife, Straight Edge',10,'4',0,1,67.4,41,33.7),(50,'BU-1679','Utility Shears',7,'4',0,1,36,25,18),(63,'KB-1217','USMC Fighting Knife, Straight Edge',10,'4',0,1,67.4,39,31.7),(62,'BU-2539','Ranger',7,'4',0,1,66,51,33),(61,'BU-2538','Folding Hunter',7,'4',0,1,69,49,34.5),(60,'BU-2535','Pathfinder',7,'4',0,1,61,50,30.5),(59,'BU-2534','Woodsman',7,'4',0,1,75,50,37.5),(66,'KB-1212','Fighting Knife, Serrated Edge',10,'4',0,1,67.4,42.5,33.7),(67,'SOG-S1','Bowie Knife',11,'4',1,1,274.95,191,164.97);
CREATE TABLE `itemranking` (
  `itemnumber` int(11) default '0',
  `views` int(11) default '0',
  `sold` int(11) default '0'
) TYPE=MyISAM;
INSERT INTO `itemranking` (`itemnumber`,`views`,`sold`) VALUES (64,48,18),(62,12,6),(60,22,6),(61,7,6),(63,17,13),(67,19,0);
CREATE TABLE `itemspecifications` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default '0',
  `specid` int(11) default '0',
  `description` longtext,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `itemspecifications` (`id`,`itemnumber`,`specid`,`description`) VALUES (307,65,13,'Made in USA'),(306,65,12,'Flat  '),(328,67,8,'Leather w/Sharpening Stone'),(317,66,14,'Kraton G  '),(316,66,13,'Made in USA'),(315,66,12,'Flat  '),(314,66,11,'20 Degrees'),(313,66,10,'Clip Handle'),(312,66,9,'11 3/4 in.'),(311,66,6,'0.66 lbs.'),(309,66,1,'1095 Carbon Country'),(261,59,9,'7 3/4 in. (19.7 cm.)'),(310,66,3,'7 in.'),(260,59,8,'Brown leather sheath'),(259,59,7,'Cocobola'),(258,59,6,'2.5 oz. (71 g.)'),(257,59,5,'Fixed Blade'),(256,59,4,'Clip'),(255,59,3,'4 in. (10.2 cm.)'),(254,59,1,'420HC'),(305,65,11,'20 Degrees'),(304,65,10,'Clip Handle'),(303,65,9,'11 3/4 in.'),(302,65,6,'0.66 lbs.'),(301,65,3,'7 in.'),(300,65,1,'1095 Carbon'),(296,64,10,'Clip Handle'),(295,64,9,'11 7/8 in.'),(294,64,8,'Leather'),(293,64,6,'0.68 lbs.'),(292,64,3,'7 in.'),(291,64,1,'1095 Carbon '),(289,63,11,'20 Degrees '),(288,63,10,'Clip Handle '),(287,63,9,'11 7/8 in.'),(286,63,8,'Leather  '),(285,63,6,'0.68 lbs.'),(284,63,3,'7 in.');
INSERT INTO `itemspecifications` (`id`,`itemnumber`,`specid`,`description`) VALUES (283,63,1,'1095 Carbon'),(252,50,9,'8 1/2 in. (21.6 cm.)'),(251,50,7,'Thermoplastic'),(250,50,6,'4 oz. (113 g.)'),(266,60,7,'Phenolic'),(265,60,6,'4.5 oz. (127 g.)'),(264,60,4,'Modified clip'),(263,60,3,'5 in. (12.7 cm.)'),(262,60,1,'420HC'),(273,61,6,'7.2 oz. (204 g.)'),(272,61,4,'Clip'),(271,61,3,'3 3/4 in. (9.5 cm.)'),(270,61,2,'4 7/8 in. (12.4 cm.)'),(269,61,1,'420HC'),(280,62,6,'5.6 oz. (158 g.)'),(279,62,4,'Clip'),(278,62,3,'3 in. (7.6 cm.)'),(277,62,2,'4 1/4 in. (10.8 cm.)'),(276,62,1,'420HC'),(253,50,14,'Two-piece steel'),(267,60,8,'Black leather sheath'),(268,60,9,'9 1/8 in. (23.2 cm.)'),(274,61,7,'Natural woodgrain'),(275,61,8,'Black leather sheath'),(281,62,7,'Natural woodgrain'),(282,62,8,'Black leather sheath'),(290,63,13,'Made in USA'),(297,64,11,'20 Degrees '),(298,64,12,'Flat  '),(299,64,13,'Made in USA'),(308,65,14,'Kraton G'),(327,67,7,'Expoxied Stacked Leather'),(326,67,6,'12.3 oz.'),(325,67,3,'6.25 in.'),(324,67,1,'Sk-5 Carbon Steel, Gun Blued');
INSERT INTO `itemspecifications` (`id`,`itemnumber`,`specid`,`description`) VALUES (329,67,9,'10.75 in.');
CREATE TABLE `itemstatus` (
  `id` int(11) NOT NULL auto_increment,
  `status` varchar(100) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `itemstatus` (`id`,`status`) VALUES (1,'In Stock'),(3,'Discontinued'),(2,'Out of Stock');
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default '0',
  `value` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `keywords` (`id`,`itemnumber`,`value`) VALUES (1,55,'Buck Knives'),(2,55,'National Knife'),(3,55,'mb101'),(4,55,'mini buck'),(5,56,'Buck Knives'),(6,56,'National Knife'),(7,56,'mb101'),(8,56,'mini buck'),(9,57,'Buck Knives'),(10,57,'National Knife'),(11,57,'mb101'),(12,57,'mini buck'),(13,58,'Buck Knives'),(14,58,'National Knife'),(15,58,'mb101'),(16,58,'mini buck'),(17,58,'Folding Knives'),(18,58,'Pocket Knives'),(19,58,'Everyday'),(20,58,'Outdoor'),(268,61,'Outdoor'),(267,61,'Hunting'),(266,61,'Folding Knives'),(265,61,'Folding Hunter'),(264,61,'BU-2538'),(275,62,'Hunting'),(274,62,'Folding Knives'),(273,62,'Ranger'),(272,62,'BU-2539'),(254,60,'Buck'),(260,60,'Outdoor'),(259,60,'Hunting'),(258,60,'Fixed blade knife\n'),(257,60,'Pathfinder'),(256,60,'BU-2535'),(293,64,'Outdoor'),(292,64,'Hunting'),(291,64,'Fixed blade knife\n'),(290,64,'USMC Fighting Knife, Serrated Edge'),(289,64,'KB-1218'),(302,65,'Outdoor'),(301,65,'Hunting'),(300,65,'Fixed blade knife\n'),(299,65,'Fighting Knife, Straight Edge'),(298,65,'KB-1211');
INSERT INTO `keywords` (`id`,`itemnumber`,`value`) VALUES (313,66,'Fishing'),(312,66,'Camping'),(310,66,'Hunting'),(311,66,'Outdoor'),(255,60,'National Knife'),(309,66,'Fixed blade knife\n'),(308,66,'Fighting Knife, Serrated Edge'),(307,66,'KB-1212'),(306,66,'National Knife'),(305,66,'Ka-Bar'),(251,59,'Outdoor'),(250,59,'Hunting'),(249,59,'Single Blade'),(248,59,'Fixed blade knife\n'),(247,59,'Woodsman'),(246,59,'BU-2534'),(245,59,'National Knife'),(244,59,'Buck'),(303,65,'Camping'),(297,65,'National Knife'),(296,65,'Ka-Bar'),(294,64,'Camping'),(288,64,'National Knife'),(287,64,'Ka-Bar'),(283,63,'Hunting'),(282,63,'Fixed blade knife\n'),(281,63,'USMC Fighting Knife, Straight Edge'),(280,63,'KB-1217'),(279,63,'National Knife'),(278,63,'Ka-Bar'),(242,50,'Multi-use Knives'),(241,50,'Utility Shears'),(240,50,'BU-1679'),(239,50,'National Knife'),(238,50,'Buck'),(263,61,'National Knife'),(262,61,'Buck'),(271,62,'National Knife'),(270,62,'Buck'),(243,50,'Hunting'),(252,59,'Camping'),(253,59,'Fishing'),(261,60,'Fishing');
INSERT INTO `keywords` (`id`,`itemnumber`,`value`) VALUES (269,61,'Fishing'),(276,62,'Outdoor'),(277,62,'Fishing'),(284,63,'Outdoor'),(285,63,'Camping'),(286,63,'Fishing'),(295,64,'Fishing'),(304,65,'Fishing'),(329,67,'Hunting'),(328,67,'Single Blade'),(327,67,'Fixed blade knife\n'),(326,67,'Bowie Knife'),(325,67,'SOG-S1'),(324,67,'National Knife'),(323,67,'SOG'),(330,67,'Tactical'),(331,67,'Camping');
CREATE TABLE `mailconfig` (
  `id` varchar(100) NOT NULL default '',
  `mailhost` varchar(255) NOT NULL default '',
  `mailfromaddress` varchar(255) default '',
  `mailauthuser` varchar(100) default '',
  `mailauthpassword` varchar(100) default '',
  `mailreplyto` varchar(255) default '',
  `mailintroduction` longtext,
  `mailbody` longtext,
  `mailsignature` longtext,
  `mailsubject` longtext,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `mailconfig` (`id`,`mailhost`,`mailfromaddress`,`mailauthuser`,`mailauthpassword`,`mailreplyto`,`mailintroduction`,`mailbody`,`mailsignature`,`mailsubject`) VALUES ('order','mail.idata.net','fernknives@idata.net','idatan_7','knives','','Thanks for shopping at Fern Knives. We have received your order and will ship it promptly. When it ships, we\'ll send you another e-mail to let you know.\n','For further assistance, please contact our Customer Service Department.','--------------------------------------------------\n\nIf you have any questions, you can reply to this e-mail or call us \nbetween 9:00 AM and 7:00 PM Eastern time.\n\nUS & Canada: 800-555-1212\nAll other countries call: (International Access Code) +973-360-0086\n\n\nThanks again!\n\nFern Knives \nhttp://www.fernknives.com','Thank you for ordering Fern Knives.'),('password','mail.idata.net','fernknives@idata.net','idatan_7','knives',NULL,'The password for your e-mail address',NULL,'Regards,\n \nFern Knives\n','From Fern Knives');
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  PRIMARY KEY  (`id`),
  KEY `name` (`name`)
) TYPE=MyISAM;
INSERT INTO `manufacturer` (`id`,`name`,`description`) VALUES (10,'Ka-Bar','KA-BAR Knives, Inc., a subsidiary of Alcas Corporation, manufactures high quality military, hunting, sporting, and all-purpose utility knives. '),(11,'SOG','SOG originally stood for Studies and Observation Group, an elite joint services military group designed for covert operations in the Vietnam War. Sanctioned to develop and purchase their own equipment, SOG created a knife for use in one of the harshest environments in the world. It is in the spirit of this elite group that SOG Specialty Knives was founded. <br>\n<br>\nTotally committed to creating the world\'s finest specialized knives and tools, SOG became the first knife manufacturer to expand its line to include a broad scope of fixed blades, folding knives and multipurpose tools. Each product is created by Cofounder and Chief Engineer, Spencer Frazer. His patented inventions and unique, futuristic style have earned SOG many awards and Frazer the recognition as an industry innovator and premier designer. <br>\n<br>\nToday, SOG products are distributed around the world and renowned for their uncompromising style and performance. '),(7,'Buck','Buck Knives-since 1902 famous manufacturer of pocket, folding & fixed blade knives & multi-use equipment for sports, work, utility and outdoor recreation-hunting, fishing, camping, hiking, backpacking, climbing, survival/tactical, diving/boating. Also custom & collector knives.\n');
CREATE TABLE `package` (
  `id` int(11) NOT NULL auto_increment,
  `invoiceitemid` int(11) default NULL,
  `trackingnumber` varchar(28) default NULL,
  `carriername` varchar(40) default NULL,
  PRIMARY KEY  (`id`),
  KEY `invoiceitemid` (`invoiceitemid`)
) TYPE=MyISAM;
CREATE TABLE `packageitem` (
  `id` int(11) NOT NULL auto_increment,
  `packageid` int(11) default NULL,
  `invoiceitemid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `packageid` (`packageid`),
  KEY `invoiceitemid` (`invoiceitemid`)
) TYPE=MyISAM;
CREATE TABLE `recentlyviewed` (
  `id` int(11) NOT NULL auto_increment,
  `userid` varchar(40) default NULL,
  `itemnumber` int(11) default NULL,
  `viewedtime` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (69,'125',59,'2004-03-26 12:37:48'),(68,'123',59,'2004-03-26 12:06:45'),(67,'120',59,'2004-03-26 11:57:23'),(66,'117',62,'2004-03-26 11:46:21'),(65,'115',59,'2004-03-26 10:47:29'),(64,'113',59,'2004-03-26 10:41:42'),(63,'111',59,'2004-03-26 10:33:20'),(62,'109',60,'2004-03-26 10:22:27'),(61,'107',59,'2004-03-26 10:19:51'),(60,'102',60,'2004-03-26 09:28:47'),(59,'100',60,'2004-03-26 09:16:52'),(58,'98',66,'2004-03-25 20:47:38'),(57,'98',60,'2004-03-25 20:47:14'),(56,'97',59,'2004-03-25 20:44:10'),(55,'91',59,'2004-03-25 17:56:42'),(54,'89',59,'2004-03-25 17:52:49'),(53,'87',59,'2004-03-25 17:45:40'),(52,'85',59,'2004-03-25 17:30:06'),(51,'84',59,'2004-03-25 17:28:23'),(50,'82',64,'2004-03-25 17:22:49'),(49,'80',60,'2004-03-25 17:19:10'),(48,'78',59,'2004-03-25 17:13:18'),(47,'76',59,'2004-03-25 17:06:24'),(46,'74',59,'2004-03-25 16:29:54'),(45,'73',60,'2004-03-25 15:39:04'),(44,'72',60,'2004-03-25 15:20:11'),(43,'71',60,'2004-03-25 15:16:46'),(70,'125',59,'2004-03-26 12:46:19');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (71,'131',59,'2004-03-26 13:42:50'),(72,'131',63,'2004-03-26 15:56:32'),(73,'131',65,'2004-03-26 18:24:53'),(74,'131',61,'2004-03-26 18:25:36'),(75,'134',59,'2004-03-26 20:08:16'),(76,'134',59,'2004-03-26 20:08:20'),(77,'131',59,'2004-03-26 20:09:29'),(78,'131',61,'2004-03-26 20:10:32'),(79,'131',59,'2004-03-26 20:16:51'),(80,'131',60,'2004-03-26 20:17:10'),(81,'136',65,'2004-03-27 12:47:25'),(82,'136',60,'2004-03-28 10:39:58'),(83,'136',62,'2004-03-29 10:02:34'),(84,'138',63,'2004-03-29 10:03:42'),(85,'136',59,'2004-03-29 10:50:40'),(86,'136',59,'2004-03-29 10:55:08'),(87,'136',62,'2004-03-29 10:55:13'),(88,'136',63,'2004-03-29 10:59:12'),(89,'141',60,'2004-03-29 11:01:00'),(90,'143',62,'2004-03-29 11:04:37'),(91,'146',63,'2004-03-29 11:16:00'),(92,'151',62,'2004-03-29 11:56:53'),(93,'153',62,'2004-03-29 12:07:14'),(94,'155',59,'2004-03-29 12:17:04'),(95,'156',62,'2004-03-29 12:29:54'),(96,'157',59,'2004-03-29 12:49:18'),(97,'159',59,'2004-03-29 12:54:22');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (98,'161',63,'2004-03-29 12:57:26'),(99,'163',63,'2004-03-29 13:01:38'),(100,'165',63,'2004-03-29 13:05:48'),(101,'167',63,'2004-03-29 13:07:51'),(102,'169',62,'2004-03-29 13:13:37'),(103,'170',63,'2004-03-29 13:16:23'),(104,'171',50,'2004-03-29 13:17:15'),(105,'174',66,'2004-03-29 13:15:53'),(106,'175',59,'2004-03-29 13:30:13'),(107,'170',60,'2004-03-29 15:02:24'),(108,'170',62,'2004-03-29 15:05:52'),(109,'170',61,'2004-03-29 20:27:09'),(110,'170',50,'2004-03-29 20:27:58'),(111,'170',59,'2004-03-29 20:29:29'),(112,'170',59,'2004-03-29 20:33:16'),(113,'170',59,'2004-03-29 20:42:35'),(114,'177',59,'2004-03-30 09:39:53'),(115,'170',59,'2004-03-30 12:12:38'),(116,'170',63,'2004-03-30 12:59:36'),(117,'170',64,'2004-03-30 14:21:19'),(118,'170',60,'2004-03-30 14:22:38'),(119,'170',61,'2004-03-30 14:24:15'),(120,'170',60,'2004-03-30 14:27:43'),(121,'170',60,'2004-03-30 14:30:49'),(122,'170',64,'2004-03-30 14:42:26'),(123,'170',62,'2004-03-30 14:52:19');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (124,'170',63,'2004-03-30 14:52:34'),(125,'170',64,'2004-03-30 15:01:10'),(126,'170',64,'2004-03-30 15:02:49'),(127,'170',64,'2004-03-30 15:03:08'),(128,'170',64,'2004-03-30 15:04:36'),(129,'170',60,'2004-03-30 15:04:38'),(130,'170',61,'2004-03-30 15:04:42'),(131,'170',61,'2004-03-30 15:04:50'),(132,'170',64,'2004-03-30 15:04:56'),(133,'170',60,'2004-03-30 15:05:08'),(134,'170',64,'2004-03-30 15:09:12'),(135,'170',60,'2004-03-30 15:09:15'),(136,'170',60,'2004-03-30 15:09:19'),(137,'170',61,'2004-03-30 15:09:24'),(138,'170',63,'2004-03-30 15:09:29'),(139,'170',64,'2004-03-30 15:09:36'),(140,'170',63,'2004-03-30 15:10:12'),(141,'178',60,'2004-03-30 15:31:23'),(142,'178',60,'2004-03-30 15:32:00'),(143,'170',64,'2004-03-30 17:21:11'),(144,'170',50,'2004-03-30 17:21:15'),(145,'170',50,'2004-03-30 17:21:23'),(146,'170',50,'2004-03-30 17:21:29'),(147,'170',64,'2004-03-30 17:21:59'),(148,'170',50,'2004-03-30 17:22:17'),(149,'170',50,'2004-03-30 17:22:19');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (150,'170',64,'2004-03-30 17:22:34'),(151,'170',50,'2004-03-30 17:26:29'),(152,'170',59,'2004-03-30 17:29:32'),(153,'170',50,'2004-03-30 17:29:50'),(154,'181',64,'2004-03-30 17:33:27'),(155,'181',63,'2004-03-30 17:35:35'),(156,'181',64,'2004-03-30 17:37:43'),(157,'170',59,'2004-03-30 17:49:10'),(158,'182',60,'2004-03-30 17:58:08'),(159,'182',62,'2004-03-30 18:00:09'),(160,'183',62,'2004-03-30 18:01:15'),(161,'184',64,'2004-03-30 18:32:05'),(162,'184',50,'2004-03-30 18:35:52'),(163,'184',64,'2004-03-30 18:49:09'),(164,'184',60,'2004-03-30 18:49:19'),(165,'184',60,'2004-03-30 18:50:24'),(166,'184',50,'2004-03-30 19:35:57'),(167,'184',50,'2004-03-30 19:36:07'),(168,'184',59,'2004-03-30 19:36:15'),(169,'186',60,'2004-03-30 20:34:21'),(170,'186',62,'2004-03-30 20:35:14'),(171,'186',60,'2004-03-30 20:38:18'),(172,'183',64,'2004-03-31 10:28:11'),(173,'183',64,'2004-03-31 10:33:40'),(174,'183',64,'2004-03-31 10:33:47'),(175,'183',64,'2004-03-31 10:46:06');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (176,'183',64,'2004-03-31 10:51:00'),(177,'183',59,'2004-03-31 10:52:21'),(178,'183',64,'2004-03-31 11:05:34'),(179,'183',65,'2004-03-31 11:07:11'),(180,'183',66,'2004-03-31 11:12:43'),(181,'183',66,'2004-03-31 11:15:56'),(182,'183',66,'2004-03-31 11:17:01'),(183,'183',66,'2004-03-31 11:19:06'),(184,'183',66,'2004-03-31 11:20:26'),(185,'183',66,'2004-03-31 11:20:56'),(186,'183',66,'2004-03-31 11:22:23'),(187,'183',66,'2004-03-31 11:23:19'),(188,'183',66,'2004-03-31 11:24:24'),(189,'183',66,'2004-03-31 11:26:38'),(190,'183',66,'2004-03-31 11:27:46'),(191,'183',66,'2004-03-31 11:27:48'),(192,'183',66,'2004-03-31 11:29:18'),(193,'183',66,'2004-03-31 11:30:08'),(194,'183',66,'2004-03-31 11:30:19'),(195,'183',66,'2004-03-31 11:30:20'),(196,'183',65,'2004-03-31 11:30:44'),(197,'183',50,'2004-03-31 11:43:44'),(198,'183',50,'2004-03-31 11:44:28'),(199,'183',50,'2004-03-31 11:44:32'),(200,'183',50,'2004-03-31 12:04:50'),(201,'183',65,'2004-03-31 12:05:05');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (202,'183',64,'2004-03-31 12:05:23'),(203,'183',64,'2004-03-31 12:05:30'),(204,'183',64,'2004-03-31 12:06:41'),(205,'183',67,'2004-03-31 12:26:41'),(206,'183',67,'2004-03-31 12:31:37'),(207,'183',67,'2004-03-31 12:33:37'),(208,'183',64,'2004-03-31 12:49:25'),(209,'183',64,'2004-03-31 12:50:41'),(210,'183',62,'2004-03-31 12:51:02'),(211,'183',50,'2004-03-31 12:51:09'),(212,'183',67,'2004-03-31 12:51:20'),(213,'183',67,'2004-03-31 12:51:25'),(214,'183',67,'2004-03-31 12:52:07'),(215,'183',62,'2004-03-31 12:52:15'),(216,'183',67,'2004-03-31 12:52:34'),(217,'183',60,'2004-03-31 12:52:41'),(218,'183',50,'2004-03-31 12:52:48'),(219,'183',67,'2004-03-31 12:53:08'),(220,'170',62,'2004-03-31 12:53:45'),(221,'170',63,'2004-03-31 12:54:01'),(222,'170',67,'2004-03-31 12:54:14'),(223,'170',67,'2004-03-31 13:07:02'),(224,'170',67,'2004-03-31 13:07:53'),(225,'170',61,'2004-03-31 13:08:21'),(226,'170',64,'2004-03-31 13:11:17'),(227,'170',60,'2004-03-31 13:11:49');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (228,'170',60,'2004-03-31 13:13:40'),(229,'170',60,'2004-03-31 13:14:16'),(230,'170',60,'2004-03-31 13:14:54'),(231,'170',67,'2004-03-31 13:18:25'),(232,'170',64,'2004-03-31 13:18:43'),(233,'170',64,'2004-03-31 13:22:37'),(234,'170',62,'2004-03-31 13:22:51'),(235,'170',64,'2004-03-31 13:24:16'),(236,'170',62,'2004-03-31 14:05:56'),(237,'179',64,'2004-03-31 14:12:46'),(238,'179',62,'2004-03-31 14:13:02'),(239,'187',64,'2004-03-31 14:34:51'),(240,'170',61,'2004-03-31 14:39:57'),(241,'170',67,'2004-03-31 14:40:06'),(242,'189',64,'2004-03-31 15:11:40'),(243,'170',64,'2004-03-31 15:46:12'),(244,'170',64,'2004-03-31 15:46:52'),(245,'170',60,'2004-03-31 16:06:15'),(246,'187',64,'2004-03-31 16:16:56'),(247,'170',50,'2004-03-31 16:15:51'),(248,'187',64,'2004-03-31 16:19:17'),(249,'187',63,'2004-03-31 16:22:16'),(250,'187',62,'2004-03-31 16:26:02'),(251,'187',50,'2004-03-31 16:32:40'),(252,'187',50,'2004-03-31 16:31:45'),(253,'187',50,'2004-03-31 16:35:54');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (254,'187',50,'2004-03-31 16:42:46'),(255,'187',65,'2004-03-31 17:18:30'),(256,'187',64,'2004-03-31 17:32:27'),(257,'187',65,'2004-03-31 17:33:52'),(258,'187',65,'2004-03-31 17:34:03'),(259,'187',66,'2004-03-31 17:36:12'),(260,'187',66,'2004-03-31 17:39:32'),(261,'187',64,'2004-03-31 17:47:20'),(262,'191',64,'2004-03-31 17:47:40'),(263,'186',64,'2004-03-31 20:07:23'),(264,'186',62,'2004-03-31 20:07:46'),(265,'186',59,'2004-03-31 20:07:54'),(266,'186',63,'2004-03-31 20:08:08'),(267,'186',65,'2004-03-31 20:08:15'),(268,'186',67,'2004-03-31 20:08:30'),(269,'170',65,'2004-03-31 20:11:27'),(270,'173',64,'2004-03-31 20:28:35'),(271,'173',67,'2004-03-31 20:32:32'),(272,'173',67,'2004-03-31 20:34:09'),(273,'173',50,'2004-03-31 20:34:29'),(274,'173',50,'2004-03-31 20:35:27'),(275,'173',64,'2004-03-31 20:37:54'),(276,'173',64,'2004-03-31 20:38:51'),(277,'173',67,'2004-03-31 20:39:24'),(278,'173',67,'2004-03-31 20:40:54'),(279,'173',65,'2004-03-31 20:42:50');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (280,'173',65,'2004-03-31 20:43:54'),(281,'170',63,'2004-04-01 05:38:16'),(282,'170',63,'2004-04-01 05:39:56'),(283,'170',63,'2004-04-01 05:41:29'),(284,'170',63,'2004-04-01 05:42:40'),(285,'170',63,'2004-04-01 05:43:51'),(286,'170',67,'2004-04-01 05:44:15'),(287,'170',60,'2004-04-01 05:46:33'),(288,'170',64,'2004-04-01 05:51:02'),(289,'170',66,'2004-04-01 05:54:51'),(290,'170',65,'2004-04-01 06:05:15'),(291,'191',64,'2004-04-01 09:15:43'),(292,'187',63,'2004-04-01 10:05:04'),(293,'187',63,'2004-04-01 10:05:44'),(294,'187',66,'2004-04-01 12:44:15'),(295,'187',50,'2004-04-01 12:54:34'),(296,'187',59,'2004-04-01 13:16:01'),(297,'187',59,'2004-04-01 13:38:59'),(298,'187',65,'2004-04-01 13:39:19'),(299,'187',65,'2004-04-01 14:33:44'),(300,'187',65,'2004-04-01 14:53:30'),(301,'191',64,'2004-04-01 15:47:11'),(302,'187',63,'2004-04-01 15:51:44'),(303,'187',63,'2004-04-01 15:56:55'),(304,'187',65,'2004-04-01 15:57:54'),(305,'187',63,'2004-04-01 16:02:21');
INSERT INTO `recentlyviewed` (`id`,`userid`,`itemnumber`,`viewedtime`) VALUES (306,'193',66,'2004-04-01 16:20:20'),(307,'187',65,'2004-04-01 17:23:08'),(308,'187',50,'2004-04-01 17:23:15'),(309,'187',60,'2004-04-01 17:23:23'),(310,'170',59,'2004-04-01 17:22:16'),(311,'187',65,'2004-04-01 17:30:11'),(312,'187',61,'2004-04-01 17:30:22'),(313,'187',50,'2004-04-01 17:30:29');
CREATE TABLE `relatedproducts` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `comment` varchar(255) default NULL,
  `type` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
CREATE TABLE `reviews` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` varchar(12) default NULL,
  `avgCustomerrating` int(11) default NULL,
  `totalcustomerreviews` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
CREATE TABLE `salesorder` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `billingaddress` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `description` longtext,
  `totalcost` double default NULL,
  `taxes` double default '0',
  `taxesdesc` varchar(100) default '',
  `status` varchar(12) default NULL,
  `shipping` varchar(10) default NULL,
  `shippingmethodid` int(11) default '0',
  `shippingmethod` varchar(10) default NULL,
  `shippingweight` double default '0',
  `total` double default '0',
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
INSERT INTO `salesorder` (`id`,`customerid`,`billingaddress`,`shippingaddress`,`description`,`totalcost`,`taxes`,`taxesdesc`,`status`,`shipping`,`shippingmethodid`,`shippingmethod`,`shippingweight`,`total`,`creationtime`) VALUES (11,'170',168,169,NULL,51,3.06,'NJ Sales Tax 6%','new','0',0,NULL,0,54.06,20040329131518),(12,'170',168,169,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040329131637),(13,'173',170,171,NULL,25,0,NULL,'new','0',0,NULL,0,25,20040329131245),(14,'175',172,173,NULL,42.5,2.55,'NJ Sales Tax 6%','new','0',0,NULL,0,45.05,20040329131719),(15,'170',168,169,NULL,50,3,'NJ Sales Tax 6%','new','0',0,NULL,0,53,20040329150520),(16,'170',174,169,NULL,51,3.06,'NJ Sales Tax 6%','new','0',0,NULL,0,54.06,20040329150654),(17,'170',174,169,NULL,199,11.94,'NJ Sales Tax 6%','new','0',0,NULL,0,210.94,20040330114926),(18,'170',174,169,NULL,51,3.06,'NJ Sales Tax 6%','new','0',0,NULL,0,54.06,20040331132316),(19,'187',177,178,NULL,90,5.4,'NJ Sales Tax 6%','new','0',0,NULL,0,95.4,20040331141655),(20,'187',177,178,NULL,0,0,'NJ Sales Tax 6%','new','0',0,NULL,0,0,20040331141924);
INSERT INTO `salesorder` (`id`,`customerid`,`billingaddress`,`shippingaddress`,`description`,`totalcost`,`taxes`,`taxesdesc`,`status`,`shipping`,`shippingmethodid`,`shippingmethod`,`shippingweight`,`total`,`creationtime`) VALUES (21,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331143543),(22,'170',174,169,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331153848),(23,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331161738),(24,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331162111),(25,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331162226),(26,'187',177,178,NULL,51,3.06,'NJ Sales Tax 6%','new','0',0,NULL,0,54.06,20040331162642),(27,'187',177,178,NULL,25,1.5,'NJ Sales Tax 6%','new','0',0,NULL,0,26.5,20040331163315),(28,'187',177,178,NULL,25,1.5,'NJ Sales Tax 6%','new','0',0,NULL,0,26.5,20040331163159),(29,'187',177,178,NULL,25,1.5,'NJ Sales Tax 6%','new','0',0,NULL,0,26.5,20040331163607),(30,'187',177,178,NULL,25,1.5,'NJ Sales Tax 6%','new','0',0,NULL,0,26.5,20040331171121);
INSERT INTO `salesorder` (`id`,`customerid`,`billingaddress`,`shippingaddress`,`description`,`totalcost`,`taxes`,`taxesdesc`,`status`,`shipping`,`shippingmethodid`,`shippingmethod`,`shippingweight`,`total`,`creationtime`) VALUES (31,'187',177,178,NULL,41,2.46,'NJ Sales Tax 6%','new','0',0,NULL,0,43.46,20040331172010),(32,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331173252),(33,'187',177,178,NULL,41,2.46,'NJ Sales Tax 6%','new','0',0,NULL,0,43.46,20040331173444),(34,'187',177,178,NULL,42.5,2.55,'NJ Sales Tax 6%','new','0',0,NULL,0,45.05,20040331173622),(35,'187',177,178,NULL,42.5,2.55,'NJ Sales Tax 6%','new','0',0,NULL,0,45.05,20040331174035),(36,'187',177,178,NULL,42.5,2.55,'NJ Sales Tax 6%','new','0',0,NULL,0,45.05,20040331174109),(37,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040331174810),(38,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401100523),(39,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401100553),(40,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401100702);
INSERT INTO `salesorder` (`id`,`customerid`,`billingaddress`,`shippingaddress`,`description`,`totalcost`,`taxes`,`taxesdesc`,`status`,`shipping`,`shippingmethodid`,`shippingmethod`,`shippingweight`,`total`,`creationtime`) VALUES (41,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401110258),(42,'187',177,178,NULL,42.5,2.55,'NJ Sales Tax 6%','new','0',0,NULL,0,45.05,20040401124540),(43,'187',177,178,NULL,67.5,4.05,'NJ Sales Tax 6%','new','0',0,NULL,0,71.55,20040401125517),(44,'187',177,178,NULL,50,3,'NJ Sales Tax 6%','new','0',0,NULL,0,53,20040401131630),(45,'187',177,178,NULL,41,2.46,'NJ Sales Tax 6%','new','0',0,NULL,0,43.46,20040401143425),(46,'187',177,178,NULL,41,2.46,'NJ Sales Tax 6%','new','0',0,NULL,0,43.46,20040401145402),(47,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401155243),(48,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401155329),(49,'187',177,178,NULL,0,0,'NJ Sales Tax 6%','new','0',0,NULL,0,0,20040401155405),(50,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401155718);
INSERT INTO `salesorder` (`id`,`customerid`,`billingaddress`,`shippingaddress`,`description`,`totalcost`,`taxes`,`taxesdesc`,`status`,`shipping`,`shippingmethodid`,`shippingmethod`,`shippingweight`,`total`,`creationtime`) VALUES (51,'187',177,178,NULL,41,2.46,'NJ Sales Tax 6%','new','0',0,NULL,0,43.46,20040401155804),(52,'187',177,178,NULL,39,2.34,'NJ Sales Tax 6%','new','0',0,NULL,0,41.34,20040401160248),(53,'170',179,169,NULL,113,6.78,'NJ Sales Tax 6%','new','0',0,NULL,0,119.78,20040401171432),(54,'187',177,178,NULL,116,6.96,'NJ Sales Tax 6%','new','0',0,NULL,0,122.96,20040401172342),(55,'187',177,178,NULL,115,6.9,'NJ Sales Tax 6%','new','0',0,NULL,0,121.9,20040401173056);
CREATE TABLE `salesorderitem` (
  `id` int(11) NOT NULL auto_increment,
  `salesorderid` int(11) default NULL,
  `itemnumber` int(11) default NULL,
  `isin` varchar(100) default '',
  `productname` varchar(255) default '',
  `manufacturer` varchar(100) default '',
  `quantity` int(11) default '0',
  `unitPrice` double default '0',
  `status` varchar(100) NOT NULL default '',
  `exchangeid` varchar(100) default '',
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `salesorderid` (`salesorderid`)
) TYPE=MyISAM;
INSERT INTO `salesorderitem` (`id`,`salesorderid`,`itemnumber`,`isin`,`productname`,`manufacturer`,`quantity`,`unitPrice`,`status`,`exchangeid`,`giftoption`) VALUES (12,11,62,'BU-2539','Ranger','Buck',1,51,'In Stock',NULL,0),(13,12,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(14,13,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(15,14,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0),(16,15,60,'BU-2535','Pathfinder','Buck',1,50,'In Stock',NULL,0),(17,16,62,'BU-2539','Ranger','Buck',1,51,'In Stock',NULL,0),(18,17,61,'BU-2538','Folding Hunter','Buck',1,49,'In Stock',NULL,0),(19,17,59,'BU-2534','Woodsman','Buck',3,50,'In Stock',NULL,0),(20,18,62,'BU-2539','Ranger','Buck',1,51,'In Stock',NULL,0),(21,19,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(22,19,62,'BU-2539','Ranger','Buck',1,51,'In Stock',NULL,0),(23,21,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(24,22,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0);
INSERT INTO `salesorderitem` (`id`,`salesorderid`,`itemnumber`,`isin`,`productname`,`manufacturer`,`quantity`,`unitPrice`,`status`,`exchangeid`,`giftoption`) VALUES (25,23,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(26,24,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(27,25,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(28,26,62,'BU-2539','Ranger','Buck',1,51,'In Stock',NULL,0),(29,27,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(30,28,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(31,29,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(32,30,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(33,31,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(34,32,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(35,33,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(36,34,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0);
INSERT INTO `salesorderitem` (`id`,`salesorderid`,`itemnumber`,`isin`,`productname`,`manufacturer`,`quantity`,`unitPrice`,`status`,`exchangeid`,`giftoption`) VALUES (37,35,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0),(38,36,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0),(39,37,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(40,38,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(41,39,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(42,40,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(43,41,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(44,42,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0),(45,43,66,'KB-1212','Fighting Knife, Serrated Edge','Ka-Bar',1,42.5,'In Stock',NULL,0),(46,43,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(47,44,59,'BU-2534','Woodsman','Buck',1,50,'In Stock',NULL,0);
INSERT INTO `salesorderitem` (`id`,`salesorderid`,`itemnumber`,`isin`,`productname`,`manufacturer`,`quantity`,`unitPrice`,`status`,`exchangeid`,`giftoption`) VALUES (48,45,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(49,46,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(50,47,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(51,48,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(52,50,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(53,51,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(54,52,63,'KB-1217','USMC Fighting Knife, Straight Edge','Ka-Bar',1,39,'In Stock',NULL,0),(55,53,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(56,53,61,'BU-2538','Folding Hunter','Buck',1,49,'In Stock',NULL,0),(57,53,64,'KB-1218','USMC Fighting Knife, Serrated Edge','Ka-Bar',1,39,'In Stock',NULL,0),(58,54,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0);
INSERT INTO `salesorderitem` (`id`,`salesorderid`,`itemnumber`,`isin`,`productname`,`manufacturer`,`quantity`,`unitPrice`,`status`,`exchangeid`,`giftoption`) VALUES (59,54,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0),(60,54,60,'BU-2535','Pathfinder','Buck',1,50,'In Stock',NULL,0),(61,55,65,'KB-1211','Fighting Knife, Straight Edge','Ka-Bar',1,41,'In Stock',NULL,0),(62,55,61,'BU-2538','Folding Hunter','Buck',1,49,'In Stock',NULL,0),(63,55,50,'BU-1679','Utility Shears','Buck',1,25,'In Stock',NULL,0);
CREATE TABLE `searchresult` (
  `id` int(11) NOT NULL auto_increment,
  `searchtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `searchresultitem` (
  `id` int(11) NOT NULL auto_increment,
  `searchid` int(11) default NULL,
  `itemid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `searchid` (`searchid`),
  KEY `itemid` (`itemid`)
) TYPE=MyISAM;
CREATE TABLE `shippingmethods` (
  `id` int(11) NOT NULL auto_increment,
  `description` longtext,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `shoppingcartitems` (
  `id` int(11) NOT NULL auto_increment,
  `userid` int(11) default NULL,
  `quantity` int(11) default NULL,
  `itemid` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  `addeddate` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `shoppingcartitems` (`id`,`userid`,`quantity`,`itemid`,`giftoption`,`addeddate`) VALUES (175,169,1,62,0,'2004-03-29 13:13:40'),(178,171,1,50,0,'2004-03-29 13:17:16'),(180,174,1,66,0,'2004-03-29 13:15:55'),(191,180,1,60,0,'2004-03-30 15:34:28'),(190,178,1,60,0,'2004-03-30 15:32:17'),(187,173,1,61,0,'2004-03-29 20:43:20'),(192,182,1,62,0,'2004-03-30 18:00:10'),(193,183,1,62,0,'2004-03-30 18:01:05'),(194,184,1,50,0,'2004-03-30 19:36:01'),(234,193,1,64,0,'2004-04-01 15:47:55'),(198,179,1,62,0,'2004-03-31 14:13:04'),(233,191,1,64,0,'2004-04-01 15:47:17');
CREATE TABLE `similarproducts` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `comment` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
CREATE TABLE `specifications` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `specifications` (`id`,`name`) VALUES (1,'Blade Steel'),(2,'Length Closed'),(3,'Blade Length'),(4,'Blade Shape'),(5,'Knife Type'),(6,'Weight'),(7,'Handle Material'),(8,'Carry System'),(9,'Length Overall'),(10,'Shape'),(11,'Edge Angle'),(12,'Grind'),(13,'Manufactured'),(14,'Material');
CREATE TABLE `themes` (
  `name` varchar(100) NOT NULL default '0',
  `color1` varchar(100) default '',
  `color2` varchar(100) default '',
  `color3` varchar(100) default '',
  `color4` varchar(100) default '',
  `color5` varchar(255) default '',
  `image1` varchar(255) default '',
  `image2` varchar(255) default '',
  `image3` varchar(255) default '',
  `image4` varchar(255) default '',
  `image5` varchar(255) default '',
  `heading1` varchar(255) NOT NULL default '',
  `heading2` varchar(255) default '',
  `heading3` varchar(255) default '',
  `heading4` varchar(255) default '',
  `heading5` varchar(255) default '',
  PRIMARY KEY  (`name`)
) TYPE=MyISAM;
INSERT INTO `themes` (`name`,`color1`,`color2`,`color3`,`color4`,`color5`,`image1`,`image2`,`image3`,`image4`,`image5`,`heading1`,`heading2`,`heading3`,`heading4`,`heading5`) VALUES ('corporate','#c2a877','#eeeecc',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL);
CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment,
  `email` varchar(128) default NULL,
  `pw` varchar(128) default NULL,
  `sourceurl` varchar(128) default NULL,
  `logintime` datetime NOT NULL default '0000-00-00 00:00:00',
  `creationdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `users` (`id`,`email`,`pw`,`sourceurl`,`logintime`,`creationdate`) VALUES (169,NULL,NULL,'http://localhost:8081/StoreFrontClient/','0000-00-00 00:00:00','2004-03-29 13:13:30'),(170,'tfearn@yahoo.com','fullers','','2004-04-01 17:14:06','2004-03-29 13:14:11'),(171,NULL,NULL,'http://localhost:8081/StoreFrontClient/youraccount.jsp','0000-00-00 00:00:00','2004-03-29 13:17:00'),(172,NULL,NULL,'http://localhost:8081/StoreFrontClient/youraccount.jsp','0000-00-00 00:00:00','2004-03-29 13:17:00'),(173,'cfearn@yahoo.com','fullers','','2004-03-29 20:43:29','2004-03-29 13:17:49'),(174,NULL,NULL,'http://localhost:8081/StoreFrontClient/index.jsp','0000-00-00 00:00:00','2004-03-29 13:15:42'),(175,'ginafearn@yahoo.com','fullers','','2004-03-29 13:16:52','2004-03-29 13:16:52'),(176,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-29 20:22:55'),(177,NULL,NULL,'http://67.80.184.158:8081/StoreFrontClient/','0000-00-00 00:00:00','2004-03-30 09:29:31'),(178,NULL,NULL,'http://67.80.184.158:8081/StoreFrontClient/','0000-00-00 00:00:00','2004-03-30 09:45:54'),(179,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 13:19:41');
INSERT INTO `users` (`id`,`email`,`pw`,`sourceurl`,`logintime`,`creationdate`) VALUES (180,'mhall@patiolife.com','markhall','','2004-03-30 15:34:28','2004-03-30 15:34:28'),(181,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 17:32:11'),(182,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 17:52:44'),(183,NULL,NULL,'','0000-00-00 00:00:00','2004-03-30 18:01:05'),(184,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 18:31:04'),(185,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 20:09:00'),(186,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-30 20:28:05'),(187,'amg@141.com','amg9246','','2004-04-01 17:23:36','2004-03-31 14:14:59'),(188,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-31 15:11:29'),(189,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-31 15:11:29'),(190,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-31 15:30:51'),(191,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-03-31 17:09:31'),(192,NULL,NULL,NULL,'0000-00-00 00:00:00','2004-04-01 15:25:00'),(193,'bcarson@yahoo.com','fullers','','2004-04-01 15:47:55','2004-04-01 15:47:55');
CREATE TABLE `wishlist` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `description` varchar(255) default NULL,
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
