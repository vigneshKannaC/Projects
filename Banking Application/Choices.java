package com.zoho.banking;

enum MainChoice {
	LOG_IN,
	LOG_OUT,
	CREATE_ACCOUNT,
	EXIT
}
enum CustomerChoice {
    SHOW_PROFILE,
    TRANSFER_AMOUNT,
    CHECK_BALANCE,
    SHOW_ACCOUNT_HISTORY,
    LOG_OUT
}

enum ClerkChoice {
    SHOW_PROFILE,
    ACCESS_CUSTOMER,
    LOG_OUT
}

enum CashierChoice {
    SHOW_PROFILE,
    DEPOSIT_AMOUNT,
    WITHDRAW_AMOUNT,
    LOG_OUT
}

enum ManagerChoice {
    SHOW_PROFILE,
    ACCESS_MEMBER,
    ADD_CLERK,
    ADD_CASHIER,
    TRANSFER_MEMBER,
    LOG_OUT
}

enum AdminChoice {
    SHOW_PROFILE,
    ACCESS_MEMBER,
    ADD_CLERK,
    ADD_CASHIER,
    ADD_MANAGER,
    TRANSFER_MEMBER,
    LOG_OUT, 
}
