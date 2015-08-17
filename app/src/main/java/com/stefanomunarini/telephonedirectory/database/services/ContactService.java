package com.stefanomunarini.telephonedirectory.database.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.stefanomunarini.telephonedirectory.bean.Contact;
import com.stefanomunarini.telephonedirectory.database.MyDBAdapter;

import java.util.ArrayList;

/**
 * Created by Stefano on 2/18/15.
 */
public class ContactService {

    private MyDBAdapter dbHelper;
    private ContentValues v;

    public ContactService(Context context) {
        dbHelper = new MyDBAdapter(context);
    }

    /**
     * Insert a new entry
     */
    public boolean insertContact(String name, String surname, String number) {
        v = new ContentValues();
        v.put(MyDBAdapter.KEY_NAME, name);
        v.put(MyDBAdapter.KEY_SURNAME, surname);
        v.put(MyDBAdapter.KEY_NUMBER, number);

        return dbHelper.executeInsert(v);
    }

    /**
     * Update an entry
     */
    public boolean updateContact(int ID, String name, String surname, String number) {
        v = new ContentValues();
        v.put(MyDBAdapter.KEY_NAME, name);
        v.put(MyDBAdapter.KEY_SURNAME, surname);
        v.put(MyDBAdapter.KEY_NUMBER, number);

        return dbHelper.executeUpdate(v, ID);
    }

    /**
     * Delete an entry
     */
    public boolean deleteContact(int ID) {
        return dbHelper.executeDeletion(ID);
    }

    /**
     * Get single contact
     */
    public Contact getContact(int ID) {
        String query = "SELECT * FROM " + MyDBAdapter.TABLE_NAME + " WHERE " + MyDBAdapter.KEY_ID + "=" + ID + " ORDER BY " + MyDBAdapter.KEY_NAME + ", " + MyDBAdapter.KEY_SURNAME + ", " + MyDBAdapter.KEY_NUMBER;
        Cursor c = dbHelper.getCursor(query);
        Contact contact = null;
        while (c.moveToNext()) {
            contact = new Contact(
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_ID)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_NAME)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_SURNAME)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_NUMBER)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_CITY)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_EMAILID)));
        }
        return contact;
    }

    /**
     * Get single contact
     */
    public Contact getContact(String searchCriteria) {
        String query = "SELECT * FROM " + MyDBAdapter.TABLE_NAME + " WHERE " + MyDBAdapter.KEY_NAME + " Like %" + searchCriteria + "% OR " + MyDBAdapter.KEY_SURNAME + " Like %" + searchCriteria + "% OR " + MyDBAdapter.KEY_CITY + " Like %" + searchCriteria + "%";
        Cursor c = dbHelper.getCursor(query);
        Contact contact = null;
        while (c.moveToNext()) {
            contact = new Contact(
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_ID)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_NAME)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_SURNAME)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_NUMBER)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_CITY)),
                    c.getString(c.getColumnIndex(MyDBAdapter.KEY_EMAILID)));
        }
        return contact;
    }

    /**
     * Get all contacts
     */
    public ArrayList<Contact> getAllContacts() {
        String query = "SELECT * FROM " + MyDBAdapter.TABLE_NAME + " ORDER BY " + MyDBAdapter.KEY_NAME + ", " + MyDBAdapter.KEY_SURNAME + ", " + MyDBAdapter.KEY_NUMBER;
        Cursor c = dbHelper.getCursor(query);
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {
                Contact contact = new Contact((c.getString(c.getColumnIndex(MyDBAdapter.KEY_ID))),
                        c.getString(c.getColumnIndex(MyDBAdapter.KEY_NAME)),
                        c.getString(c.getColumnIndex(MyDBAdapter.KEY_SURNAME)),
                        c.getString(c.getColumnIndex(MyDBAdapter.KEY_NUMBER)),
                        c.getString(c.getColumnIndex(MyDBAdapter.KEY_CITY)),
                        c.getString(c.getColumnIndex(MyDBAdapter.KEY_EMAILID)));
                contactArrayList.add(contact);
            }
        }
        return contactArrayList;
    }
}
