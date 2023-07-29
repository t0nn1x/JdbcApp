package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String pass = "password";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Database connection successful!\n");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Inserting data 
            System.out.println("Inseting a new employee to database\n");
            int rowsAffected = myStmt.executeUpdate(
                "INSERT INTO employees " +
                "(last_name, first_name, email, department, salary)" +
                "VALUES " +
                "('Anton', 'Khrobust', 'antonhrobust@gmail.com', 'DEV', '100000.00')"
                );

            // 4. Verify this by getting list of employees
            myRs = myStmt.executeQuery("select * from employees");

            // 5. Process the result set
            while (myRs.next()) {
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
