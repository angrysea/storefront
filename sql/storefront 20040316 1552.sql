/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) NOT NULL default '0',
  `type` int(11) default NULL,
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(14) default NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
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
INSERT INTO `categoryitem` (`id`,`category`,`itemnumber`) VALUES (49,17,50),(110,1,63),(48,13,50),(82,13,50),(109,22,62),(108,18,62),(107,17,62),(106,2,62),(91,17,59),(90,8,59),(105,22,61),(104,18,61),(103,17,61),(102,2,61),(101,22,60),(100,18,60),(99,17,60),(98,1,60),(97,22,60),(96,18,60),(95,17,60),(94,1,60),(93,22,59),(92,18,59),(89,1,59),(88,22,59),(87,18,59),(86,17,59),(85,8,59),(84,1,59),(83,17,50),(111,17,63),(112,18,63),(113,21,63),(114,22,63),(115,1,63),(116,17,63),(117,18,63),(118,21,63),(119,22,63),(120,1,64),(121,17,64),(122,18,64),(123,21,64),(124,22,64),(125,1,63),(126,17,63),(127,18,63),(128,21,63),(129,22,63),(130,1,63),(131,17,63),(132,18,63),(133,21,63),(134,22,63),(135,1,65),(136,17,65),(137,18,65),(138,21,65),(139,22,65),(140,1,66),(141,17,66),(142,18,66),(143,21,66),(144,22,66);
CREATE TABLE `comments` (
  `id` int(11) NOT NULL auto_increment,
  `comment` varchar(255) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL auto_increment,
  `addressid` int(11) default NULL,
  `type` varchar(30) default NULL,
  `number` varchar(28) default NULL,
  `expdate` varchar(5) default NULL,
  `cardholder` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `addressid` (`addressid`)
) TYPE=MyISAM;
CREATE TABLE `customer` (
  `id` varchar(40) NOT NULL default '',
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
  `url` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
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
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (64,'1218','KB-1218','The most famous knife in the World gets updated with a serrated edge to better cut synthetic and looped materials.\r\n\r\nWeight: 0.68 lbs.\r\nLock Style: N/A  \r\nLength: Blade length 7\", Overall length 11 7/8\" \r\nGrind: Flat  \r\nShape: Clip Handle\r\nMaterial: Leather  \r\nStamp: USMC\r\nHRC: 56-58  \r\nEdge Angle: 20 Degrees \r\nButt Cap /Guard: Pwdrd Metal/1095 Carbon  \r\nSteel: 1095 Carbon \r\nCountry Mft\'d: Made in USA\r\n',NULL,'./images/KB-1218small.jpg','./images/KB-1218medium.jpg','./images/KB-1218large.jpg',0,NULL,1,NULL),(50,'815BK','BU-1679','Tough shears for home, shop or game. Integrated screwdriver and bottle opener--pry open lids & bottles, use notched jaws to open screw-top containers. Easy-clean, take-apart design. \r\n\r\n* Weight: 4 oz. (113 g.)\r\n* Handle Material: Thermoplastic\r\n* Size: 8 1/2\" (21.6 cm.)\r\n* Blade/Cutting Edge: Two-piece steel\r\n',NULL,'./images/BU-1679small.jpg','./images/BU-1679medium.jpg','./images/BU-1679large.jpg',0,NULL,1,NULL),(63,'1217','KB-1217','The most famous fixed blade knife in the World - \"the KA-BAR\" - was designed to serve our troops during World War II and is still doing its job, with honors, more than 50 years later.\r\n\r\n* Weight: 0.68 lbs. Lock Style: N/A  \r\n* Length: Blade length 7\", Overall length 11 7/8\" \r\n* Grind: Flat  \r\n* Shape: Clip Handle \r\n* Material: Leather  \r\n* Stamp: USMC HRC: 56-58  \r\n* Edge Angle: 20 Degrees \r\n* Butt Cap /Guard: Pwdrd Metal/1095 Carbon  \r\n* Steel: 1095 Carbon Country \r\n* Mft\'d: Made in USA  \r\n',NULL,'./images/KB-1217small.jpg','./images/KB-1217medium.jpg','./images/KB-1217large.jpg',0,NULL,1,NULL);
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (62,'112FG','BU-2539','Ranger with finger grooves to look and feel just right. Brass bolsters, liners and rivets. Classic wood handle. \r\n\r\n* Weight: 5.6 oz. (158 g.)\r\n* Handle Material: Natural woodgrain\r\n* Carry System: Black leather sheath\r\n* Blade Steel: 420HC\r\n* Length Closed: 4 1/4\" (10.8 cm.)\r\n* Blade Length: 3\" (7.6 cm.)\r\n* Blade Shape: Clip\r\n ',NULL,'./images/BU-2539small.jpg','./images/BU-2539medium.jpg','./images/BU-2539large.jpg',0,NULL,1,NULL),(61,'110FG','BU-2538','Famous Folding Hunter with finger grooves to look and feel just right. Brass bolsters, liners and rivets. Classic wood handle. \r\n\r\n* Weight: 7.2 oz. (204 g.)\r\n* Handle Material: Natural woodgrain\r\n* Carry System: Black leather sheath\r\n* Blade Steel: 420HC\r\n* Length Closed: 4 7/8\" (12.4 cm.)\r\n* Blade Length: 3 3/4\" (9.5 cm.)\r\n* Blade Shape: Clip\r\n ',NULL,'./images/BU-2538small.jpg','./images/BU-2538medium.jpg','./images/BU-2538large.jpg',0,NULL,1,NULL),(60,'105','BU-2535','Medium size fixed-blade hunting knife--great for general outdoor use. Polished aluminum butt and guard. \r\n\r\n* Weight: 4.5 oz. (127 g.)\r\n* Handle Material: Phenolic\r\n* Carry System: Black leather sheath\r\n* Blade Steel: 420HC\r\n* Length Overall: 9 1/8\" (23.2 cm.)\r\n* Blade Length: 5\" (12.7 cm.)\r\n* Blade Shape: Modified clip\r\n ',NULL,'./images/BU-2535small.jpg','./images/BU-2535medium.jpg','./images/BU-2535large.jpg',0,NULL,1,NULL);
INSERT INTO `details` (`itemnumber`,`manufactureritemnumber`,`distributoritemnumber`,`description`,`catalog`,`imageurlsmall`,`imageurlmedium`,`imageurllarge`,`salesrank`,`availability`,`shippingweight`,`url`) VALUES (59,'102BR','BU-2534','Smaller fixed-blade hunting knife is perfect for small game, as well as fishing or camping activities. Polished brass butt and guard. Cocobola handle. \r\n\r\n* Weight: 2.5 oz. (71 g.)\r\n* Handle Material: Cocobola\r\n* Carry System: Brown leather sheath\r\n* Blade Steel: 420HC\r\n* Length Overall: 7 3/4\" (19.7 cm.)\r\n* Blade Length: 4\" (10.2 cm.)\r\n* Blade Shape: Clip\r\n ',NULL,'./images/BU-2534small.jpg','./images/BU-2534medium.jpg','./images/BU-2534large.jpg',0,NULL,1,NULL),(65,'1211','KB-1211','A practical, all-purpose utility knife featuring ergonomically designed slip resistant handle. Serves every task perfectly. \r\n\r\n7\" black epoxy coated blade. 11-3/4\" overall. Plain Edge with a leather sheath.\r\n\r\n* Weight: 0.66 lbs.\r\n* Lock Style: N/A  \r\n* Length: Blade length 7\", Overall length 11 3/4\" \r\n* Grind: Flat  \r\n* Shape: Clip Handle\r\n* Material: Kraton G  \r\n* Stamp: USA\r\n* HRC: 56-58  \r\n* Edge Angle: 20 Degrees\r\n* Butt Cap /Guard: Pwdrd Metal/1095 Carbon  \r\n* Steel: 1095 Carbon\r\n* Country Mft\'d: Made in USA  \r\n',NULL,'./images/KB-1211small.jpg','./images/KB-1211medium.jpg','./images/KB-1211large.jpg',0,NULL,1,NULL),(66,'1212','KB-1212','A partially serrated edge makes cutting looped and synthetic material a breeze. Blade marked 1211. \r\n\r\n7\" Epoxy Coated blade, 11-3/4\" overall. Partially serrated blade, leather sheath.\r\n\r\nWeight: 0.66 lbs.\r\nLength: Blade length 7\", Overall length 11 3/4\" \r\nGrind: Flat  \r\nShape: Clip Handle\r\nMaterial: Kraton G  \r\nStamp: USA\r\nHRC: 56-58  \r\nEdge Angle: 20 Degrees\r\nButt Cap /Guard: Pwdrd Metal/1095 Carbon  \r\nSteel: 1095 Carbon Country\r\nMft\'d: Made in USA  \r\n',NULL,'./images/KB-1212small.jpg','./images/KB-1212medium.jpg','./images/KB-1212large.jpg',0,NULL,1,NULL);
CREATE TABLE `distributor` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `distributor` (`id`,`name`,`description`) VALUES (4,'National Knife','Wholesaler and distributor of pocket knives, tactical knives, automatic knives, swords, collectibles, and Zippo lighters.');
CREATE TABLE `featuredproduct` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `comments` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
CREATE TABLE `grouptype` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL default '',
  `description` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `grouptype` (`id`,`name`,`description`) VALUES (1,'Use',''),(2,'Type','');
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
INSERT INTO `item` (`id`,`isin`,`productname`,`manufacturer`,`distributor`,`quantity`,`status`,`listprice`,`ourprice`,`ourcost`) VALUES (64,'KB-1218','USMC Fighting Knife, Serrated Edge',10,'4',0,1,67.4,0,31.7),(65,'KB-1211','Ka-Bar Fighting Knife, Straight Edge',10,'4',0,1,67.4,0,33.7),(50,'BU-1679','Utility Shears',7,'4',0,1,36,0,18),(63,'KB-1217','USMC Fighting Knife, Straight Edge',10,'4',0,1,67.4,0,31.7),(62,'BU-2539','Ranger',7,'4',0,1,66,0,33),(61,'BU-2538','Folding Hunter',7,'4',0,1,69,0,34.5),(60,'BU-2535','Pathfinder',7,'4',0,1,61,0,30.5),(59,'BU-2534','Woodsman',7,'4',0,1,75,0,37.5),(66,'KB-1212','Ka-Bar Fighting Knife, Serrated Edge',10,'4',0,1,67.4,0,33.7);
CREATE TABLE `itemstatus` (
  `id` int(11) NOT NULL auto_increment,
  `status` varchar(100) default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `itemstatus` (`id`,`status`) VALUES (1,'Available'),(2,'Discontinued');
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default '0',
  `value` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;
INSERT INTO `keywords` (`id`,`itemnumber`,`value`) VALUES (1,55,'Buck Knives'),(2,55,'National Knife'),(3,55,'mb101'),(4,55,'mini buck'),(5,56,'Buck Knives'),(6,56,'National Knife'),(7,56,'mb101'),(8,56,'mini buck'),(9,57,'Buck Knives'),(10,57,'National Knife'),(11,57,'mb101'),(12,57,'mini buck'),(13,58,'Buck Knives'),(14,58,'National Knife'),(15,58,'mb101'),(16,58,'mini buck'),(17,58,'Folding Knives'),(18,58,'Pocket Knives'),(19,58,'Everyday'),(20,58,'Outdoor'),(21,61,'Buck Knives'),(22,61,'National Knife'),(23,61,'BU-2538'),(24,61,'Folding Hunter'),(25,61,'Folding Knives'),(26,61,'Hunting'),(27,61,'Outdoor'),(28,61,'Fishing'),(29,62,'Buck Knives'),(30,62,'National Knife'),(31,62,'BU-2539'),(32,62,'Ranger'),(33,62,'Folding Knives'),(34,62,'Hunting'),(35,62,'Outdoor'),(36,62,'Fishing'),(37,63,'Ka-Bar'),(38,63,'National Knife'),(39,63,'KB-1217'),(40,63,'USMC Fighting Knife'),(41,63,'Fixed blade knife\n'),(42,63,'Hunting'),(43,63,'Outdoor'),(44,63,'Camping'),(45,63,'Fishing'),(46,64,'Ka-Bar'),(47,64,'National Knife');
INSERT INTO `keywords` (`id`,`itemnumber`,`value`) VALUES (48,64,'KB-1218'),(49,64,'USMC Fighting Knife, Serrated Edge'),(50,64,'Fixed blade knife\n'),(51,64,'Hunting'),(52,64,'Outdoor'),(53,64,'Camping'),(54,64,'Fishing'),(55,65,'Ka-Bar'),(56,65,'National Knife'),(57,65,'KB-1211'),(58,65,'Ka-Bar Fighting Knife, Straight Edge'),(59,65,'Fixed blade knife\n'),(60,65,'Hunting'),(61,65,'Outdoor'),(62,65,'Camping'),(63,65,'Fishing'),(64,66,'Ka-Bar'),(65,66,'National Knife'),(66,66,'KB-1212'),(67,66,'Ka-Bar Fighting Knife, Serrated Edge'),(68,66,'Fixed blade knife\n'),(69,66,'Hunting'),(70,66,'Outdoor'),(71,66,'Camping'),(72,66,'Fishing');
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  PRIMARY KEY  (`id`),
  KEY `name` (`name`)
) TYPE=MyISAM;
INSERT INTO `manufacturer` (`id`,`name`,`description`) VALUES (10,'Ka-Bar','KA-BAR Knives, Inc., a subsidiary of Alcas Corporation, manufactures high quality military, hunting, sporting, and all-purpose utility knives. '),(11,'SOG','SOG originally stood for Studies and Observation Group, an elite joint services military group designed for covert operations in the Vietnam War. Sanctioned to develop and purchase their own equipment, SOG created a knife for use in one of the harshest environments in the world. It is in the spirit of this elite group that SOG Specialty Knives was founded. \r\n\r\nTotally committed to creating the world\'s finest specialized knives and tools, SOG became the first knife manufacturer to expand its line to include a broad scope of fixed blades, folding knives and multipurpose tools. Each product is created by Cofounder and Chief Engineer, Spencer Frazer. His patented inventions and unique, futuristic style have earned SOG many awards and Frazer the recognition as an industry innovator and premier designer. \r\n\r\nToday, SOG products are distributed around the world and renowned for their uncompromising style and performance. '),(7,'Buck Knives','Buck Knives-since 1902 famous manufacturer of pocket, folding & fixed blade knives & multi-use equipment for sports, work, utility and outdoor recreation-hunting, fishing, camping, hiking, backpacking, climbing, survival/tactical, diving/boating. Also custom & collector knives. ');
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
  `customerid` varchar(40) default NULL,
  `itemNumber` int(11) default NULL,
  `viewedtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`),
  KEY `itemNumber` (`itemNumber`)
) TYPE=MyISAM;
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
  `totalcost` double default NULL,
  `status` varchar(12) default NULL,
  `shipping` varchar(10) default NULL,
  `shippingmethod` varchar(10) default NULL,
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
CREATE TABLE `salesorderitem` (
  `id` int(11) NOT NULL auto_increment,
  `salesorderid` int(11) default NULL,
  `itemnumber` int(11) default NULL,
  `quantity` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `salesorderid` (`salesorderid`)
) TYPE=MyISAM;
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
CREATE TABLE `shoppingcart` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `creationtime` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) TYPE=MyISAM;
CREATE TABLE `shoppingcartitems` (
  `id` int(11) NOT NULL auto_increment,
  `cartid` int(11) default NULL,
  `quanity` int(11) default NULL,
  `itemid` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  `addeddate` timestamp(14) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `cartid` (`cartid`)
) TYPE=MyISAM;
CREATE TABLE `similarproducts` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `comment` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) TYPE=MyISAM;
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
