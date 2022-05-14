package com.example.magiccoffee_v2.DTO;

public class CartItem{

    public CartItem() {

    }

    public CartItem(int quantity, String name, String image, float totalPrice, String cfId, String temper, String size) {
        Quantity = quantity;
        Name = name;
        Image = image;
        Price = totalPrice;
        CfId = cfId;
        Temper = temper;
        Size = size;
    }

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

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
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

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    private int Quantity;
    private String Name;
    private String Image;
    private float Price;
    private String CfId;
    private String Temper;


    private String Size;


}
