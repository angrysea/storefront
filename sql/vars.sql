# Connection: idata
# Host: idata-appserver
# Saved: 2004-04-15 11:00:38
# 
USE `storefront`;
CREATE TABLE `variations` (
  `id` int(11) NOT NULL auto_increment,
  `clothingsize` varchar(100) default NULL,
  `clothingcolor` varchar(100) default NULL,
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
