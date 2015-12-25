package in.saurabhjinturkar.telephonedirectory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import in.saurabhjinturkar.telephonedirectory.bean.News;
import in.saurabhjinturkar.telephonedirectory.database.services.NewsService;

import java.util.Date;
import java.util.List;

/**
 * Created by Saurabh on 8/19/2015.
 */
public class NewsListAdapter extends ArrayAdapter<News> {

    Context context;
    List<News> newsList;

    public NewsListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        this.context = context;
        NewsService service = new NewsService(context);
        this.newsList = service.getAllNews();
        this.addAll(newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.two_line_list_item, null);
        }

        News item = getItem(position);
        if (item != null) {

            TextView newsTitleView = (TextView) view.findViewById(android.R.id.text1);
            TextView newsBodyView = (TextView) view.findViewById(android.R.id.text2);

            newsTitleView.setPadding(16, 16, 16, 16);
            newsBodyView.setPadding(16, 16, 16, 16);

            if (newsTitleView != null) {
                newsTitleView.setText(item.getTitle());
            }
            Date date = new Date();
            date.setTime(Long.parseLong(item.getTime()));
            if (newsBodyView != null) {
                newsBodyView.setText(item.getBody() + "\n" + date.toString());
            }
        }

        return view;
    }
}
