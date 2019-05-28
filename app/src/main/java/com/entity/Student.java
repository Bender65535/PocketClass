package com.entity;

public class Student {
    private String uID;
    private String name;
    private int classid;
    private String sex;
    private String born;
    private String area;
    private String phone;
    private String email;
    private byte[] image;

    public Student(String name,int classid, String sex, String born, String area, String phone, String email,byte[] image) {
        this.name = name;
        this.classid=classid;
        this.sex = sex;
        this.born = born;
        this.area = area;
        this.phone = phone;
        this.email = email;
        this.image=image;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }
}
