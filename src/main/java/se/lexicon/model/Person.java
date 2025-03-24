package se.lexicon.model;


import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class Person {

    private int id;
    private String name;
    private String email;

    public Person(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public Person(int id, String name, String email) {
        this(name, email);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        if (name.length() > 100){
            throw new StringIndexOutOfBoundsException("Name cannot exceed 100 characters");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Objects.requireNonNull(email);
        if (email.length() > 100){
            throw new StringIndexOutOfBoundsException("Email cannot exceed 100 characters");
        }
        EmailValidator validator = EmailValidator.getInstance();
        if(validator.isValid(email))
            this.email = email.toLowerCase();
        else{
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
