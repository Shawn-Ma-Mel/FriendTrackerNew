package com.mad.android.friendtracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class EditFriendActivity extends AppCompatActivity {

    protected static final int PICK_CONTACTS = 100;
    EditText editName;
    EditText editEmail;
    EditText editBirthday;
    String id;
    String getId;
    String nameOld;
    String emailOld;
    String birthdayOld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        Button done = (Button)findViewById(R.id.done);
        editName = (EditText) findViewById(R.id.edit_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editBirthday = (EditText)findViewById(R.id.edit_birthday);
        editBirthday.setInputType(InputType.TYPE_NULL);
        editBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(EditFriendActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            editBirthday.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });




        getId = this.getIntent().getStringExtra("EXTRA_ID");
        if (getId == null || getId.equals("")) {
            id = model.FriendTracker.getRandomString();
        } else {
            id = getId;
            for (int i = 0; i < model.FriendTracker.getFriendArrayList().size(); i++) {
                if (model.FriendTracker.getFriendArrayList().get(i).getId().equals(getId)) {

                    nameOld = model.FriendTracker.getFriendArrayList().get(i).getName();
                    emailOld = model.FriendTracker.getFriendArrayList().get(i).getEmail();
                    birthdayOld = model.FriendTracker.getFriendArrayList().get(i).getBirthday();
                    editName.setText(nameOld);
                    editEmail.setText(emailOld);
                    editBirthday.setText(birthdayOld);
                    model.FriendTracker.getFriendArrayList().remove(i);
                }
            }
        }

        editBirthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(EditFriendActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        editBirthday.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String nameSave = editName.getText().toString();
                String emailSave = editEmail.getText().toString();
                String birthdaySave = editBirthday.getText().toString();
                model.FriendTracker.addFriend(id,nameSave,emailSave,birthdaySave);
      //          Context context = EditFriendActivity.this;
         //       Class destinationActivity = FriendListActivity.class;
        //        Intent saveFriendIntent = new Intent(context,destinationActivity);
      //          startActivity(saveFriendIntent);

                finish();
            }
        });

        Button addFriendFromContact = (Button)findViewById(R.id.addFriendFromContract);
        addFriendFromContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //          Context context = EditFriendActivity.this;
                //         Class destionationActivity = FriendListActivity.class;
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new ContactDataManager(this, data);
                String name = "";
                String email = "";
//              String birthday = "";

                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                } catch (ContactDataManager.ContactQueryException e) {
                };

                  editName = (EditText) findViewById(R.id.edit_name);
                  editEmail = (EditText) findViewById(R.id.edit_email);
                  editName.setText(name);
                  editEmail.setText(email);
    //            birthday = editBirthday.getText().toString();
    //
   //             model.FriendTracker.addFriend(id,name,email,birthday);

            }
        }
    }
}
