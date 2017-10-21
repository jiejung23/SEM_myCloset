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



------ Remote connection: 

mysql -u root -proot

> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'youpassword' WITH GRANT OPTION;

> FLUSH PRIVILEGES;



------ Import .sql file:

$ mysql -u root -p

> show databases;

> use myCloset;

> source (path of sql_mycloset.sql);



------ Export .sql file:

$ cd (the folder you want to put the file in)

$ mysqldump -u root -p myCloset>sql_mycloset.sql

// If cannot find mysqldump command, please add the path of mysqldump before it

// You can mysqldump path using $ find  / -name mysql -print



------ Install MySQL:

1. Start MySQL in your computer

2. Set username and password

3. Open MySQL Workbench, create a new connection, add a new Schema named 'myCloset'

4. Import .sql file. You should download it from Database folder first.

5. Let your MySQL accessible to remote users

6. Change the DBHelper Class. Change ip to your ip4 address. Change the username and password to your own ones. (Once you change a network, the ip4 will change, too)

7. Run the project

-------Best tutorial that teaches you how to setup OpenCV on your Android Studio
https://www.youtube.com/watch?v=ObXROP8DU1U&t=438s
