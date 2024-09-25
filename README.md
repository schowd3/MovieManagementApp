# Movie Management App - Background
A comprehensive Movie Management application that allows users to perform CRUD (Create, Read, Update, Delete) operations on movie-related data. The application is implemented using JDBC (Java Database Connectivity) and interacts with an Oracle database to store and manage movie information.

This application is built with a focus on efficiency and ease of use, allowing users to manage movie entries, including adding new movies, updating existing entries, and removing outdated data.

Different classes/objects represents the entites that a user can modify.

# Features:
CRUD Operations: Full support for Create, Read, Update, and Delete operations for movie management.
Oracle Database Integration: Seamless integration with Oracle databases using JDBC.
Object-Oriented Design: The application uses different classes to represent entities such as movies, directors, and actors that can be modified by the user.
Graphical User Interface (GUI): The app includes an intuitive GUI controller that manages user interaction, making it simple to perform database operations.

# Prerequisites
Before running the Movie Management app, ensure that the following are installed and set up:

Java Development Kit (JDK) (version 8 or later)
Oracle Database with JDBC drivers properly configured.
Database Schema: Ensure the necessary database tables and schema are set up by running the provided database scripts.

#Setup Instructions
1. Database Setup
          To ensure the application runs smoothly, you need to set up the database schema first:
          
          1.Run Database Scripts: Navigate to the /db-scripts folder, where you'll find SQL scripts for creating the necessary tables and initial dataset. Execute these scripts in your Oracle database instance.
          
          2.Verify Table Creation: Confirm that all the required tables (such as Movies, Directors, Actors, etc.) are created successfully in your Oracle database.

2. Configuration:
          1. Application Properties:
          Locate the application.properties file.
          Update the datasource configurations (such as the database URL, username, and password) to match your Oracle database setup.
          Example:
          jdbc.url=jdbc:oracle:thin:@localhost:1521:xe
          jdbc.username=your_db_username
          jdbc.password=your_db_password
          
          2. GUI Controller Setup:
          
          The application uses a GUI controller to manage interactions with the database. Before running the GUI:
          Ensure that the database connection properties are correctly set in the GuiController.java file.
          Update the login credentials and connection strings as per your database configuration.
