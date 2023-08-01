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
            myStmt = myConn.prepareCall("{call get_count_for_department(?, ?)}");

            // 3. Set the parameters
            myStmt.setString(1, theDepartment);
            myStmt.registerOutParameter(2, Types.INTEGER);

            // 4. Call stored procedure
            System.out.println("Calling stored procedure. get_count_for_department('" + theDepartment + "', ?)");
            myStmt.execute();
            System.out.println("Finishing calling stored procedure");

            // 6. Get the value of the OUT parameter
            int theCount = myStmt.getInt(2);
            System.out.println("\nThe count = " + theCount);
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
