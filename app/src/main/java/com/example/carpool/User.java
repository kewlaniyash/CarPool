package com.example.carpool;

public class User {
    private String Name;
    private String email;
    private String phone;
    private String car_no;

    public User() {
    }

    public User(String name, String email, String phone, String car_no) {
        Name = name;
        this.email = email;
        this.phone = phone;
        this.car_no = car_no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }
}
