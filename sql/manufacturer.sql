/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `longname` varchar(100) default '',
  `description` varchar(100) default '',
  `shortdescription` longtext,
  PRIMARY KEY  (`id`),
  KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
INSERT INTO `manufacturer` (`id`,`name`,`longname`,`description`,`shortdescription`) VALUES (10,'Ka-Bar','Ka-Bar','KA-BAR Knives, Inc., a subsidiary of Alcas Corporation, manufactures high quality military, hunting,',NULL),(11,'SOG','SOG','SOG originally stood for Studies and Observation Group, an elite joint services military group desig',NULL),(7,'Buck','Buck','Buck Knives-since 1902 famous manufacturer of pocket, folding & fixed blade knives & multi-use equip',NULL),(12,'Gerber','Gerber','In 1939 Joseph Gerber hired a local knife maker to build 25 sets of kitchen cutlery to be given as C',NULL),(13,'Camillus','Camillus','Camillus Cutlery Company is one of the oldest knife manufacturers in the United States. Ever since t',NULL),(14,'Becker','Becker','The Becker Knife and Tool Company has been at the forefront of hard use survival knives and tools si',NULL),(15,'Western','Western','In 1991, Camillus Cutlery grew with the acquisition of the Western® Cutlery Company. Originally loc',NULL),(16,'Cold Steel','Cold Steel','Cold Steel, based in Ventura, CA, is a premier manufacturer of fixed blade hunting, tactical, japane',NULL);
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
