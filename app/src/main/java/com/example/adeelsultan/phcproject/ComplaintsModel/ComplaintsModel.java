package com.example.adeelsultan.phcproject.ComplaintsModel;

/**
 * Created by AdeelSultan on 4/22/2020.
 */

public class ComplaintsModel {
    private String Name, Email, Blood, Cnic, Phone, Issue, Gender, Status, ImageURL;

    public ComplaintsModel(String name, String email, String blood, String cnic, String phone, String issue, String gender, String status, String imageURL) {
        Name = name;
        Email = email;
        Blood = blood;
        Cnic = cnic;
        Phone = phone;
        Issue = issue;
        Gender = gender;
        Status = status;
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

    public String getBlood() {
        return Blood;
    }

    public void setBlood(String blood) {
        Blood = blood;
    }

    public String getCnic() {
        return Cnic;
    }

    public void setCnic(String cnic) {
        Cnic = cnic;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
