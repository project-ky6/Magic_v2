package com.example.magiccoffee_v2.DTO;

public class Branch {
    public Branch(String address, String x, String y, String id) {
        this.Address = address;
        this.X = x;
        this.Y = y;
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }

    public String getY() {
        return Y;
    }

    public void setY(String y) {
        Y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String Address;
    private String X, Y, id;
}
