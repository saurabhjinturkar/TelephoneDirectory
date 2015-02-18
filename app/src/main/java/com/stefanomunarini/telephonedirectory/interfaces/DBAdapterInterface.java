package com.stefanomunarini.telephonedirectory.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Stefano on 2/18/15.
 */
public interface DBAdapterInterface {

    /**
     *  Insert an entry
     *  @Param: ContentValues v
     *  @Return: boolean
     */
    public boolean executeInsert(ContentValues v);

    /**
     *  Update an entry
     *  @Param: ContentValues v, Object ID
     *  @Return: boolean
     */
    public boolean executeUpdate(ContentValues v, int ID);

    /**
     *  Delete an entry
     *  @Param: int ID
     *  @Return: boolean
     */
    public boolean executeDeletion(int ID);

    /**
     *  Delete a table
     *  @Param: String TABLE_NAME
     *  @Return: boolean
     */
    /*public boolean executeTableDeletion(String TABLE_NAME);*/

    /**
     *  Get cursor
     *  @Param: String query
     *  @Return: Cursor
     */
    public Cursor getCursor(String query);
}