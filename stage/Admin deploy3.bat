"c:\Program Files\Internet Explorer\iexplore.exe" "http://localhost:8080/manager/deploy?path=/StoreFrontAdmin"
copy C:\projects\storefront\StoreFrontAdmin\StoreFrontAdmin.war C:\projects\storefront\stage\StoreFrontAdmin.war
"c:\Program Files\Internet Explorer\iexplore.exe" "http://localhost:8080/manager/deploy?path=/StoreFrontAdmin&war=/projects/storefront/stage/StoreFrontAdmin.war"