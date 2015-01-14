/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;
CREATE TABLE `shippingmethods` (
  `id` int(11) NOT NULL auto_increment,
  `carrier` varchar(100) default '',
  `code` varchar(100) default '',
  `country` varchar(100) default '',
  `fixedprice` double default '0',
  `description` varchar(100) default '',
  `notes` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
INSERT INTO `shippingmethods` (`id`,`carrier`,`code`,`country`,`fixedprice`,`description`,`notes`) VALUES (2,'UPS','12','US',0,'UPS 3 Day Select','3 business days in the 48 contiguous states.\n'),(3,'UPS','02','US',0,'UPS 2nd Day Air','2nd business day delivery to all 50 states and Puerto Rico.\n'),(4,'UPS','01','US',0,'UPS Next Day Air','Guaranteed delivery by the end of the next business day.\n'),(5,'UPS','11','CA',0,'UPS Canada Standard','Delivery to all 10 Canadian provinces.\n'),(6,'UPS','07','ALL',0,'UPS Worldwide Express','Next day Canada, 2nd day delivery to other worldwide destinations.\n'),(1,'UPS','03','US',0,'UPS Ground','Usually delivered in 4 to 7 business days.'),(7,'USPS','Global Express Mail (EMS)','ALL',0,'USPS Global Express Mail','Usually delivered in 3 to 5 business days.'),(8,'USPS','Global Express Guaranteed Document Service','ALL',0,'USPS Global Express Guaranteed','Delivered in  2 to 3 business days.');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
