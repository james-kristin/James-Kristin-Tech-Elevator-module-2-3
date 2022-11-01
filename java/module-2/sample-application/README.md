# Bookmark Manager - Module 2 Sample Application

This application demonstrates everything learned in Module 2 (Client-Server Programming in Java).

The overall Bookmark Manager application consists of several parts:
- a SQL database
- a server with REST APIs for Authentication, Users, Bookmarks, and Tags
- a command line administrative client application
- a customer facing, Vue single page web application client (part of Module 3)

Throughout the application, comments at the top of each class and interface explain its purpose.

Before running the application, create the `BookmarkDB` database. Then run the `database/BookmarkDB.sql` script to create the tables with some test users and data.

> Note: The `BookmarkDB.sql` file contains information on the various users and their credentials that are created and available for use when testing.

To run the application, first open the `bookmark-server` project and run the `main` method of the `BookmarkManagerApplication` class. Once the server is running, you can start the administrative client application.

To run the command line administrative client, open the `bookmark-admin` project and run the `main` method of the `BookmarkAdminApplication` class. You can only login with a user account that has the admin role. There is an *admin* user setup in the test data from `BookmarkDB.sql` file—username: `admin`, password: `password`.

The administrative client allows an admin to manage public bookmarks. One of the main use cases for an admin user is to review and handle any inappropriate, flagged bookmarks. If the bookmark is inappropriate, the admin may delete the bookmark. Otherwise they may remove the flag.

You can test the endpoints of the server REST API directly using Postman. In the `bookmark-server\postman` folder, there is a `Bookmark Manager.postman_collection.json` file that you can import that creates a Collection that you can use for testing. This collection saves the user token after login to use when making other requests.
