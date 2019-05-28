package com.entity;

public class Teacher {
    private String uID;
    private String name;
    private int subjectid;
    private String sex;
    private String born;
    private String area;
    private String phone;
    private String email;
    private byte[] image;

    public Teacher(String name, int subjectid, String sex, String born, String area, String phone, String email, byte[] image) {
        this.name = name;
        this.subjectid = subjectid;
        this.sex = sex;
        this.born = born;
        this.area = area;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public String getSex() {
        return sex;
    }

    public String getBorn() {
        return born;
    }

    public String getArea() {
        return area;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }
}
