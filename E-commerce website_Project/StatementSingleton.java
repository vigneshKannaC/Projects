package com.zoho.ecommerce;
import java.sql.SQLException;
import java.sql.Statement;

final class StatementSingleton {
    private static Statement st = null;
    private static StatementSingleton single = null;

    private StatementSingleton() throws SQLException {
        st = Database.getInstance().getConnection().createStatement();
    }

    public synchronized static Statement getStatement() throws SQLException {
        if (single == null || st == null)
            single = new StatementSingleton();
        return st;
    }

    public static void close() throws SQLException {
        if (st != null) {
            st.close();
            st = null;
        }
        single = null;
    }
}
