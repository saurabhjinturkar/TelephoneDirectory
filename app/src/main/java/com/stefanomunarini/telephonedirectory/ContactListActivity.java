package com.stefanomunarini.telephonedirectory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stefanomunarini.telephonedirectory.bean.ContactList;
import com.stefanomunarini.telephonedirectory.database.MyDBAdapter;

import java.util.HashMap;
import java.util.Map;


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
        implements ContactListFragment.Callbacks, View.OnClickListener, SearchView.OnQueryTextListener {

    public static Context context;
    /**
     * Floating button (used to add an entry)
     */
//    FloatingActionButton fab;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private String version;
    private String url = "http://saurabhjinturkar.in/gcm/update.php";

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

        version = readVersion();
        Log.i("ContactListActivity", "Version from shared prefs " + version);
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.update_contact:

                this.version = readVersion();

                // Start progress dialog
                final ProgressDialog progressDialog = new ProgressDialog(ContactListActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Updating..");

                RequestQueue queue = Volley.newRequestQueue(context);

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("UPDATE_RESPONSE", response);

                                JsonParser parser = new JsonParser();
                                JsonElement jsonElement = parser.parse(response);

                                JsonObject obj;
                                if (jsonElement.isJsonArray()) {
                                    obj = (JsonObject) ((JsonArray) jsonElement).get(0);
                                } else {
                                    obj = (JsonObject) jsonElement;
                                }

                                if (obj.get("code").getAsInt() == 102) {
                                    updateVersion(obj.get("version").getAsString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                                    builder.setMessage("Your contacts are already updated!")
                                            .setTitle("Already updated..").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    progressDialog.dismiss();
                                    dialog.show();
                                    return;
                                }

                                if (obj.get("code").getAsInt() == 103) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);

                                    builder.setMessage("You are not authorized to access the contacts. Please contact admin.")
                                            .setTitle("Authorization failure!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    progressDialog.dismiss();
                                    dialog.show();
                                    return;
                                }

                                String contacts = obj.get("contacts").getAsString();

                                MyDBAdapter adapter = new MyDBAdapter(getBaseContext());
                                adapter.open();
                                adapter.truncateTable();
                                adapter.updateTable(contacts);
                                adapter.close();

                                ContactList contactList = new ContactList(getBaseContext());
                                ContactListFragment.myListAdapter.clear();
                                ContactListFragment.myListAdapter.addAll(contactList);
                                ContactListFragment.myListAdapter.notifyDataSetChanged();

                                updateVersion(obj.get("version").getAsString());

                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);

                                builder.setMessage("Your contacts have been successfully updated!")
                                        .setTitle("Successfully updated..").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                AlertDialog alertDialog = new AlertDialog.Builder(ContactListActivity.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Update can not be completed. Please try again.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        System.out.println("####################VERSION TO BE SENT = " + version);
                        params.put("version", version);
                        params.put("auth", "bHCvcEhEff42EZK2");
                        return params;
                    }


                };
                // Add the request to the RequestQueue.
                queue.getCache().clear();
                queue.add(stringRequest);

                progressDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateVersion(String version) {

        Log.i("UpdateVersion", "Version to store: " + version);
        // Update version

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("version", version);
        editor.commit();
    }

    private String readVersion() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String version = settings.getString("version", "0");
        return version;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d("Filter_Contact", "onQueryTextSubmit.. " + s);
        ContactListFragment.myListAdapter.getFilter().filter(s);
        ContactListFragment.myListAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("Filter_Contact", "onQueryTextChange.. " + s);
        ContactListFragment.myListAdapter.getFilter().filter(s);
        ContactListFragment.myListAdapter.notifyDataSetChanged();
        return false;
    }
}
