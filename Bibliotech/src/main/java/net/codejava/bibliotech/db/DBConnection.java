package net.codejava.bibliotech.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static DataSource dataSource;
    
    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/bibliotechDB");
        } catch (NamingException e) {
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}