# Bookmark Manager Web - Module 3 Sample Application

This application demonstrates everything learned in Module 3 (Web Application Development), except for direct JavaScript DOM interaction and event handling.

The overall Bookmark Manager application consists of several parts:
- a SQL database
- a server with REST APIs for Authentication, Users, Bookmarks, and Tags
- a command line administrative client application 
- a customer facing, Vue single page web application client

Most of these are from the Module 2 sample application. Only the customer facing web application client is new. 

Before running the application, ensure that you've created the `BookmarkDB`. Then run the `database/BookmarkDB.sql` script to create the tables and populate them with test users and data.

> Note: The `BookmarkDB.sql` file contains information on the various users and their credentials. You can use this data when testing your application, or register new users.

To run the application, first open the `bookmark-server` project (from module 2) and run the `main` method of the `BookmarkManagerApplication` class. Once the server is running, you can start the customer facing Vue web application.

To run the web client, in the terminal, go into the `bookmark-web` folder and run the following commands to install dependencies and run the web application:
```
npm install
npm run serve
```
Once the web server has started, click the link shown in the terminal to open the web application. The database script establishes several user accounts to use when exploring the application. It's recommended to start with either the `job_coach` or `newbie_coder` accounts (password: `password`).

The customer facing web client allows a user to create and manage both public and private bookmarks. They may also view the public bookmarks of other users. If they come across an inappropriate bookmark from another user, they can flag it for review by a site administrator. 
