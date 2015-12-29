package in.saurabhjinturkar.telephonedirectory.database.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import in.saurabhjinturkar.telephonedirectory.bean.News;
import in.saurabhjinturkar.telephonedirectory.database.NewsDBAdapter;

/**
 * Created by Saurabh on 8/19/2015.
 */
public class NewsService {
    private NewsDBAdapter newsDBAdapter;
    private ContentValues v;

    public NewsService(Context context) {
        newsDBAdapter = new NewsDBAdapter(context);
    }

    /**
     * Insert a new entry
     */
    public boolean insertNews(String title, String body, String time) {
        v = new ContentValues();
        v.put(NewsDBAdapter.KEY_TITLE, title);
        v.put(NewsDBAdapter.KEY_BODY, body);
        v.put(NewsDBAdapter.KEY_TIME, time);

        return newsDBAdapter.executeInsert(v);
    }

    /**
     * Delete an entry
     */
    public boolean deleteNews(int ID) {
        return newsDBAdapter.executeDeletion(ID);
    }

    public boolean deleteAllNews() {
        newsDBAdapter.truncate();
        return true;
    }

    /**
     * Get single contact
     */
    public News getNews(int ID) {
        String query = "SELECT * FROM " + NewsDBAdapter.TABLE_NAME + " WHERE " + NewsDBAdapter.KEY_ID + "=" + ID + " ORDER BY " + NewsDBAdapter.KEY_TIME + " DESC;";
        Cursor c = newsDBAdapter.getCursor(query);
        News news = null;
        while (c.moveToNext()) {
            news = new News(
                    c.getString(c.getColumnIndex(NewsDBAdapter.KEY_ID)),
                    c.getString(c.getColumnIndex(NewsDBAdapter.KEY_TITLE)),
                    c.getString(c.getColumnIndex(NewsDBAdapter.KEY_BODY)),
                    c.getString(c.getColumnIndex(NewsDBAdapter.KEY_TIME)));
        }
        return news;
    }

    /**
     * Get all news
     */
    public ArrayList<News> getAllNews() {
        String query = "SELECT * FROM " + NewsDBAdapter.TABLE_NAME + " ORDER BY " + NewsDBAdapter.KEY_TIME + " DESC;";
        Cursor c = newsDBAdapter.getCursor(query);
        ArrayList<News> newsArrayList = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {
                News contact = new News(c.getString(c.getColumnIndex(NewsDBAdapter.KEY_ID)),
                        c.getString(c.getColumnIndex(NewsDBAdapter.KEY_TITLE)),
                        c.getString(c.getColumnIndex(NewsDBAdapter.KEY_BODY)),
                        c.getString(c.getColumnIndex(NewsDBAdapter.KEY_TIME)));
                newsArrayList.add(contact);
            }
        }
        return newsArrayList;
    }
}
