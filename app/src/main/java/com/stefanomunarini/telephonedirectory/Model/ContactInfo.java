package com.stefanomunarini.telephonedirectory.Model;

/**
 * Created by Stefano on 2/18/15.
 */
public class ContactInfo {

    private String name;
    private String surname;
    private String number;

    public ContactInfo(String name, String surname, String number){
        setName(name);
        setSurname(surname);
        setNumber(number);
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
