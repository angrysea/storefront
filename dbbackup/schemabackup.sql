/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront_instance2`;
USE `storefront_instance2`;

DROP TABLE IF EXISTS `sortfields`;
CREATE TABLE `sortfields` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(100) default '',
  `description` varchar(100) default '',
  `fieldname` varchar(100) default '',
  `fieldtype` varchar(100) default '',
  `direction` varchar(10) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
INSERT INTO `sortfields` (`id`,`type`,`description`,`fieldname`,`fieldtype`,`direction`) VALUES (30,'search','Price: highest first','a.ourprice','string','desc'),(20,'search','Price: lowest first','a.ourprice','string','asc'),(40,'search','Most Popular','views','string','asc'),(15,'search','Manufacturer Name','a.manufacturer','string','asc'),(1,'search','Product Name','a.productname','string','asc');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
