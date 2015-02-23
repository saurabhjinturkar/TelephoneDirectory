package com.stefanomunarini.telephonedirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stefanomunarini.telephonedirectory.ContactListActivity;
import com.stefanomunarini.telephonedirectory.interfaces.DBAdapterInterface;

/**
 * Created by Stefano on 2/18/15.
 */
public class MyDBAdapter extends SQLiteOpenHelper implements DBAdapterInterface {

    public MyDBAdapter(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    // Database instance
    public static SQLiteDatabase db;

    // TAG used for Logs
    public static String TAG = "Telephone-Directory";

    //Database name
    private static final String DATABASE_NAME = "telephonedirectory.db";

    //Database version
    private static final int SCHEMA_VERSION = 1;

    //Database path in device
    public static final String DB_PATH = "/data/data/" + ContactListActivity.class.getPackage().getName() + "/databases/telephonedirectory.db";

    /**
     * __________________
     * |                 |
     * |     CONTACT     |
     * |_________________|
     */
    //TABLE NAME
    public static final String TABLE_NAME = "contact";

    //ATTRIBUTE NAME
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NUMBER = "number";

    // CREATE TABLE QUERY
    private static final String TABLE_CONTACT_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " VARCHAR NOT NULL, " +
                    KEY_SURNAME + " VARCHAR NOT NULL, " +
                    KEY_NUMBER + " VARCHAR NOT NULL);";

    /**
     * Called automatically ONLY the first time the app is opened
     * @param database istanza della superclasse
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CONTACT_CREATE);
    }

    /**
     * Called to upgrade the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Open db connection
     */
    public void open() {
        db = getWritableDatabase();
    }
    /**
     * Close db connection
     */
    public void close() {
        db.close();
    }

    @Override
    public boolean executeInsert(ContentValues v) {
        open();
        try{
            db.insert(TABLE_NAME, null, v);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean executeUpdate(ContentValues v, int ID) {
        open();
        try{
            db.update(TABLE_NAME, v, KEY_ID + "=" + ID, null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean executeDeletion(int ID) {
        open();
        try{
            db.delete(TABLE_NAME,KEY_ID + "=" + ID,null);
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Cursor getCursor(String query) {
        open();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}