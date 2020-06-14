package com.example.adeelsultan.phcproject.PatientsModel;

/**
 * Created by AdeelSultan on 4/9/2020.
 */

public class PatientsModel {
    private String Name, Email, Password, Phone, Address, Gender, ImageURL;

    public PatientsModel(String name, String email, String password, String phone, String address, String gender, String imageURL) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        Gender = gender;
        ImageURL = imageURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}

