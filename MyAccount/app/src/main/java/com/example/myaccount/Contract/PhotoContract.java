package com.example.myaccount.Contract;

import android.graphics.Bitmap;

public class PhotoContract {


    private String photo;


    public PhotoContract(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}


