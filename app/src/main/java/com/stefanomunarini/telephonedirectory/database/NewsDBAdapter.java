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
 * Created by Saurabh on 8/19/2015.
 */
public class NewsDBAdapter extends SQLiteOpenHelper implements DBAdapterInterface {

    //Database path in device
    public static final String DB_PATH = "/data/data/" + ContactListActivity.class.getPackage().getName() + "/databases/telephonedirectory.db";

    //TABLE NAME
    public static final String TABLE_NAME = "news";
    //ATTRIBUTE NAME
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_TIME = "time";

    // CREATE TABLE QUERY
    private static final String TABLE_NEWS_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TITLE + " VARCHAR NOT NULL, " +
                    KEY_BODY + " VARCHAR NOT NULL, " +
                    KEY_TIME + " VARCHAR NOT NULL);";
    //Database name
    private static final String DATABASE_NAME = "telephonedirectory.db";
    //Database version
    private static final int SCHEMA_VERSION = 1;
    // Database instance
    public static SQLiteDatabase db;
    // TAG used for Logs
    public static String TAG = "NewsDBAdapter";
    private Context context;

    public NewsDBAdapter(Context context) {
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
        database.execSQL(TABLE_NEWS_CREATE);

//        insertTestData(database);
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

    public void truncate() {
        String sql = "truncate table if exists " + TABLE_NAME;
        db.execSQL(sql);
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
                cv.put(NewsDBAdapter.KEY_TITLE, colums[0].trim());
                cv.put(NewsDBAdapter.KEY_BODY, colums[1].trim());
                cv.put(NewsDBAdapter.KEY_TIME, colums[2].trim());
                db.insert(TABLE_NAME, null, cv);
                System.out.println(cv.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}