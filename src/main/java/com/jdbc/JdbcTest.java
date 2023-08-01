package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

            // 2. Prepare the stored procedure call
            myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");

            // 3. Set the parameters
            myStmt.setString(1, theDepartment);

            // 4. Call stored procedure
            System.out.println("Calling stored procedure. get_employees_for_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finishing calling stored procedure");

            // 6. Get the value of the OUT parameter
            myRs = myStmt.getResultSet();

            // 7. Display the result set
            display(myRs);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        ResultSetMetaData rsmd = myRs.getMetaData();
        int numColumns = rsmd.getColumnCount();

        // Print the column names
        for (int i = 1; i <= numColumns; i++) {
            System.out.print(rsmd.getColumnName(i) + "\t");
        }
        System.out.println();

        // Print the rows of the result set
        while (myRs.next()) {
            for (int i = 1; i <= numColumns; i++) {
                System.out.print(myRs.getString(i) + "\t");
            }
            System.out.println();
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
