package com.example.myaccount.Contract;

public class BoardComment {


    private String comment;
    private String id;
    private String day;
    private String number;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public BoardComment(String comment, String id, String day,String number) {
        this.comment = comment;
        this.id = id;
        this.day = day;
        this.number=number;
    }

}


