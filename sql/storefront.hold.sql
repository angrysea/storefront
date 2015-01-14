# Connection: idata
# Host: idata-appserver
# Saved: 2004-03-03 11:10:42
# 
DROP TABLE Customer;
DROP TABLE CreditCard;
DROP TABLE Address;
DROP TABLE SalesOrder;
DROP TABLE SalesOrderItem;
DROP TABLE Invoice;
DROP TABLE Invoiceitem;
DROP TABLE Package;
DROP TABLE PackageItem;
DROP TABLE Item;
DROP TABLE Details;
DROP TABLE SimilarProducts;
DROP TABLE ShoppingCart;
DROP TABLE ShoppingCartItems;
DROP TABLE FeaturedProduct;
DROP TABLE Variation;
DROP TABLE Reviews;
DROP TABLE CustomerReview;
DROP TABLE RecentlyViewed;
DROP TABLE SearchResult;
DROP TABLE SearchResultItem;

CREATE TABLE Customer (
	id varchar(40),
	first varchar(40),
	last varchar(40),
	mi varchar(1),
	salutation varchar(6),
	suffix varchar(8),
	fullname varchar(128),
	nickname varchar(128),
	home varchar(14),
	mobil varchar(14),
	work varchar(14),
	fax varchar(14),
	email1 varchar(128),
	email2 varchar(128),
	email3 varchar(128),
             birthdaymonth varchar(2),
             birthdayyear varchar(2),
	url varchar(128),
        PRIMARY KEY(id)
);

CREATE TABLE CreditCard (
	id integer,
	addressid integer,
	type varchar(30),
	number varchar(28),
	expdate varchar(5),
	cardholder varchar(128),
	PRIMARY KEY (id),
       	KEY (addressid),
);

CREATE TABLE Address (
	id integer,
             customerid varchar(40),
	type integer,
	address1 varchar(40),
	address2 varchar(40),
	address3 varchar(40),
	city varchar(40),
	state varchar(2),
	zip varchar(10),
	country varchar(40),
	phone varchar(14),
	PRIMARY KEY (id),
	KEY (customerid)
);

CREATE TABLE SalesOrder (
	id integer,
             customerid  varchar(40),
	totalcost double,
	status varchar(12),
             shipping varchar(10),
             shippingmethod varchar(10),
	creationtime TIMESTAMP,
	PRIMARY KEY (id),
	KEY (customerid)
);

CREATE TABLE SalesOrderItem (
	id integer,
             salesorderid integer,
	itemnumber integer,
      	quantity integer,
             giftoption integer,
	PRIMARY KEY (id),
	KEY (salesorderid)
);

CREATE TABLE Invoice (
	id integer,
      	customerid  varchar(40),
      	salesorderid integer,
      	taxes double,
      	shippingcost double,
      	totalcost double,
      	paymentmethod double,
      	status varchar(40),
      	creationtime TIMESTAMP,
	PRIMARY KEY (id),
	KEY (customerid),
             KEY (salesorderid)
);

CREATE TABLE Invoiceitem (
	id integer,
      	invoiceid integer,
	unitprice double,
	totalprice double,
	itemnumber varchar(12),
	exchangeid varchar(12),
      	quantity integer,
             giftoption integer,
	PRIMARY KEY (id),
	KEY (invoiceid)
);

CREATE TABLE Package (
	id integer,
      	invoiceitemid integer,
	trackingnumber varchar(28),
             carriername varchar(40),
	PRIMARY KEY (id),
             KEY (invoiceitemid)
);

CREATE TABLE PackageItem (
	id integer,
      	packageid integer,
      	invoiceitemid integer,
	PRIMARY KEY(id),
             KEY(packageid),
             KEY(invoiceitemid)
);

CREATE TABLE Item (
	id integer,
	productname varchar(128),
	isin varchar(12),
	quantity integer,
	listprice double,
	ourprice double,
	PRIMARY KEY (id)
);

CREATE TABLE Details (
	id integer,
	itemnumber integer,
	manufacturer varchar(128),
	distributor varchar(128),
	imageurlsmall varchar(128),
	imageurlmedium varchar(128),
	imageurllarge varchar(128),
	merchantid varchar(12),
	minprice double,
	maxprice double,
	minsaleprice double,
	maxsaleprice double,
	multimerchant varchar(128),
	merchantsku varchar(128),
	salesrank integer,
	availability varchar(128),
	productdescription varchar(255),
	status varchar(28),
	url varchar(128),
             PRIMARY KEY (id),
             KEY (itemnumber)
);

CREATE TABLE SimilarProducts (
	id integer,
	itemnumber integer,
	PRIMARY KEY (id),
	KEY (itemnumber)
);

CREATE TABLE ShoppingCart (
	id integer,
	customerid  varchar(40),
	creationtime TIMESTAMP,
	PRIMARY KEY (id),
	KEY (customerid)
);

CREATE TABLE ShoppingCartItems (
	id integer,
	cartid integer,
             quanity integer,
	itemid integer,
             giftoption integer,
	addeddate TIMESTAMP,
	PRIMARY KEY (id),
	KEY (cartid)
);

CREATE TABLE WishList ( 
	id integer,
	customerid  varchar(40),
             description varchar(255),
	creationtime TIMESTAMP,
	PRIMARY KEY (id),
	KEY (customerid)
);

CREATE TABLE WishListItems (
	id integer,
	wishlistid integer,
	itemid integer,
             quanitydesired integer,
             comment varchar(255),
             priority integer,
             giftoption integer,
	addeddate TIMESTAMP,
	PRIMARY KEY (id),
	KEY (cartid)
);

CREATE TABLE FeaturedProduct (
	id integer,
	itemnumber integer,
	comments varchar(255),
	PRIMARY KEY (id),
      	KEY (itemnumber)
);

CREATE TABLE Variation (
	id integer,
	itemnumber integer,
	clothingsize varchar(20),
	clothingcolor varchar(20),
	price double,
	saleprice double,
	shipdate timestamp,
	availability varchar(128),
	multimerchant varchar(128),
	merchantsku varchar(128),
	PRIMARY KEY (id),
	KEY (itemnumber)
);

CREATE TABLE Reviews (
	id integer,
	itemnumber varchar(12),
	avgCustomerrating integer,
	totalcustomerreviews integer,
	PRIMARY KEY (id),
	KEY (itemnumber)
);

CREATE TABLE CustomerReview (
	id integer,
	reviewid integer,
	rating varchar(128),
	reviewdate TIMESTAMP,
	summary varchar(128),
	comment varchar(128),
	PRIMARY KEY (id),
	KEY (reviewid)
);

CREATE TABLE RecentlyViewed (
	id integer,
        customerid  varchar(40),
	itemNumber integer,
	viewedtime TIMESTAMP,
	PRIMARY KEY (id),
	KEY (customerid),
	KEY (itemNumber)
);

CREATE TABLE SearchResult (
	id integer,
	searchtime TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE TABLE SearchResultItem (
	id integer,
	searchid integer,
	itemid integer,
	PRIMARY KEY (id),
	KEY (searchid),
	KEY (itemid)
);

