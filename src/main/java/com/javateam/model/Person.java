package com.javateam.model;

import java.time.LocalDate;

public class Person {

    private int idperson;
    private String firstname;
    private String lastname;
    private String nickname;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private LocalDate birthDate;

    public Person() {}

    public Person(String firstname,
                  String lastname,
                  String nickname,
                  String phoneNumber,
                  String emailAddress,
                  String address,
                  LocalDate birthDate) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Person(int idperson,
                  String firstname,
                  String lastname,
                  String nickname,
                  String phoneNumber,
                  String emailAddress,
                  String address,
                  LocalDate birthDate) {

        this(firstname, lastname, nickname, phoneNumber, emailAddress, address, birthDate);
        this.idperson = idperson;
    }

    public int getIdperson() { return idperson; }
    public void setIdperson(int idperson) { this.idperson = idperson; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}