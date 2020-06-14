package com.example.adeelsultan.phcproject.DoctorsModel;

/**
 * Created by AdeelSultan on 4/9/2020.
 */

public class DoctorsModel {

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

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public Float getD_rting() {
        return D_rting;
    }

    public void setD_rting(Float d_rting) {
        D_rting = d_rting;
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

    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private String Address;
    private String Specialty;
    private Float D_rting;
    private String Gender;

    public DoctorsModel(String name, String email, String password, String phone, String address, String specialty, String d_rting, String gender, String imageURL) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        Specialty = specialty;
        D_rting = Float.parseFloat(d_rting);
        Gender = gender;
        ImageURL = imageURL;
    }

    private String ImageURL;

}
