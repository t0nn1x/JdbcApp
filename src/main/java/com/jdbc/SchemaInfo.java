package com.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInfo {
    public static void main(String[] args) throws SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String columnNamePattern = null;
        String[] Types = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String pass = "password";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("Database connection successful!\n");

            // 2. Get metadata
            DatabaseMetaData databaseMetaData = myConn.getMetaData();

            // 3. Get list of tables
            System.out.println("List of tables");
            System.out.println("--------------");

            myRs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, Types);

            while(myRs.next()) {
                System.out.println(myRs.getString("TABLE_NAME"));
            } 

            // 4. Get list of columns
            System.out.println("\n\nList of columns");
            System.out.println("--------------");

            myRs = databaseMetaData.getColumns(catalog, schemaPattern, "employees", columnNamePattern);

            while(myRs.next()) {
                System.out.println(myRs.getString("COLUMN_NAME"));
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
