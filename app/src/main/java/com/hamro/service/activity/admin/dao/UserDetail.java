package com.hamro.service.activity.admin.dao;

import androidx.annotation.NonNull;

public class UserDetail {

    private String name,phone,email="",register_date;

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @NonNull
    @Override
    public String toString() {
        return name+"\n"+phone+"\n"+email+"\n"+register_date;
    }
}
