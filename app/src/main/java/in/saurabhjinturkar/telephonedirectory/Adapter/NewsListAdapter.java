package in.saurabhjinturkar.telephonedirectory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.saurabhjinturkar.telephonedirectory.bean.News;
import in.saurabhjinturkar.telephonedirectory.database.services.NewsService;

/**
 * Created by Saurabh on 8/19/2015.
 */
public class NewsListAdapter extends ArrayAdapter<News> {

    Context context;
    List<News> newsList;

    public static final List<Long> times = Arrays.asList(
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));
    public static final List<String> timesString = Arrays.asList("hour", "minute", "second");

    public static String toDuration(long duration) {

        long now = Calendar.getInstance().getTimeInMillis();
        if (now - duration > TimeUnit.DAYS.toMillis(1)) {
            Date date = new Date();
            date.setTime(duration);
            return date.toString();
        }

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < times.size(); i++) {
            Long current = times.get(i);
            long temp = (now - duration) / current;
            if (temp > 0) {
                res.append(temp).append(" ").append(timesString.get(i)).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }
        }
        if ("".equals(res.toString()))
            return "0 second ago";
        else
            return res.toString();
    }

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

            String time = toDuration(Long.parseLong(item.getTime()));
            if (newsBodyView != null) {
                newsBodyView.setText(item.getBody() + "\n" + time);
            }
        }

        return view;
    }
}
