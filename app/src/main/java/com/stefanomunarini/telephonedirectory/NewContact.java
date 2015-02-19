package com.stefanomunarini.telephonedirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.stefanomunarini.telephonedirectory.bean.Contact;
import com.stefanomunarini.telephonedirectory.database.MyDBAdapter;
import com.stefanomunarini.telephonedirectory.database.services.ContactService;

/**
 * Created by Stefano on 2/18/15.
 */
public class NewContact extends ActionBarActivity {

    /**
     * The EditTexts from where we retrieve datas
     */
    private EditText editText_name, editText_surname,
            editText_international_prefix, editText_prefix,
            editText_number;

    private int id;

    private boolean updateContact = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        editText_name = (EditText) findViewById(R.id.edittext_name);
        editText_surname = (EditText) findViewById(R.id.edittext_surname);
        editText_international_prefix = (EditText) findViewById(R.id.edittext_internationalprefix);
        editText_prefix = (EditText) findViewById(R.id.edittext_prefix);
        editText_number = (EditText) findViewById(R.id.edittext_number);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            updateContact = true;

            setTitle(getResources().getText(R.string.edit_contact));

            id = Integer.parseInt(extras.getString(MyDBAdapter.KEY_ID));

            ContactService contactService = new ContactService(this);
            Contact contact = contactService.getContact(id);

            if (contact!=null) {
                name = contact.getName();
                surname = contact.getSurname();
                number = contact.getNumber();

                editText_name.setText(name);
                editText_surname.setText(surname);

                int first_space = number.indexOf(" ");
                editText_international_prefix.setText(number.substring(1, first_space));
                int second_space = number.indexOf(" ", first_space + 1);
                editText_prefix.setText(number.substring(first_space + 1, second_space));
                editText_number.setText(number.substring(second_space + 1, number.length() - 1));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                navigateUpTo(new Intent(this, ContactListActivity.class));
                return true;
            case R.id.action_save:
                if (validateForms()) {
                    Intent intent = new Intent(this, ContactListActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String name, surname, international_prefix, prefix, number;
    private boolean validateForms() {
        name = editText_name.getText().toString().substring(0,1).toUpperCase() + editText_name.getText().toString().substring(1);
        surname = editText_surname.getText().toString().substring(0,1).toUpperCase() + editText_surname.getText().toString().substring(1);
        international_prefix = editText_international_prefix.getText().toString();
        prefix = editText_prefix.getText().toString();
        number = editText_number.getText().toString();

        if (checkForNullValues()) {
            ContactService contactService = new ContactService(this);
            String formattedNumber = "+" + international_prefix + " " + prefix + " " + number;
            if (!updateContact) {
                contactService.insertContact(name, surname, formattedNumber);
            } else {
                contactService.updateContact(id,name,surname,formattedNumber);
            }
            Toast.makeText(this, getResources().getText(R.string.contact_saved), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForNullValues() {
        Toast toast;
        if (name.isEmpty() || surname.isEmpty() || international_prefix.isEmpty() || prefix.isEmpty()){
            toast = Toast.makeText(this, getResources().getText(R.string.empty_field), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else {
            if (number.length()<6){
                toast = Toast.makeText(this,getResources().getText(R.string.incorrect_number),Toast.LENGTH_SHORT);
                toast.show();
                return false;
            } else {
                return true;
            }
        }


    }

}