package com.stefanomunarini.telephonedirectory.bean;

import android.content.Context;

import com.stefanomunarini.telephonedirectory.database.services.ContactService;

import java.util.ArrayList;

/**
 * Created by Stefano on 2/19/15.
 */
public class ContactList extends ArrayList<Contact> {

    private ContactService contactService;

    public ContactList(Context context){
        super();
        contactService = new ContactService(context);
        addAll(contactService.getAllContacts());
    }
}
