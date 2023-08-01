package com.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetDemo {
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

            // 2. Run query
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM employees");

            // 3. Get result set metadata
            ResultSetMetaData rsMetaData = myRs.getMetaData();

            // 4. Display info 
            int columnCount = rsMetaData.getColumnCount();
            System.out.println("Column count: " + columnCount + "\n");
            
            for(int column = 1; column <= columnCount; column++) {
                System.out.println("Column name " + rsMetaData.getColumnName(column));
                System.out.println("Column type name " + rsMetaData.getColumnTypeName(column));
                System.out.println("Is nullable " + rsMetaData.isNullable(column));
                System.out.println("Is auto increment " + rsMetaData.isAutoIncrement(column) + "\n");
            }

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
