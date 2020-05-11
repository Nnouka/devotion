package com.nouks.devotion.domain.dtos.data;

public class EmailAddress {
    private String name;
    private String email;

    public EmailAddress() {
    }

    public EmailAddress(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailAddress{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
