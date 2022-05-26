package com.example.magiccoffee_v2.DTO;
import java.util.List;

public class Cart {

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<CartItem> getItems() {
        return Items;
    }

    public void setItems(List<CartItem> items) {
        Items = items;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getUidNV() {
        return UidNV;
    }

    public void setUidNV(String uidNV) {
        UidNV = uidNV;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Cart(String uid, String id, String status, List<CartItem> items, String price, String note, String uidNV, String phoneNumber) {
        Uid = uid;
        this.id = id;
        Status = status;
        Items = items;
        Price = price;
        Note = note;
        UidNV = uidNV;
        PhoneNumber = phoneNumber;
    }

    public Cart(String status, List<CartItem> items, String price, String note) {
        Status = status;
        Items = items;
        Price = price;
        Note = note;
    }
    public Cart(Cart cart) {
        Uid = cart.Uid;
        this.id = cart.id;
        Status = cart.Status;
        Items = cart.Items;
        Price = cart.Price;
        Note = cart.Note;
        UidNV = cart.UidNV;
        PhoneNumber = cart.PhoneNumber;
    }
    public Cart() {
    }
    private String Uid;
    private String id;
    private String Status;
    private List<CartItem> Items;
    private String Price;
    private String Note;
    private String UidNV;


    private String PhoneNumber;

    public void updateTotalPrice(){
        int totalPrice = 0;
        for(CartItem cartItem: Items){
            totalPrice += (cartItem.getPrice() * cartItem.getQuantity());
        }
        Price = totalPrice+"";
    }

    public void addItem(CartItem item){
        for(CartItem cartItem: Items){
            if(cartItem.getCfId().equals(item.getCfId())){
                if(cartItem.getSize().equals(item.getSize())){
                    if(cartItem.getTemper().equals(item.getTemper())){
                        cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                        cartItem.setPrice(cartItem.getPrice() + item.getPrice());
                        return;
                    }
                }
            }
        }
        Items.add(item);
    }
}
