package com.mad.android.friendtracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button chooseFriend;
    private Button chooseMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS},1);
        chooseFriend = (Button)findViewById(R.id.friend);
        chooseMeeting = (Button)findViewById(R.id.meeting);

        chooseFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity.this;
                Class destionationActivity = FriendListActivity.class;
                Intent startFriendList = new Intent(context,destionationActivity);
                startActivity(startFriendList);
            }
        });

    }
}
