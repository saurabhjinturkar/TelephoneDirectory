package com.stefanomunarini.telephonedirectory.bean;

/**
 * Created by Stefano on 2/19/15.
 */
public class Contact {

    private String id;
    private String name;
    private String surname;
    private String number;
    private String city;
    private String emailid;

    /**
     * Default Constructor
     */
    public Contact() {

    }

    public Contact(String id, String name, String surname, String number, String city, String emailid ) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setNumber(number);
        this.setCity(city);
        this.setEmailid(emailid);
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
