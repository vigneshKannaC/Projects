package com.zoho.banking;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

class ClerkAccess extends ClerkDBManagement implements ClerkMenus {
    private Staff staff;
    private User user;
    ResultSet rs;

    ClerkAccess() {

    }

    ClerkAccess(Staff staff) {
        this.staff = staff;
    }

    public void showProfile() {
        showStaff(staff.getRoleId(), staff.getBranchId(), false);
    }

    void showStaff(int role_id, int branch_id, boolean isClerk) {
        try {
            if (branch_id != 0)
                rs = getStaffs(role_id, branch_id);
            else
                rs = getStaffs(role_id);

            System.out.println(String.format("%-5s %-10s %-10s %-10s", "id", "name", "gender", "status"));
            System.out.println(String.format("%-5s %-10s %-10s %-10s", "--", "----", "------", "------"));

            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                System.out.println(String.format("%-5s %-10s %-10s %-10s", user.getId(), user.getName(),
                        user.getGender(), user.getStatus()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void accessCustomer() {
        showCustomers(5, staff.getBranchId(), true);
    }

    void showCustomers(int role_id, int branch_id, boolean isClerk) {
        try {
            if (branch_id != 0)
                rs = showCustomers(role_id, branch_id);
            else
                rs = showCustomers(role_id);

            System.out.println(String.format("%-5s %-10s %-15s %-10s %-15s %-10s", "id", "name", "mobile", "gender", "status", "scheme"));
            System.out.println(String.format("%-5s %-10s %-15s %-10s %-15s %-10s", "--", "----", "------", "------", "------", "------"));

            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println(String.format("%-5s %-10s %-15s %-10s %-10s %-10s", user.getCustomerId(), user.getName(),
                        user.getMobileNumber(), user.getGender(), user.getCustomerStatus(), user.getScheme()));
            }

            changeStatus(role_id, branch_id, isClerk);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String status;
    private int updated;

    void changeStatus(int role_id, int branch_id, boolean isClerk) {
        try {
            System.out.println("\n\t1. Choose id from list\t2. Go back\n");
            System.out.print("Enter your choice: ");
            int option=Validation.isNumeric(Validation.reader.readLine());
            if ( option == 1) {
                System.out.print("\nEnter the id you want to update: ");
                int id = Validation.isNumeric(Validation.reader.readLine());

                if (role_id == 5) {
                    changeCustomerStatus(role_id, branch_id, id, isClerk);
                } else {
                    changeStaffStatus(role_id, branch_id, id);
                }
            }
            else if( option != 2 ){            	
                System.out.println("Invalid choice!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeCustomerStatus(int role_id, int branch_id, int id, boolean isClerk) {
        try {
            rs = findCustomer(id);

            if (rs.next()) {
                System.out.print("Enter the new status: ");
                status = Validation.reader.readLine();

                if (!isClerk) {
                    updated = statusUpdate(status, id);

                    if (updated > 0) {
                        insertAccNo(id, branch_id);
                        statusUpdate(id);
                        System.out.println("User status updation successful");
                    } else {
                        System.out.println("Customer didn't get clerk approval!");
                    }
                } else {
                    updateStatus(status, id);
                    System.out.println("Customer status updation successful!");
                }
            } else {
                System.out.println("Invalid user id!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeStaffStatus(int role_id, int branch_id, int id) {
        try {
            rs = findStaff(role_id, id);

            if (rs.next()) {
                System.out.print("Enter the new status: ");
                status = Validation.reader.readLine();
                updated = setStatus(status, id);

                if (updated > 0) {
                    System.out.println("Staff status updation successful!");
                } else {
                    System.out.println("Staff status updation unsuccessful!!!");
                }
            } else {
                System.out.println("Invalid staff id!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertAccNo(int customer_id, int branch_id) {
        String acc_no = Validation.generateAccountNumber(customer_id, branch_id);
        updateAccountNo(acc_no, customer_id);
    }

    public void executeMenu() {
        try {
            while (true) {
                System.out.println("\n\t\t\tClerk Menu");
                System.out.println("\t\t\t----- ----");
                System.out.println("\t\t\t1. Show Profile");
                System.out.println("\t\t\t2. Access Customer");
                System.out.println("\t\t\t3. Log Out");
                System.out.print("Enter your choice between (1-3): ");
                int choice = Validation.isNumeric(Validation.reader.readLine());

                if (choice > 0 && choice < 4) {
                    ClerkChoice clerkChoice = ClerkChoice.values()[choice - 1];

                    switch (clerkChoice) {
                        case SHOW_PROFILE:
                            showProfile();
                            break;
                        case ACCESS_CUSTOMER:
                            accessCustomer();
                            break;
                        case LOG_OUT:
                            System.out.println("Log out successful! Thanks for coming!");
                            return;
                    }
                } else {
                    System.out.print("Invalid choice!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

