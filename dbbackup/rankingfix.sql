/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront`;
USE `storefront`;

DROP TABLE IF EXISTS `itemranking`;
CREATE TABLE `itemranking` (
  `itemnumber` int(11) NOT NULL default '0',
  `views` int(11) default '0',
  `sold` int(11) default '0',
  PRIMARY KEY  (`itemnumber`)
) TYPE=MyISAM;
INSERT INTO `itemranking` (`itemnumber`,`views`,`sold`) VALUES (74,3,0),(73,1,0),(71,1,0),(70,7,0),(69,19,3),(75,3,0),(76,6,1),(77,9,1),(78,5,0),(79,3,0),(80,1,0),(81,0,0),(82,27,3),(83,5,0),(84,2,0),(85,0,0),(86,3,1),(87,0,0),(88,1,0),(89,1,0),(90,0,0),(91,0,0),(92,0,0),(93,1,0),(94,19,4),(95,4,0),(96,3,0),(97,0,0),(98,2,0),(99,2,0),(100,0,0),(101,0,0),(102,0,0),(103,6,0),(104,2,0),(105,3,1),(106,0,0),(107,1,0),(108,2,0),(109,1,0),(110,1,0),(111,1,0),(112,0,0),(113,1,0),(114,5,0),(115,1,0),(116,1,0),(117,0,0),(118,21,5),(119,5,1),(120,0,0),(121,3,0),(122,1,0),(123,0,0),(124,0,0),(125,0,0),(126,0,0),(127,0,0),(128,0,0),(129,3,0),(130,2,0),(131,1,0),(132,0,0),(133,0,0),(134,1,0),(135,0,0),(136,0,0),(137,1,0),(138,0,0),(139,0,0),(140,0,0),(141,2,0),(142,0,0),(143,0,0),(144,0,0),(145,0,0),(146,0,0),(147,0,0),(148,1,1),(149,0,0),(150,0,0),(151,0,0),(152,1,0),(153,3,0),(154,1,0);
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
