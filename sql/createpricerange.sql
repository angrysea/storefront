# Connection: Storefront
# Host: 10.1.1.50
# Saved: 2004-07-14 12:36:04
# 
# Host: 10.1.1.50
# Database: storefront
# Table: 'category'
# 
CREATE TABLE `pricerange` (
  `id` int(11) NOT NULL auto_increment,
  `groupid` int(11) default '1',
  `sortorder` int(11) default '0',
  `url` varchar(100) default '',
  `active` tinyint(1) default '1',
  `name` varchar(100) default '',
  `startprice` double default 0,
  `endprice` double default 0,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1; 

