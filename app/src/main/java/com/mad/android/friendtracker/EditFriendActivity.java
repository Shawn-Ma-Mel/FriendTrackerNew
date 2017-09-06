package com.mad.android.friendtracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

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
    ImageView photo = null;
    ImageUtils imageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        photo = (ImageView) findViewById(R.id.friend_photo);
        imageUtils = new ImageUtils(this);
        Button done = (Button) findViewById(R.id.done);
        editName = (EditText) findViewById(R.id.edit_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editBirthday = (EditText) findViewById(R.id.edit_birthday);
        editBirthday.setInputType(InputType.TYPE_NULL);
        editBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(EditFriendActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            editBirthday.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDialog();
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
                finish();
            }
        });

        Button addFriendFromContact = (Button)findViewById(R.id.addFriendFromContract);
        addFriendFromContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });
    }
    private void chooseDialog() {
        new AlertDialog.Builder(this)//
                .setTitle("choose photo")//

                .setNegativeButton("Photos", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        imageUtils.byAlbum();

                    }
                })

                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否存在SD卡
                            imageUtils.byCamera();
                        }
                    }
                }).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("-->requestCode:" + requestCode + "-->resultCode:"
                + resultCode);

        switch (requestCode) {
            case ImageUtils.ACTIVITY_RESULT_CAMERA: // 拍照
                try {
                    if (resultCode == -1) {
                        imageUtils.cutImageByCamera();
                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (imageUtils.picFile != null) {
                            imageUtils.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ImageUtils.ACTIVITY_RESULT_ALBUM:
                try {
                    if (resultCode == -1) {
                        Bitmap bm_icon = imageUtils.decodeBitmap();
                        if (bm_icon != null) {
                            photo.setImageBitmap(bm_icon);
                        }
                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (imageUtils.picFile != null) {
                            imageUtils.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_CONTACTS) {
//            if (resultCode == RESULT_OK) {
//                ContactDataManager contactsManager = new ContactDataManager(this, data);
//                String name = "";
//                String email = "";
//                try {
//                    name = contactsManager.getContactName();
//                    email = contactsManager.getContactEmail();
//                } catch (ContactDataManager.ContactQueryException e) {
//                };
//                  editName = (EditText) findViewById(R.id.edit_name);
//                  editEmail = (EditText) findViewById(R.id.edit_email);
//                  editName.setText(name);
//                  editEmail.setText(email);
//            }
//        }
//    }
}
