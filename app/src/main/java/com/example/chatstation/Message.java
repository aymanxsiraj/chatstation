package com.example.chatstation;

public class Message {
    public String msg;
    public String status;
    public String userId;
    public String time;



    public Message(){}

    public Message(String msg, String status, String userId) {
        this.msg = msg;
        this.status = status;
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
