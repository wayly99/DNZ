package com.example.lastHomeWork.entity;

public class Food_Info {
    private int food_id;
    private String food_name;
    private int price;
    private int food_number;
    private int shop_id;
    private int buy_number;
    private String pictureUrl;


    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFood_number() {
        return food_number;
    }

    public void setFood_number(int food_number) {
        this.food_number = food_number;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
