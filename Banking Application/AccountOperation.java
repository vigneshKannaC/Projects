package com.zoho.banking;
interface AccountOperation {
    void transferAmount();
    void checkBalance();
    void showAccountHistory();
}

interface CustomerMenus extends AccountOperation {
    void showProfile();
    void executeMenu();
}

interface ClerkMenus {
    void accessCustomer();
}

interface CashierMenus {
    void depositAmount();
    void withdrawAmount();
}

interface ManagerMenus extends ClerkMenus {
    void accessMember(int branchId);
    void addClerk();
    void addCashier();
    void transferMember(int branchId,boolean isAdmin);
}

interface AdminMenus extends ManagerMenus {
    void addManager();
}
