package com.stefanomunarini.telephonedirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stefanomunarini.telephonedirectory.bean.Contact;
import com.stefanomunarini.telephonedirectory.database.MyDBAdapter;
import com.stefanomunarini.telephonedirectory.database.services.ContactService;

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a {@link ContactListActivity}
 * in two-pane mode (on tablets) or a {@link ContactDetailActivity}
 * on handsets.
 */
public class ContactDetailFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    public static Contact mContact;
    private ContactService contactService;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ContactService contactService = new ContactService(getActivity());

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            mContact = contactService.getContact(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_detail, container, false);

        // Show the content as text in a TextView.
        if (mContact != null) {
            ((TextView) rootView.findViewById(R.id.contact_name_surname)).setText(mContact.getName() + " " + mContact.getSurname());
            ((TextView) rootView.findViewById(R.id.contact_number)).setText(mContact.getNumber());

            rootView.findViewById(R.id.contact_number).setOnClickListener(this);
            ((TextView)rootView.findViewById(R.id.contact_number)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact_detail, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getActivity(), NewContact.class);
                intent.putExtra(MyDBAdapter.KEY_ID, ContactDetailFragment.mContact.getId());
                startActivity(intent);
            /*case R.id.action_remove:
                contactService = new ContactService(getActivity());
                contactService.deleteContact(Integer.parseInt(mContact.getId()));
                ContactListFragment.myListAdapter.notifyDataSetChanged();
                ContactListFragment.populateListView();*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.contact_number:
                String number = "tel:" + mContact.getNumber().toString().trim().substring(1);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
        }
    }
}
