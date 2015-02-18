package com.stefanomunarini.telephonedirectory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stefanomunarini.telephonedirectory.dummy.DummyContent;

import java.util.List;

/**
 * Created by Stefano on 2/18/15.
 */
public class MyListAdapter extends ArrayAdapter<DummyContent.Contact> {

    private Context context;

    public MyListAdapter(Context context, int textViewResourceId, List<DummyContent.Contact> items) {
        super(context, textViewResourceId, items);

        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.two_line_list_item, null);
        }

        DummyContent.Contact item = getItem(position);
        if (item!= null) {

            TextView nameSurnameView = (TextView) view.findViewById(android.R.id.text1);
            TextView numberView = (TextView) view.findViewById(android.R.id.text2);

            nameSurnameView.setPadding(16,16,16,16);
            numberView.setPadding(16,16,16,16);

            if (nameSurnameView != null) {
                nameSurnameView.setText(item.name + " " + item.surname);
            }

            if (numberView != null) {
                numberView.setText(item.number);
            }
        }

        return view;
    }
}