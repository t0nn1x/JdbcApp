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
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String pass = "password";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Database connection successful!\n");

            // 2. Create a statement
            myStmt = myConn.prepareStatement(
                "SELECT * FROM employees WHERE salary > ? AND department = ?"
                );

            // 3.1. Set the parameters
            myStmt.setDouble(1, 80000);
            myStmt.setString(2, "Legal");

            // 4. Execute SQL query
            myRs = myStmt.executeQuery();

            // 5.1 Display the result
            display(myRs);

            // Reusing the prepared statement with another values
            System.out.println("Reusing prepared statement");

            // 3.2. Set the new parameters
            myStmt.setDouble(1, 25000);
            myStmt.setString(2, "HR");

            // 4.2. Execute SQL query
            myRs = myStmt.executeQuery();

            // 5.2. Display the result
            display(myRs); 


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
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
