package com.example.chatstation;

public class ChatList {
    public String id;
    public String name;
    public String url;
    public String lastMsg;
    public String time;
    public String phone;
    public String bio;
    public ChatList(){

    }

    public ChatList(String id, String name, String url,String lastMsg,String time) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.lastMsg = lastMsg;
        this.time = time;
    }
}
