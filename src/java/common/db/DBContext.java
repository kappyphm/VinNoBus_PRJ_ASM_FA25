/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.db;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kappyphm
 */
public class DBContext {

    private final String user = "sa";
    private final String pass = "123";
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=ASM_PRJ";

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

    public static void main(String[] args) {
        try {
            DBContext db = new DBContext();
            String sql = "INSERT INTO Users (username, email, hashedPass) OUTPUT inserted.uid VALUES(?,?,?)";
            Connection cnn = db.getConnection();
            try (PreparedStatement stm = cnn.prepareStatement(sql)) {
                stm.setString(1, "admin");
                stm.setString(2, "admin");
                stm.setString(3, "admin");
                
                ResultSet rs = stm.executeQuery();
                
                UUID newId = null;
                if (rs.next()) {
                    newId = UUID.fromString(rs.getString("uid"));
                    System.out.println(newId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
