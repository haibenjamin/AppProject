package com.example.appproject.Model;

public class User {

    private  String name;
    private String email;
    private int wins;
    private int loses;


    public User(){}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        wins=0;
        loses=0;

    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public void addWin(){wins++;};
    public void addLose(){loses++;};



}
