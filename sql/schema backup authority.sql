/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `authority`;
USE `authority`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_name` varchar(15) NOT NULL default '',
  `user_pass` varchar(15) NOT NULL default '',
  `description` varchar(254) default '',
  PRIMARY KEY  (`user_name`)
) ENGINE=MyISAM;
INSERT INTO `users` VALUES  ('tomcat','tomcat',NULL);
INSERT INTO `users` VALUES  ('both','tomcat',NULL);
INSERT INTO `users` VALUES  ('admin','',NULL);
INSERT INTO `users` VALUES  ('sfadmin','storefront',NULL);
INSERT INTO `users` VALUES  ('sfmanager','storefront',NULL);
INSERT INTO `users` VALUES  ('manager','',NULL);
INSERT INTO `users` VALUES  ('sfshipping','storefront',NULL);

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_name` varchar(15) NOT NULL default '',
  `role_name` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`user_name`)
) ENGINE=MyISAM;
INSERT INTO `user_roles` VALUES  ('tomcat','tomcat');
INSERT INTO `user_roles` VALUES  ('admin','admin');
INSERT INTO `user_roles` VALUES  ('manager','manager');
INSERT INTO `user_roles` VALUES  ('sfmanager','sfmanager');
INSERT INTO `user_roles` VALUES  ('sfadmin','sfadmin');
INSERT INTO `user_roles` VALUES  ('sfshipping','sfshipping');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
