package com.stefanomunarini.telephonedirectory.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.stefanomunarini.telephonedirectory.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefano on 2/18/15.
 */
public class MyListAdapter extends ArrayAdapter<Contact> implements Filterable {

    private Context context;
    private List<Contact> contactList;

    public MyListAdapter(Context context, int textViewResourceId, List<Contact> items) {
        super(context, textViewResourceId, items);

        this.context = context;
        this.contactList = items;
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

    private Contact_Filter contact_filter;
    @Override
    public Filter getFilter() {

        if (contact_filter == null)
            contact_filter = new Contact_Filter(contactList);

        return contact_filter;
    }

    /*
     * Class used to filter the Clinical Folder
     * based on user input in search bar
     */
    private class Contact_Filter extends Filter {

        private List<Contact> mContact;

        /*
         * Constructor
         * @param: clinicalFolder
         */
        public Contact_Filter(List<Contact> clinicalFolder){
            this.mContact = clinicalFolder;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            Log.d("Filter_Contact", "Finding... " + constraint);

            if (constraint == null || constraint.length() == 0) {
                // Don't do filter, because the search word is null
                results.values = mContact;
                results.count = mContact.size();
            } else {
                // We perform filtering operation
                ArrayList<Contact> mContactList = new ArrayList<Contact>();

                for (Contact contact : mContact) {

                    String name = contact.getName().toUpperCase();
                    String surname = contact.getSurname().toUpperCase();
                    String number = contact.getNumber();
                    if (name.contains(constraint.toString().toUpperCase()) || surname.contains(constraint.toString().toUpperCase()) || number.contains(constraint.toString())) {
                        mContactList.add(contact);
                    }
                }

                results.values = mContactList;
                results.count = mContactList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Now we have to inform the adapter about the new list filtered
            if (filterResults.count == 0) {
                notifyDataSetInvalidated();
                Log.d("Filter_Contact","notifyDataSetInvalidated");
            }
            else {
                contactList = (ArrayList<Contact>) filterResults.values;
                Log.d("Filter_Contact", "notifyDataSetChanged");
                notifyDataSetChanged();
            }
        }
    }
}