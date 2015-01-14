# Connection: Storefront
# Host: 10.1.1.50
# Saved: 2004-06-30 11:42:23
# 
delete FROM item;
delete FROM details;
delete FROM manufacturer;
delete FROM itemspecifications;
delete FROM itemranking;

select * from item where listprice >1000;