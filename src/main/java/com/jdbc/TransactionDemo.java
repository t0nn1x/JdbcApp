package com.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionDemo {
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

            // 2. Turn off auto commit
            myConn.setAutoCommit(false);

            // 3. Show salaries before
            System.out.println("Salaries before: \n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

            // 4. Transaction Step 1: Delete all HR employees
            myStmt = myConn.createStatement();
            myStmt.executeUpdate("DELETE FROM employees WHERE department = 'HR'");

            // 5. Transaction Step 2: Set salaries to 300000 for all Engineering
            myStmt.executeUpdate("UPDATE employees SET salary = 300000 WHERE department = 'Engineering'");
            System.out.println("\nTransaction steps are ready \n");

            // 6. Ask user if it is OK to save
            boolean ok = askUserIfOkToSave();

            if (ok) {
                // store in db
                myConn.commit();
                System.out.println("\n>>Transaction committed\n");
            } else {
                // discard
                myConn.rollback();
                System.out.println("\n>>Transaction rolled back");
            }

            // 7. Show salaries after
            System.out.println("Salaries after: \n");
            showSalaries(myConn, "HR");
            showSalaries(myConn, "Engineering");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    private static boolean askUserIfOkToSave() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is it okay to save? 1 for Yes, 0 for No");
        int input = scanner.nextInt();
        return input == 1;
    }

    private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Prepare statement
            myStmt = myConn.prepareStatement("SELECT * FROM employees WHERE department=?");
            myStmt.setString(1, theDepartment);

            // Execute SQL query
            myRs = myStmt.executeQuery();

            // Process result set
            while (myRs.next()) {
                String lastName = myRs.getString("last_name");
                String firstName = myRs.getString("first_name");
                double salary = myRs.getDouble("salary");
                String department = myRs.getString("department");

                System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
            }
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
