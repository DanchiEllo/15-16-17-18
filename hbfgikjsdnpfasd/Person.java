package com.example.restDemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Person {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String surname;
    private String lastname;
    private LocalDate birthday;
    @OneToMany(cascade = CascadeType.ALL)
    List<Message> messages;
    public Person(int id, String firstname, String surname, String lastname, LocalDate birthday, List<Message> messages) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.messages = messages;
    }

    public Person() {

    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setId(Long id) {
        this.id = Math.toIntExact(id);
    }

    public Long getId() {
        return (long) id;
    }
}