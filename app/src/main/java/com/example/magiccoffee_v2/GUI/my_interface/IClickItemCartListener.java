package com.example.magiccoffee_v2.GUI.my_interface;

public interface IClickItemCartListener {
    void onClickItemCartPlus(int position, int quantity);
    void onClickItemCartLess(int position, int quantity);
}
