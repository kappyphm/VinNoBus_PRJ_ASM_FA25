/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.db;

import java.sql.*;

/**
 *
 * @author kappyphm
 */
public class DBContext {

    private final String user = "sa";
    private final String pass = "sa";
    private final String db = "VinNoBus";
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName="+db;

    public DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy JDBC Driver!", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }



}
