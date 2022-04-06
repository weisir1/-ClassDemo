package com.example.classdemo.Util;

public class DBValue {
    private int id;
    private String name;
    private String phone;
    private String sex;
    private String userName;
    private String passWord;

    public DBValue(int id, String name, String phone, String sex, String userName, String passWord) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.userName = userName;
        this.passWord = passWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
