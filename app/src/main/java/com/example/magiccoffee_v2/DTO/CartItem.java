package com.example.magiccoffee_v2.DTO;

public class CartItem{

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getCfId() {
        return CfId;
    }

    public void setCfId(String cfId) {
        CfId = cfId;
    }

    public String getTemper() {
        return Temper;
    }

    public void setTemper(String temper) {
        Temper = temper;
    }

    public CartItem(int quantity, String name, String image, double price, String cfId, String temper) {
        Quantity = quantity;
        Name = name;
        Image = image;
        Price = price;
        CfId = cfId;
        Temper = temper;
    }

    private int Quantity;

    private String Name;
    private String Image;
    private double Price;
    private String CfId;
    private String Temper;


}
