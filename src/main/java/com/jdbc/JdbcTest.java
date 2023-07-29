package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
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

            // 3. Check employee information
            System.out.println("BEFORE THE DELETE...");
            displayEmployee(myConn, "John", "Doe");

            // 4. Executing the update
            System.out.println("DELETING an employee\n");
            int rowsAffected = myStmt.executeUpdate(
                "DELETE FROM employees " + 
                "WHERE last_name = 'Doe' and first_name = 'John'" 
                );

            // 5. Check employee information
            System.out.println("AFTER THE DELETE...");
            displayEmployee(myConn, "John", "Doe");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // 1. Prepare statement
            myStmt = myConn.prepareStatement("select last_name, first_name, email from employees where last_name=? and first_name=?");

            // 2. Set the parameters
            myStmt.setString(1, lastName);
            myStmt.setString(2, firstName);

            // 3. Execute SQL query
            myRs = myStmt.executeQuery();

            // 4. Process the result set
            while (myRs.next()) {
                String theLastName = myRs.getString("last_name");
                String theFirstName = myRs.getString("first_name");
                String email = myRs.getString("email");

                System.out.printf("%s, %s, %s\n", theLastName, theFirstName, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myStmt, myRs);
        }
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

    private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);		
    }
}
