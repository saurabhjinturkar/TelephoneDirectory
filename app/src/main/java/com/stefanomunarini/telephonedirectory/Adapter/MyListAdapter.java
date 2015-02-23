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

import com.stefanomunarini.telephonedirectory.ContactListFragment;
import com.stefanomunarini.telephonedirectory.bean.Contact;
import com.stefanomunarini.telephonedirectory.bean.ContactList;

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

        Log.d("Filter_Contact", "getFilter");

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
        public Contact_Filter(List<Contact> contacts){
            this.mContact = contacts;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            Log.d("Filter_Contact", "Finding... " + constraint);

            ContactList contactList1;
            if (constraint == null || constraint.length() == 0) {
                Log.d("Filter_Contact", "constraint == null || constraint.length() == 0... ");
                Log.d("Filter_Contact", "mContact size... " + mContact.size());
                contactList1 = new ContactList(getContext());
                // Don't do filter, because the search word is null
                results.values = contactList1;
                results.count = contactList1.size();
            } else {
                contactList1 = new ContactList(getContext());
                // We perform filtering operation
                ContactList mContactList = new ContactList();

                for (Contact contact : contactList1) {

                    String name = contact.getName().toUpperCase();
                    String surname = contact.getSurname().toUpperCase();
                    String number = contact.getNumber();
                    if (name.contains(constraint.toString().toUpperCase()) || surname.contains(constraint.toString().toUpperCase()) || number.contains(constraint.toString())) {
                        mContactList.add(contact);
                        Log.d("Filter_Contact", "Found... " + contact.getName() + " " + contact.getSurname());
                    } else {
                        mContactList.remove(contact);
                        Log.d("Filter_Contact", "No matches found..Deleting contact from list..");
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
                ContactListFragment.contactList.clear();
                notifyDataSetInvalidated();
                Log.d("Filter_Contact","notifyDataSetInvalidated");
            }
            else {
                ContactListFragment.contactList.clear();
                ContactListFragment.contactList.addAll((ContactList) filterResults.values);
                Log.d("Filter_Contact", "notifyDataSetChanged");
                notifyDataSetChanged();
            }
        }
    }
}