package com.example.myaccount.Contract;

public class ItemList {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String content;

    public ItemList(String title, String content, String price, String photo) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.photo = photo;
    }

    private String price;
    private String photo;

}
