# internet-shop

This is a N-tier client-server architectured project with minimum necessary functionality and various access restrictions for administrators and users.
Project has DB layer, DAO layer, Service layer, Controllers layer and View layer.
This project has been developed according to SOLID principles with authorization and authentication by RBAC filter strategy.
 ## Technologies
 * Java Servlets
 * Web Filters
 * JSP
 * Tomcat
 * MySQL
 * JDBC
 ## Functionality
 * User: See all products and add them to the cart, see its cart and remove products from it, complete order, see all its orders.
 * Administrator: Delete, add products, see and delete registered users, see and delete completed orders, see order details.
 * For each: Login and registrate.
 ## Setting Up project
 To set up the project, you need:
 * run sql queries provided in src/main/resources/init_db.sql in your RDBMS to create local DB for the project.
 * to create user for the DB schema and configure his access rights, so the application could produce changes 
 in the DB please run following queries in your RDBMS:<br>
     CREATE USER 'admin'@'localhost'<br>
       &emsp; IDENTIFIED BY 'matestudent';<br>
     GRANT ALL<br>
       &emsp; ON internet_shop.*<br>
       &emsp; TO 'admin'@'localhost';
 * after registration and logging in as the new user please, use "Injecting test data in to DB" link to insert 
 some test data as well as the admin user. After this procedure you may login as Admin using "admin" as login and 
 "222" as password. There also will be User with login “user” and password ”111”. 
