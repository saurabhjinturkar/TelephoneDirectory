package in.saurabhjinturkar.telephonedirectory;

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

import com.stefanomunarini.telephonedirectory.R;

import in.saurabhjinturkar.telephonedirectory.bean.Contact;
import in.saurabhjinturkar.telephonedirectory.database.services.ContactService;

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

    private TextView name;
    private TextView contactnumber;
    private TextView emailid;
    private TextView city;
    private TextView address;

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

        name = (TextView) rootView.findViewById(R.id.contact_name_surname);
        contactnumber = (TextView) rootView.findViewById(R.id.contact_number);
        emailid = (TextView) rootView.findViewById(R.id.contact_emailid);
        city = (TextView) rootView.findViewById(R.id.contact_city);
        address = (TextView) rootView.findViewById(R.id.contact_address);

        // Show the content as text in a TextView.
        if (mContact != null) {
            name.setText(mContact.getName() + " " + mContact.getSurname());
            contactnumber.setText(mContact.getNumber());

            contactnumber.setOnClickListener(this);
            ((TextView) rootView.findViewById(R.id.contact_number)).setMovementMethod(LinkMovementMethod.getInstance());

            emailid.setText(mContact.getEmailid());
            city.setText(mContact.getCity());

            address.setText(mContact.getAddress() + "," + mContact.getCity());
            address.setOnClickListener(this);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case R.id.action_edit:
                Intent intent = new Intent(getActivity(), NewContact.class);
                intent.putExtra(MyDBAdapter.KEY_ID, ContactDetailFragment.mContact.getId());
                startActivity(intent);*/
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
        switch (id) {
            case R.id.contact_number:
                String number = "tel:" + mContact.getNumber().toString().trim().substring(1);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
                break;
            case R.id.contact_address:
                System.out.println("Address Clicked");
                TextView textView = (TextView) v;
                String map = "http://maps.google.co.in/maps?q=" + textView.getText().toString().replace(" ", "+");
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(i);
                break;
        }
    }
}
