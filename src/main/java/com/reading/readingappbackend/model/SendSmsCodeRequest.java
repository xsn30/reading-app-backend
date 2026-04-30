package com.reading.readingappbackend.model;

public class SendSmsCodeRequest {

    private String username;
    private String phone;

    public SendSmsCodeRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}