package com.stefanomunarini.telephonedirectory.database.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.stefanomunarini.telephonedirectory.database.MyDBAdapter;

/**
 * Created by Stefano on 2/18/15.
 */
public class ContactService {

    private MyDBAdapter dbHelper;
    protected ContactService(Context context){
        dbHelper = new MyDBAdapter(context);
    }

    private ContentValues v;

    /**
     * Insert a new entry
     */
    public boolean insertContact (String name, String surname, String number){
        v = new ContentValues();
        v.put(MyDBAdapter.KEY_NAME, name);
        v.put(MyDBAdapter.KEY_SURNAME, surname);
        v.put(MyDBAdapter.KEY_NUMBER, number);

        return dbHelper.executeInsert(v);
    }

    /**
     * Update an entry
     */
    public boolean updateContact (int ID, String name, String surname, String number){
        v = new ContentValues();
        v.put(MyDBAdapter.KEY_NAME, name);
        v.put(MyDBAdapter.KEY_SURNAME, surname);
        v.put(MyDBAdapter.KEY_NUMBER, number);

        return dbHelper.executeUpdate(v, ID);
    }

    /**
     * Delete an entry
     */
    public boolean deleteContact (int ID){
        return dbHelper.executeDeletion(ID);
    }

    /**
     * Get single contact
     */
    public Cursor getContact (int ID){
        String query = "SELECT * FROM " + MyDBAdapter.TABLE_NAME + " WHERE " + MyDBAdapter.KEY_ID + "=" + ID + " ORDER BY " + MyDBAdapter.KEY_NAME + ", " + MyDBAdapter.KEY_SURNAME + ", " + MyDBAdapter.KEY_NUMBER;
        return dbHelper.getCursor(query);
    }

    /**
     * Get all contacts
     */
    public Cursor getAllContacts (){
        String query = "SELECT * FROM " + MyDBAdapter.TABLE_NAME + " ORDER BY " + MyDBAdapter.KEY_NAME + ", " + MyDBAdapter.KEY_SURNAME + ", " + MyDBAdapter.KEY_NUMBER;
        return dbHelper.getCursor(query);
    }



}
