/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront_instance3`;
USE `storefront_instance3`;

DROP TABLE IF EXISTS `catalog`;
CREATE TABLE `catalog` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` varchar(254) default '',
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
  `baseurl` varchar(255) default '',
  `basesecureurl` varchar(255) NOT NULL default '',
  `theme` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
