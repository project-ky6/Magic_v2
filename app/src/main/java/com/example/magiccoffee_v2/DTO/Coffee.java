package com.example.magiccoffee_v2.DTO;


import com.example.magiccoffee_v2.R;

import java.io.Serializable;
import java.util.List;

public class Coffee implements Serializable {

    public Coffee(String id, String imageLink, String name, String type, float price, List<Size> size, List<Temper> temper) {
        this.id = id;
        ImageLink = imageLink;
        Name = name;
        Type = type;
        Price = price;
        Size = size;
        Temper = temper;
    }
    private int image;
    private String id;
    private String ImageLink;
    private String Name;
    private String Type;
    private float Price;

    public List<Size> getSize() {
        return Size;
    }

    public void setSize(List<Size> size) {
        Size = size;
    }

    public List<Temper> getTemper() {
        return Temper;
    }

    public void setTemper(List<Temper> temper) {
        Temper = temper;
    }

    private List<Size> Size;
    private List<Temper> Temper;

    public int getImage() {
        return R.drawable.av2;
    }

    public void setImage() {
        this.image = R.drawable.av2;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public Coffee(int image, String name,String Type, float price) {
        this.image = image;
        this.Name = name;
        this.Price = price;
        this.Type = Type;
    }
}
