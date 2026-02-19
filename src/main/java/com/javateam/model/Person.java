package com.javateam.model;

import java.time.LocalDate;

public class Person {

    private int idperson;
    private String lastname;
    private String firstname;
    private String nickname;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private LocalDate birthDate;

    // =========================
    // Constructors
    // =========================

    public Person() {
    }

    public Person(String lastname,
                  String firstname,
                  String nickname,
                  String phoneNumber,
                  String address,
                  String emailAddress,
                  LocalDate birthDate) {

        this.lastname = lastname;
        this.firstname = firstname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    public Person(int idperson,
                  String lastname,
                  String firstname,
                  String nickname,
                  String phoneNumber,
                  String address,
                  String emailAddress,
                  LocalDate birthDate) {

        this.idperson = idperson;
        this.lastname = lastname;
        this.firstname = firstname;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    // =========================
    // Getters & Setters
    // =========================

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    // =========================
    // toString (useful for debug)
    // =========================

    @Override
    public String toString() {
        return "Person{" +
                "idperson=" + idperson +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}