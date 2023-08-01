package com.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDemo {
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        // 1. Load the properties file
        Properties props = new Properties();
        props.load(new FileInputStream("connection.properties"));

        // 2. Read the props
        String theUser = props.getProperty("user");
        String thePassword = props.getProperty("password");
        String theDbUrl = props.getProperty("dburl");

        System.out.println("\nConnecting to db...");
        System.out.println("Database URL: " + theDbUrl);
        System.out.println("User: " + theUser);

        // 3. Get a connection to DB
        Connection myConn = DriverManager.getConnection(theDbUrl, theUser, thePassword);
        System.out.println("\nConnection successful!\n");
    }
}
