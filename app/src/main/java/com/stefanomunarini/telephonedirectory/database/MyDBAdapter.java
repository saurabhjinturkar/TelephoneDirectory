package com.stefanomunarini.telephonedirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stefanomunarini.telephonedirectory.ContactListActivity;
import com.stefanomunarini.telephonedirectory.interfaces.DBAdapterInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Stefano on 2/18/15.
 */
public class MyDBAdapter extends SQLiteOpenHelper implements DBAdapterInterface {

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
    public static final String KEY_CITY = "city";
    public static final String KEY_EMAILID = "emailid";
    public static final String KEY_ADDRESS = "address";

    // CREATE TABLE QUERY
    private static final String TABLE_CONTACT_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " VARCHAR NOT NULL, " +
                    KEY_SURNAME + " VARCHAR NOT NULL, " +
                    KEY_EMAILID + " VARCHAR NOT NULL, " +
                    KEY_CITY + " VARCHAR NOT NULL, " +
                    KEY_ADDRESS + " VARCHAR NOT NULL, " +
                    KEY_NUMBER + " VARCHAR NOT NULL);";
    //Database name
    private static final String DATABASE_NAME = "telephonedirectory.db";
    //Database version
    private static final int SCHEMA_VERSION = 7;
    // Database instance
    public static SQLiteDatabase db;
    // TAG used for Logs
    public static String TAG = "Telephone-Directory";

    private Context context;

    public MyDBAdapter(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.context = context;
    }

    /**
     * Called automatically ONLY the first time the app is opened
     *
     * @param database istanza della superclasse
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CONTACT_CREATE);

        insertTestData(database);
    }

    /**
     * Called to upgrade the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

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
        try {
            db.insert(TABLE_NAME, null, v);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean executeUpdate(ContentValues v, int ID) {
        open();
        try {
            db.update(TABLE_NAME, v, KEY_ID + "=" + ID, null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean executeDeletion(int ID) {
        open();
        try {
            db.delete(TABLE_NAME, KEY_ID + "=" + ID, null);
        } catch (Exception e) {
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

    public void insertTestData(SQLiteDatabase db) {

        String mCSVfile = "data.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split("\\|");
                System.out.println(Arrays.toString(colums));
                if (colums.length != 6) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                ContentValues cv = new ContentValues(5);
                cv.put(MyDBAdapter.KEY_NAME, colums[0].trim());
                cv.put(MyDBAdapter.KEY_SURNAME, colums[1].trim());
                cv.put(MyDBAdapter.KEY_NUMBER, colums[2].trim());
                cv.put(MyDBAdapter.KEY_EMAILID, colums[3].trim());
                cv.put(MyDBAdapter.KEY_CITY, colums[4].trim());
                cv.put(MyDBAdapter.KEY_ADDRESS, colums[5].trim());
                db.insert(TABLE_NAME, null, cv);
                System.out.println(cv.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public void updateTable(String data) {

        String[] dataLines = data.split("\\n");
        String line = "";
        db.beginTransaction();

        for (int iter = 0; iter < dataLines.length; iter++) {
            line = dataLines[iter];

            Log.i(TAG, line);
            String[] colums = line.split("\\|");
            System.out.println(Arrays.toString(colums));
            if (colums.length != 6) {
                Log.d("CSVParser", "Skipping Bad CSV Row");
                continue;
            }
            ContentValues cv = new ContentValues(5);
            cv.put(MyDBAdapter.KEY_NAME, colums[0].trim());
            cv.put(MyDBAdapter.KEY_SURNAME, colums[1].trim());
            cv.put(MyDBAdapter.KEY_NUMBER, colums[2].trim());
            cv.put(MyDBAdapter.KEY_EMAILID, colums[3].trim());
            cv.put(MyDBAdapter.KEY_CITY, colums[4].trim());
            cv.put(MyDBAdapter.KEY_ADDRESS, colums[5].trim());
            db.insert(TABLE_NAME, null, cv);
            System.out.println(cv.toString());
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void truncateTable() {
        String sql = "Delete from " + TABLE_NAME;
        db.execSQL(sql);
    }

}