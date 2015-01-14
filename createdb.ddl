create database diecast;
use diecast;

create table product (
	id 		INTEGER NOT NULL AUTO_INCREMENT,
	mfgrid 		VARCHAR(64),
	mfgr 		VARCHAR(32),
	type		VARCHAR(32),
	war		VARCHAR(32),
	country		VARCHAR(32),
	scale		VARCHAR(8),
	productline	VARCHAR(32),
	listprice	DOUBLE,
	sellprice	DOUBLE,
	title		VARCHAR(128),
	subtitle	VARCHAR(255),
	description	TEXT,
	qtyinstock	INTEGER,
	instockdate	DATE,
	length		double,
	width		double,
	height		double,
	weight		double,
	picturesmall	VARCHAR(128),
	picturelarge	VARCHAR(128),
	PRIMARY KEY 	(id),
	KEY		(mfgr),
	KEY		(type),
	KEY		(war),
	KEY		(country),
	KEY		(scale)
);

create table customer (
	email		VARCHAR(128),
	firstname	VARCHAR(32),
	lastname	VARCHAR(32),
	password	VARCHAR(32),
	PRIMARY KEY 	(email),
	KEY		(lastname)
);

create table cart (
	time_stamp	TIMESTAMP,
	email		VARCHAR(128),
	productid	INTEGER,
	qty		INTEGER,
	PRIMARY KEY 	(email)
);

create table wishlist (
	time_stamp	TIMESTAMP,
	email		VARCHAR(128),
	productid	INTEGER,
	qty		INTEGER,
	PRIMARY KEY 	(email)
);

create table invoice (
	time_stamp	TIMESTAMP,
	id		INTEGER,
	email		VARCHAR(128),
	billaddr1	VARCHAR(128),
	billaddr2	VARCHAR(128),
	billcity	VARCHAR(32),
	billstate	VARCHAR(32),
	billzip		VARCHAR(24),
	billcountry	VARCHAR(24),
	billphone	VARCHAR(24),
	shipaddr1	VARCHAR(128),
	shipaddr2	VARCHAR(128),
	shipcity	VARCHAR(32),
	shipstate	VARCHAR(32),
	shipzip		VARCHAR(24),
	shipcountry	VARCHAR(24),
	shipphone	VARCHAR(24),
	taxes		DOUBLE,
	shippingcost	DOUBLE,
	totalcost	DOUBLE,
	shipmethod	VARCHAR(16),
	paymentmethod	VARCHAR(16),
	creditcard	VARCHAR(32),
	trackingnumber	VARCHAR(64),
	status		VARCHAR(16),
	PRIMARY KEY 	(id),
	KEY		(email)
);

create table invoiceitems (
	time_stamp	TIMESTAMP,
	invoiceid	INTEGER,
	qty		INTEGER,
	productid	INTEGER,
	PRIMARY KEY	(invoiceid)
);

create table counters (
	dummyid		INTEGER NOT NULL AUTO_INCREMENT,
	invoiceid	INTEGER,
	PRIMARY KEY	(dummyid)
);