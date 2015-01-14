drop TABLE `affiliate`;
CREATE TABLE `affiliate` (
  `id` int(11) NOT NULL default '0',
  `first` varchar(128) default NULL,
  `last` varchar(128) default NULL,
  `payeename` varchar(128) default NULL,
  `taxid` varchar(128) default NULL,
  `company` varchar(128) default NULL,
  `description` varchar(128) default NULL,
  `address1` varchar(128) default NULL,
  `address2` varchar(128) default NULL,
  `address3` varchar(128) default NULL,
  `city` varchar(128) default NULL,
  `state` varchar(128) default NULL,
  `zip` varchar(128) default NULL,
  `country` varchar(128) default NULL,
  `phone` varchar(128) default NULL,
  `customerservice` varchar(128) default NULL,
  `support` varchar(128) default NULL,
  `fax` varchar(128) default NULL,
  `email` varchar(128) default NULL,
  `websitename` varchar(128) default NULL,
  `affiliateurl` varchar(128) default NULL,
  `creationdate` varchar(128) default NULL,
  `pw` varchar(128) default NULL,
  `itemssold` int(11) default '0',
  `totalsales` double default '0',
  `totalcommission` double default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM; 

drop TABLE `affiliatecommission`;
CREATE TABLE `affiliatecommission` (
  `id` int(11) NOT NULL default '0',
  `affiliateid` int(11) default '0',
  `salesorderitem` int(11) default '0',
  `percent` double default '0',
  `commission` double default '0',
  `creationdat` datetime NOT NULL default '0000-00-00 00:00:00',
  `paiddate` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

drop TABLE `affiliateprogram`;
CREATE TABLE `affiliateprogram` (
  `id` int(11) NOT NULL default '0',
  `description` varchar(128) default NULL,
  `minpercent` double default '0',
  `maxpercent` double default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM; 

drop TABLE `affiliatecommissionlevel`;
CREATE TABLE `affiliatecommissionlevel` (
  `id` int(11) NOT NULL default '0',
  `programid` varchar(128) default NULL,
  `description` varchar(128) default NULL,
  `linktype` varchar(128) default NULL,
  `start` double default '0',
  `end` double default '0',
  `percent` double default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM; 

