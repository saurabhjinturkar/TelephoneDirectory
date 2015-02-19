package com.stefanomunarini.telephonedirectory.bean;

/**
 * Created by Stefano on 2/19/15.
 */
public class Contact {

    private String id;
    private String name;
    private String surname;
    private String number;

    public Contact(String id, String name, String surname, String number) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setNumber(number);
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

}
