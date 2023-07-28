package com.zoho.banking;
class Staff {
    private int userId;
    private int roleId;
    private int branchId;
    private String name;
    
    Staff(int userId, int roleId, int branchId, String name) {
        this.userId = userId;
        this.roleId = roleId;
        this.branchId = branchId;
        this.name = name;
    }
    
    int getUserId() {
        return userId;
    }
    int getRoleId() {
        return roleId;
    }
    int getBranchId() {
        return branchId;
    }
    String getName() {
        return name;
    }

}
