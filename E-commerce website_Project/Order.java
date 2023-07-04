package com.zoho.ecommerce;
class Order{
    private int id;
    private int user_id;
    private int product_id;
    private String address;
    private String ordered_date;
    private String due_date;
    private String payment_mode;
    private String status;
    private String received_date;
    private String dead_date;
    private int quantity;
    private double total_amount;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOrdered_date() {
        return ordered_date;
    }
    public void setOrdered_date(String ordered_date) {
        this.ordered_date = ordered_date;
    }
    public String getDue_date() {
        return due_date;
    }
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    public String getPayment_mode() {
        return payment_mode;
    }
    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReceived_date() {
        return received_date;
    }
    public void setReceived_date(String received_date) {
        this.received_date = received_date;
    }
    public String getDead_date() {
        return dead_date;
    }
    public void setDead_date(String dead_date) {
        this.dead_date = dead_date;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
    public Order(int id, int user_id, int product_id, String address, String ordered_date, String due_date,
            String payment_mode, String status, String received_date, String dead_date, int quantity,
            double total_amount) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.address = address;
        this.ordered_date = ordered_date;
        this.due_date = due_date;
        this.payment_mode = payment_mode;
        this.status = status;
        this.received_date = received_date;
        this.dead_date = dead_date;
        this.quantity = quantity;
        this.total_amount = total_amount;
    }
    
}