package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        CallableStatement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String pass = "password";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Database connection successful!\n");

            String theDepartment = "Engineering";
            int theIncreaseAmount = 10000;

            // 2. Show salaries before
            System.out.println("Salaries before: ");
            showSalaries(myConn, theDepartment);

            // 3. Prepare the stored procedure call
            myStmt = (CallableStatement) myConn.prepareCall(
                    "{call increase_salaries_for_department(?, ?)}");

            // 4. Set the params
            myStmt.setString(1, theDepartment);
            myStmt.setDouble(2, theIncreaseAmount);

            // 5. Call stored procedure
            System.out.println("\nCalling stored procedure");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // 6. Show salaries after
            System.out.println("\nSalaries after: ");
            showSalaries(myConn, theDepartment);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
        CallableStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Prepare statement
            myStmt = (CallableStatement) myConn.prepareCall(
                    "{call get_employees_for_department(?)}");

            // Set the parameter
            myStmt.setString(1, theDepartment);

            // Call stored procedure
            myStmt.execute();

            // Get the result set
            myRs = myStmt.getResultSet();

            // Display the result set
            display(myRs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myStmt, myRs);
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
