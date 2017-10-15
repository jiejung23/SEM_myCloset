# SEM_myCloset


Android API Version: 24

Java Version: 1.8

Sprint 1:
- Choose a picture and add a cloth
- Show all clothes
- Show number of clothes in different categories
- Integrate OpenCV into Android Studio


MySQL Download: https://dev.mysql.com/downloads/mysql/

MySQL Workbench Download: https://dev.mysql.com/downloads/workbench/


Database Name: myCloset

Table Name: clothes

Import .sql file:

$ mysql -u root -p

> show databases;

> use myCloset;

> source (path of sql_mycloset.sql);


Export .sql file:

$ cd (the folder you want to put the file in)

$ mysqldump -u root -p myCloset>sql_mycloset.sql

// If cannot find mysqldump command, please add the path of mysqldump before it

// You can mysqldump path using $ find  / -name mysql -print

