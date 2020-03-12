package com.example.myaccount.Contract;

public class Shoppingresultcontract {


    private String title;

    public Shoppingresultcontract(String title, int price, int number, int totalprice) {
        this.title = title;
        this.price = price;
        this.number = number;
        this.totalprice = totalprice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    private int price;
    private int number;
    private int totalprice;

}


