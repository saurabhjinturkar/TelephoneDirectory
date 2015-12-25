package in.saurabhjinturkar.telephonedirectory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import in.saurabhjinturkar.telephonedirectory.Adapter.NewsListAdapter;

import com.stefanomunarini.telephonedirectory.R;

import in.saurabhjinturkar.telephonedirectory.database.NewsDBAdapter;


public class NewsActivity extends ActionBarActivity {

    ListView newslist;
    NewsListAdapter newsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newslist = (ListView) findViewById(R.id.news_listView);

        newsListAdapter = new NewsListAdapter(this);
        newslist.setAdapter(newsListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            NewsDBAdapter adapter = new NewsDBAdapter(this);
            adapter.truncate();
            adapter.close();
            newsListAdapter = new NewsListAdapter(this);
            newslist.setAdapter(newsListAdapter);
            newsListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
