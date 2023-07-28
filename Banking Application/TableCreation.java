package com.zoho.banking;
import java.sql.SQLException;
import java.sql.Statement;

class TableCreation {

    private static Statement st;

    static {
        try {
		st = Database.getInstance().getConnection().createStatement();
		createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    static void createTables() {      
            createRolesTable();
            createSchemesTable();
            createCategoriesTable();
            createBranchesTable();
            createUsersTable();
            createCustomersTable();
            createTransactionsTable();
    }

    private static void createRolesTable() {
        try {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS roles (id SERIAL PRIMARY KEY, name VARCHAR NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createSchemesTable() {
        try {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS schemes (id SERIAL PRIMARY KEY, name VARCHAR NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCategoriesTable() {
        try {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name VARCHAR NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createBranchesTable() {
        try {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS branches (id SERIAL PRIMARY KEY, name VARCHAR NOT NULL, ifsc VARCHAR(11) NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUsersTable() {
        try {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, role_id INT NOT NULL, name VARCHAR NOT NULL, mobile_no BIGINT NOT NULL, dob date NOT NULL, gender VARCHAR NOT NULL, branch_id INT NOT NULL, password VARCHAR, status varchar default 'inactive', FOREIGN KEY (branch_id) REFERENCES branches (id) ON DELETE CASCADE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCustomersTable() {
        try {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS customers (id SERIAL PRIMARY KEY, user_id INT NOT NULL, aadhar BIGINT NOT NULL, scheme_id INT NOT NULL, category_id INT NOT NULL, account_no varchar(10), balance BIGINT default 0, opening_date DATE DEFAULT CURRENT_DATE, address VARCHAR NOT NULL, status varchar default 'applied', FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE, FOREIGN KEY (scheme_id) REFERENCES schemes (id) ON DELETE CASCADE, FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void createTransactionsTable() {
        try {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS transactions (id SERIAL PRIMARY KEY, from_id INT, to_id INT, amount BIGINT NOT NULL, transaction_date DATE DEFAULT CURRENT_DATE, FOREIGN KEY (from_id) REFERENCES customers (id) ON DELETE CASCADE, FOREIGN KEY (to_id) REFERENCES customers (id) ON DELETE CASCADE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
