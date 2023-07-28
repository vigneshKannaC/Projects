package com.zoho.banking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileReader;
import java.util.Properties;
import java.io.IOException;

final class Database {
    private FileReader fr = null;
    private Properties prop = new Properties();
    private Connection con = null;
    private static Database db = null;

    private Database() {
    
        try {
            fr = new FileReader("db_details.properties");
            prop.load(fr);
        } 
        catch (IOException e) {
            System.out.println("********File Not Found");
        }
        
        try {
            con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("pass"));
        } 
        catch (SQLException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
        
    }

    public static Database getInstance() throws SQLException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public Connection getConnection() {
        return con;
    }

    public void closeDB() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
