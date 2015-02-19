package com.stefanomunarini.telephonedirectory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stefanomunarini.telephonedirectory.bean.Contact;

import java.util.List;

/**
 * Created by Stefano on 2/18/15.
 */
public class MyListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> items;

    public MyListAdapter(Context context, int textViewResourceId, List<Contact> items) {
        super(context, textViewResourceId, items);

        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.two_line_list_item, null);
        }

        Contact item = getItem(position);
        if (item!= null) {

            TextView nameSurnameView = (TextView) view.findViewById(android.R.id.text1);
            TextView numberView = (TextView) view.findViewById(android.R.id.text2);

            nameSurnameView.setPadding(16,16,16,16);
            numberView.setPadding(16,16,16,16);

            if (nameSurnameView != null) {
                nameSurnameView.setText(item.getName() + " " + item.getSurname());
            }

            if (numberView != null) {
                numberView.setText(item.getNumber());
            }
        }

        return view;
    }
}