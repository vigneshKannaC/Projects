package com.zoho.banking;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

class ManagerDBManagement extends ClerkDBManagement {
    boolean isExists(long mobile_no, int branch_id) throws SQLException {
        rs = st.executeQuery("select id from users where mobile_no=" + mobile_no + " and branch_id=" + branch_id);
        if (rs.next())
            return true;
        return false;
    }

    int getBranchId(int userId) throws SQLException {
        rs = st.executeQuery("select branch_id from users where id=" + userId);
        rs.next();
        return rs.getInt(1);
    }

    int getBranchId(String branchName) throws SQLException {
        rs = st.executeQuery("select id from branches where name=upper('" + branchName + "')");
        rs.next();
        return rs.getInt(1);
    }

    int validateMember(int id) throws SQLException {
        rs = st.executeQuery("select id from users where id=" + id);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }

    ResultSet putMember(int role_id, String name, long mobile_no, String gender, Date date, String status, int branch) throws SQLException {
        pst = con.prepareStatement("insert into users values(default,?,?,?,?,?,?,default,?) returning id");
        pst.setInt(1, role_id);
        pst.setString(2, name);
        pst.setLong(3, mobile_no);
        pst.setDate(4, date);
        pst.setString(5, gender);
        pst.setInt(6, branch);
        pst.setString(7, status);
        rs = pst.executeQuery();
        return rs;
    }

    void changeBranch(int userId, int branchId) throws SQLException {
        st.executeUpdate("update users set branch_id=" + branchId + " where id=" + userId);
    }
}

