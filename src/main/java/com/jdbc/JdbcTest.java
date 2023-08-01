package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
            myStmt = myConn.prepareCall("{call greet_the_department(?)}");

            // 3. Set the parameters
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setString(1, theDepartment);

            // 4. Call stored procedure
            System.out.println("Calling stored procdeure. greet_the_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finishing calling stored procedure");

            // 6. Get the value of INOUT parameter
            String theResult = myStmt.getString(1);
            System.out.println("\nThe result = " + theResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
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
