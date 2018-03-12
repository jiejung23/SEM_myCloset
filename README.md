# SEM_myCloset

------ Features:<br>
Add and manage clothes<br>
Search clothes by color and free tags<br>
Recognize the color of the clothing automatically<br>
Identify the color name by RGB value<br>
Recommend to declutter useless clothes<br>
Provide channels to declutter useless clothes<br>
Recommend new styles<br>
Manage the liked styles
<br>

------ Configuration:<br>
Android API Version: 24<br>
Java Version: 1.8
MySQL with JDBC
<br>
MySQL Download: https://dev.mysql.com/downloads/mysql/<br>
MySQL Workbench Download: https://dev.mysql.com/downloads/workbench/
<br>

### Database Instruction
Database Name: myCloset<br>
Table Name: clothes
<br>

------ Remote connection: <br>
mysql -u root -proot<br>
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'youpassword' WITH GRANT OPTION;<br>
FLUSH PRIVILEGES;
<br>

------ Import .sql file:<br>
$ mysql -u root -p<br>
show databases;<br>
use myCloset;<br>
source (path of sql_mycloset.sql);
<br>

------ Export .sql file:<br>
$ cd (the folder you want to put the file in)<br>
$ mysqldump -u root -p myCloset>sql_mycloset.sql<br>
// If cannot find mysqldump command, please add the path of mysqldump before it<br>
// You can mysqldump path using $ find  / -name mysql -print
<br>

------ Install MySQL:<br>
1. Start MySQL in your computer<br>
2. Set username and password<br>
3. Open MySQL Workbench, create a new connection, add a new Schema named 'myCloset'<br>
4. Import .sql file. You should download it from Database folder first.<br>
5. Let your MySQL accessible to remote users<br>
6. Change the DBHelper Class. Change ip to your ip4 address. Change the username and password to your own ones. (Once you change a network, the ip4 will change, too)<br>
7. Run the project
<br>
------- Best tutorial that teaches you how to setup OpenCV on your Android Studio<br>
https://www.youtube.com/watch?v=ObXROP8DU1U&t=438s
<br>
