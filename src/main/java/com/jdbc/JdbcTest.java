package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;

public class JdbcTest {
    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String pass = "password";

        try {
            myConn = DriverManager.getConnection(dbUrl, user, pass);

            System.out.println("Database connection successful!\n");

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery("select * from employees");

            while (myRs.next()) {
                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
