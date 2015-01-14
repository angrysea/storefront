/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=NO_AUTO_VALUE_ON_ZERO */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `storefront_instance3`;
USE `storefront_instance3`;

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` int(11) default '0',
  `type` varchar(64) default NULL,
  `first` varchar(100) default '',
  `last` varchar(100) default '',
  `mi` varchar(100) default '',
  `salutation` varchar(100) default '',
  `suffix` varchar(100) default '',
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(100) default '',
  `company` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `carrier`;
CREATE TABLE `carrier` (
  `code` varchar(100) NOT NULL default '',
  `license` varchar(100) default '',
  `user` varchar(100) default '',
  `password` varchar(100) default '',
  `pickuptype` varchar(100) default '',
  `version` varchar(100) default '',
  `url` varchar(100) default ''
) ENGINE=MyISAM;

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
  `basesecureurl` varchar(100) default '',
  `url` varchar(255) default '',
  `theme` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL auto_increment,
  `groupid` int(11) NOT NULL default '1',
  `name` varchar(100) default '',
  `description` longtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `categoryitem`;
CREATE TABLE `categoryitem` (
  `id` int(11) NOT NULL auto_increment,
  `category` int(11) default '0',
  `itemnumber` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `cctransactions`;
CREATE TABLE `cctransactions` (
  `id` int(11) NOT NULL auto_increment,
  `salesorderid` int(11) default '0',
  `result` varchar(12) default '',
  `respmsg` varchar(255) default '',
  `authcode` varchar(100) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL auto_increment,
  `comment` varchar(255) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL auto_increment,
  `company` varchar(128) default NULL,
  `description` longtext,
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
  `defaultshipping` int(11) default '1',
  `baseurl` varchar(255) default '',
  `basesecureurl` varchar(255) default '',
  `url` varchar(100) default '',
  `pw` varchar(100) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `countrycodes`;
CREATE TABLE `countrycodes` (
  `code` char(3) NOT NULL default '',
  `name` varchar(100) default '',
  PRIMARY KEY  (`code`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(100) default '',
  `itemid` int(11) default '0',
  `description` longtext,
  `quantitylimit` int(11) default '0',
  `discount` double default '0',
  `discounttype` int(11) default '0',
  `redemptiondate` datetime default '0000-00-00 00:00:00',
  `experationdate` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `creditcard`;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL default '0',
  `type` varchar(30) default NULL,
  `number` varchar(28) default NULL,
  `expmonth` char(2) default NULL,
  `expyear` varchar(4) default '',
  `cardholder` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL default '0',
  `first` varchar(40) default NULL,
  `last` varchar(40) default NULL,
  `mi` char(1) default NULL,
  `salutation` varchar(6) default NULL,
  `suffix` varchar(8) default NULL,
  `fullname` varchar(128) default NULL,
  `nickname` varchar(128) default NULL,
  `home` varchar(14) default NULL,
  `mobil` varchar(14) default NULL,
  `work` varchar(14) default NULL,
  `fax` varchar(14) default NULL,
  `email1` varchar(128) default NULL,
  `email2` varchar(128) default NULL,
  `email3` varchar(128) default NULL,
  `birthdaymonth` char(2) default NULL,
  `birthdayyear` char(2) default NULL,
  `ccflag` tinyint(4) default '0',
  `url` varchar(128) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `customerreview`;
CREATE TABLE `customerreview` (
  `id` int(11) NOT NULL auto_increment,
  `reviewid` int(11) default NULL,
  `rating` varchar(128) default NULL,
  `reviewdate` timestamp NOT NULL,
  `summary` varchar(128) default NULL,
  `comment` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  KEY `reviewid` (`reviewid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `details`;
CREATE TABLE `details` (
  `itemnumber` int(11) NOT NULL default '0',
  `manufactureritemnumber` varchar(100) default '',
  `distributoritemnumber` varchar(100) default '',
  `description` longtext,
  `imageurlsmall` varchar(128) default NULL,
  `imageurlmedium` varchar(128) default NULL,
  `imageurllarge` varchar(128) default NULL,
  `salesrank` int(11) default NULL,
  `availability` varchar(128) default NULL,
  `shippingweight` double default '0',
  `height` double default '0',
  `length` double default '0',
  `width` double default '0',
  `handlingcharges` double default '0',
  `hasvariations` tinyint(1) default '0',
  `url` varchar(128) default NULL,
  PRIMARY KEY  (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `distributor`;
CREATE TABLE `distributor` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  `dropshipfee` double default '0',
  `email` varchar(100) default '',
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(14) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `featuredproducts`;
CREATE TABLE `featuredproducts` (
  `id` int(11) NOT NULL auto_increment,
  `sortorder` int(11) default '0',
  `itemnumber` int(11) default NULL,
  `type` varchar(25) default '',
  `heading` varchar(255) default '',
  `comments` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `grouptype`;
CREATE TABLE `grouptype` (
  `id` int(11) NOT NULL auto_increment,
  `catalog` int(11) default '0',
  `name` varchar(100) default '',
  `description` longtext,
  `image` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `billingaddress` int(11) default '0',
  `taxes` double default NULL,
  `taxesdescription` varchar(100) NOT NULL default '',
  `shippingcost` double default NULL,
  `totalcost` double default NULL,
  `paymentmethod` int(11) default NULL,
  `authorizationcode` varchar(100) default '',
  `status` varchar(40) default NULL,
  `creationtime` timestamp NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `invoiceitem`;
CREATE TABLE `invoiceitem` (
  `id` int(11) NOT NULL auto_increment,
  `invoiceid` int(11) unsigned zerofill NOT NULL default '00000000001',
  `salesorderitemid` int(11) default '0',
  `unitprice` double default NULL,
  `totalprice` double default NULL,
  `itemnumber` varchar(12) default NULL,
  `exchangeid` varchar(12) default NULL,
  `quantity` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `invoiceid` (`invoiceid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL auto_increment,
  `catalog` int(11) default '0',
  `isin` varchar(128) default NULL,
  `productname` varchar(100) default NULL,
  `manufacturer` int(11) default '0',
  `distributor` varchar(100) default '',
  `quantity` int(11) default '-1',
  `allocated` int(11) default '0',
  `sold` int(11) default '0',
  `backordered` int(11) default '0',
  `minimumonhand` int(11) default '0',
  `reorderquantity` int(11) default '0',
  `quantityonorder` int(11) default '0',
  `status` int(11) default '0',
  `listprice` double default NULL,
  `ourprice` double default '0',
  `ourcost` double default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `itemranking`;
CREATE TABLE `itemranking` (
  `itemnumber` int(11) NOT NULL default '0',
  `views` int(11) default '0',
  `sold` int(11) default '0',
  PRIMARY KEY  (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `itemspecifications`;
CREATE TABLE `itemspecifications` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default '0',
  `specid` int(11) default '0',
  `value` double(11,0) default '0',
  `description` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `itemstatus`;
CREATE TABLE `itemstatus` (
  `id` int(11) NOT NULL auto_increment,
  `status` varchar(100) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `keywords`;
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default '0',
  `value` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(100) default NULL,
  `url` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `mailconfig`;
CREATE TABLE `mailconfig` (
  `id` varchar(100) NOT NULL default '',
  `mailhost` varchar(255) NOT NULL default '',
  `mailfromname` varchar(100) default '',
  `mailfromaddress` varchar(255) default '',
  `mailauthuser` varchar(100) default '',
  `mailauthpassword` varchar(100) default '',
  `mailreplyto` varchar(255) default '',
  `url` varchar(100) default '',
  `mailintroduction` longtext,
  `mailbody` longtext,
  `mailsignature` longtext,
  `mailsubject` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `description` longtext,
  PRIMARY KEY  (`id`),
  KEY `name` (`name`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `packingslip`;
CREATE TABLE `packingslip` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` int(11) default '0',
  `invoiceid` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `trackingnumber` varchar(28) default NULL,
  `carriername` varchar(100) default '',
  `shippingmethodid` int(11) default '0',
  `creationdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `packingslipitem`;
CREATE TABLE `packingslipitem` (
  `id` int(11) NOT NULL auto_increment,
  `packingslipid` int(11) default '0',
  `qtyshipped` int(11) default NULL,
  `salesorderitemid` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `payflowpro`;
CREATE TABLE `payflowpro` (
  `hostAddress` varchar(64) default '',
  `hostPort` int(11) default '0',
  `timeout` int(11) default '0',
  `partnerID` varchar(100) default '',
  `vendor` varchar(100) default '',
  `logon` varchar(100) default '',
  `password` varchar(100) default '',
  `proxyAddress` varchar(64) default '',
  `proxyPort` int(11) default '0',
  `proxyLogon` varchar(100) default '',
  `proxyPassword` varchar(100) default ''
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `recentlyviewed`;
CREATE TABLE `recentlyviewed` (
  `id` int(11) NOT NULL auto_increment,
  `userid` varchar(40) default NULL,
  `itemnumber` int(11) default NULL,
  `viewedtime` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `recurringorder`;
CREATE TABLE `recurringorder` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `billingaddress` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `description` longtext,
  `optimizeshipping` tinyint(1) default '0',
  `shippingmethodid` int(11) default '0',
  `frequency` int(11) default '0',
  `startdate` timestamp NOT NULL,
  `enddate` timestamp NOT NULL,
  `creationtime` timestamp NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `recurringorderitem`;
CREATE TABLE `recurringorderitem` (
  `id` int(11) NOT NULL auto_increment,
  `recurringorderrid` int(11) default NULL,
  `trxtype` char(2) default '',
  `itemnumber` int(11) default NULL,
  `quantity` int(11) default '0',
  `unitPrice` double default '0',
  `status` varchar(100) default '',
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `salesorderid` (`recurringorderrid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `relatedproducts`;
CREATE TABLE `relatedproducts` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `comment` varchar(255) default NULL,
  `type` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` varchar(12) default NULL,
  `avgCustomerrating` int(11) default NULL,
  `totalcustomerreviews` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `salesorder`;
CREATE TABLE `salesorder` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `billingaddress` int(11) default '0',
  `shippingaddress` int(11) default '0',
  `description` longtext,
  `couponcode` varchar(100) default '',
  `discount` double default '0',
  `discountdesc` varchar(254) default '',
  `totalcost` double default NULL,
  `taxes` double default '0',
  `taxesdesc` varchar(100) default '',
  `status` varchar(25) default NULL,
  `optimizeshipping` tinyint(1) default '0',
  `shipping` double default NULL,
  `shippingmethodid` int(11) default '0',
  `shippingweight` double default '0',
  `total` double default '0',
  `authorizationcode` varchar(100) default '',
  `mailstatus` varchar(100) default '0',
  `creationtime` timestamp NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `salesorderitem`;
CREATE TABLE `salesorderitem` (
  `id` int(11) NOT NULL auto_increment,
  `salesorderid` int(11) default NULL,
  `trxtype` char(2) default '',
  `itemnumber` int(11) default NULL,
  `quantity` int(11) default '0',
  `quantitytoship` int(11) default '0',
  `shipped` int(11) default '0',
  `unitPrice` double default '0',
  `status` varchar(100) default '',
  `exchangeid` varchar(100) default '',
  `giftoption` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `salesorderid` (`salesorderid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `salestax`;
CREATE TABLE `salestax` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(100) default '',
  `sellfrom` varchar(100) default '',
  `sellto` varchar(100) default '',
  `description` varchar(100) default '',
  `taxrate` double default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `searchresults`;
CREATE TABLE `searchresults` (
  `id` int(11) NOT NULL auto_increment,
  `userid` int(11) default '0',
  `search` longtext,
  `itemsfound` varchar(100) default '',
  `searchtime` timestamp NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `searchresultsitem`;
CREATE TABLE `searchresultsitem` (
  `id` int(11) NOT NULL auto_increment,
  `searchid` int(11) default NULL,
  `idx` int(11) default '0',
  `itemid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemid` (`itemid`),
  KEY `searchid` (`searchid`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `shippingmethods`;
CREATE TABLE `shippingmethods` (
  `id` int(11) NOT NULL auto_increment,
  `carrier` varchar(100) default '',
  `code` varchar(100) default '',
  `country` varchar(100) default '',
  `fixedprice` double default '0',
  `description` varchar(100) default '',
  `notes` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `shoppingcartitems`;
CREATE TABLE `shoppingcartitems` (
  `id` int(11) NOT NULL auto_increment,
  `userid` int(11) default NULL,
  `quantity` int(11) default NULL,
  `itemid` int(11) default NULL,
  `giftoption` tinyint(1) default '0',
  `addeddate` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `similarproducts`;
CREATE TABLE `similarproducts` (
  `id` int(11) NOT NULL auto_increment,
  `itemnumber` int(11) default NULL,
  `similaritem` int(11) default '0',
  `comment` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `itemnumber` (`itemnumber`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `skipurl`;
CREATE TABLE `skipurl` (
  `url` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`url`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `sortfields`;
CREATE TABLE `sortfields` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(100) default '',
  `description` varchar(100) default '',
  `fieldname` varchar(100) default '',
  `fieldtype` varchar(100) default '',
  `direction` varchar(10) default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `specifications`;
CREATE TABLE `specifications` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default '',
  `type` varchar(100) default '0',
  `selecttype` varchar(100) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `statecodes`;
CREATE TABLE `statecodes` (
  `code` char(2) NOT NULL default '',
  `name` varchar(100) default '',
  PRIMARY KEY  (`code`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(128) default NULL,
  `description` longtext,
  `address1` varchar(40) default NULL,
  `address2` varchar(40) default NULL,
  `address3` varchar(40) default NULL,
  `city` varchar(40) default NULL,
  `state` char(2) default NULL,
  `zip` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `phone` varchar(100) default '',
  `fax` varchar(100) default '',
  `email` varchar(100) default '',
  `startzip` varchar(10) default NULL,
  `endzip` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `themes`;
CREATE TABLE `themes` (
  `name` varchar(100) NOT NULL default '0',
  `color1` varchar(100) default '',
  `color2` varchar(100) default '',
  `color3` varchar(100) default '',
  `color4` varchar(100) default '',
  `color5` varchar(255) default '',
  `image1` varchar(255) default '',
  `image2` varchar(255) default '',
  `image3` varchar(255) default '',
  `image4` varchar(255) default '',
  `image5` varchar(255) default '',
  `heading1` varchar(255) NOT NULL default '',
  `heading2` varchar(255) default '',
  `heading3` varchar(255) default '',
  `heading4` varchar(255) default '',
  `heading5` varchar(100) default '',
  `titleinfo` longtext,
  `metacontenttype` longtext,
  `metakeywords` longtext,
  `metadescription` longtext,
  `imagebaseurl` varchar(255) default '',
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment,
  `email` varchar(128) default NULL,
  `pw` varchar(128) default NULL,
  `sourceurl` varchar(128) default NULL,
  `logintime` datetime NOT NULL default '0000-00-00 00:00:00',
  `creationdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `variations`;
CREATE TABLE `variations` (
  `id` int(11) NOT NULL auto_increment,
  `quantity` int(11) default '-1',
  `clothingsize` varchar(100) default NULL,
  `clothingcolor` varchar(100) default NULL,
  `fabric` varchar(100) default '',
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
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `visitedurl`;
CREATE TABLE `visitedurl` (
  `url` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`url`)
) ENGINE=MyISAM;

DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE `wishlist` (
  `id` int(11) NOT NULL auto_increment,
  `customerid` varchar(40) default NULL,
  `description` varchar(255) default NULL,
  `creationtime` timestamp NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `customerid` (`customerid`)
) ENGINE=MyISAM;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
