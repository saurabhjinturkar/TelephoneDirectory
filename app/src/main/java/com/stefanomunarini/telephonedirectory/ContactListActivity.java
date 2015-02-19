package com.stefanomunarini.telephonedirectory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;


/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ContactListFragment} and the item details
 * (if present) is a {@link ContactDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ContactListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ContactListActivity extends ActionBarActivity
        implements ContactListFragment.Callbacks, View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     * Floating button (used to add an entry)
     */
    FloatingActionButton fab;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        context = this;

        if (findViewById(R.id.contact_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ContactListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.contact_list))
                    .setActivateOnItemClick(true);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    /**
     * Callback method from {@link ContactListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            //arguments.putSerializable("contact", DummyContact.CONTACTS.get(realPosition));
            arguments.putString(ContactDetailFragment.ARG_ITEM_ID, id);

            ContactDetailFragment fragment = new ContactDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ContactDetailActivity.class);
            //detailIntent.putExtra("contact", DummyContact.CONTACTS.get(realPosition));
            detailIntent.putExtra(ContactDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                Intent intent = new Intent(this, NewContact.class);
                startActivity(intent);
                finish();
                break;
            default: break;
        }
    }
}
