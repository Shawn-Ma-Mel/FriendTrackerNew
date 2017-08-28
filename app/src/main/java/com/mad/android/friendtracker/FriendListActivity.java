package com.mad.android.friendtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity implements FriendAdapter.ListItemClickListener{

    private static int num_list_items = model.FriendTracker.listCount();
    private FriendAdapter mFriendAdapter;
    private RecyclerView mFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mFriendList = (RecyclerView) findViewById(R.id.friend_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mFriendList.setLayoutManager(layoutManager);

        ArrayList<model.Friend> savedFriends = model.FriendTracker.getFriendArrayList();
        mFriendAdapter = new FriendAdapter(savedFriends,this);
        mFriendList.setAdapter(mFriendAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ArrayList<model.Friend> savedFriends = model.FriendTracker.getFriendArrayList();
//        String name = savedFriends.get(clickedItemIndex+1).getName();
//       String email = savedFriends.get(clickedItemIndex+1).getEmail();
        Context context = FriendListActivity.this;
        Class editFriend = EditFriendActivity.class;
        Intent startEditFriend = new Intent(context,editFriend);
        startEditFriend.putExtra("name","email");
        startActivity(startEditFriend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_friend) {
            Context context = FriendListActivity.this;
            Class destinationActivity = EditFriendActivity.class;
            Intent startEditFriend = new Intent(context,destinationActivity);
            startActivity(startEditFriend);

        }
        return super.onOptionsItemSelected(item);
    }


}
