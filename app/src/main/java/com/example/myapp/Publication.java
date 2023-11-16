package com.example.myapp;

public class Publication {

    private String UserName;
    private String message;
    private String dateTime;


    Publication(String UserName,String message,String dateTime){
        this.UserName = UserName;
        this.dateTime = dateTime;
        this.message = message;

    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
