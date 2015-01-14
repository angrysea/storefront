# Connection: idata
# Host: idata-appserver
# Saved: 2004-04-29 10:47:21
# 
USE `authority`;
CREATE TABLE `users` (
  `user_name` varchar(15) NOT NULL default '',
  `user_pass` varchar(15) NOT NULL default '',
  `description` varchar(254) default '',
  PRIMARY KEY  (`user_name`)
) TYPE=MyISAM;
CREATE TABLE `user_roles` (
  `user_name` varchar(15) NOT NULL default '',
  `role_name` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`user_name`)
) TYPE=MyISAM;
