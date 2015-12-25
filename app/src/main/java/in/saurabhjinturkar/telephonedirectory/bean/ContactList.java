package in.saurabhjinturkar.telephonedirectory.bean;

import android.content.Context;

import java.util.ArrayList;

import in.saurabhjinturkar.telephonedirectory.database.services.ContactService;

/**
 * Created by Stefano on 2/19/15.
 */
public class ContactList extends ArrayList<Contact> {

    private ContactService contactService;

    public ContactList() {
    }

    public ContactList(Context context) {
        super();
        contactService = new ContactService(context);
        addAll(contactService.getAllContacts());
    }
}
