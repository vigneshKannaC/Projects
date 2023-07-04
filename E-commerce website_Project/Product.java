package com.zoho.ecommerce;
class Product {
    private int id;
    private String brand_name;
    private String model_name;
    private String product_name;
    private int quantity;
    private double price;

    public Product(int id, String brand_name, String model_name, String product_name, int quantity, double price) {
        this.id = id;
        this.brand_name = brand_name;
        this.model_name = model_name;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return ""+String.format("\t\t%-5s %-15s %-20s %-30s %-12s %-10s",id,brand_name,model_name,product_name,quantity,price);
    }

}
