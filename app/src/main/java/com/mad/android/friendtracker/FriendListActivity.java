package com.mad.android.friendtracker;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.helper.ItemTouchHelper;
import model.FriendTracker;
import java.util.ArrayList;

import static com.mad.android.friendtracker.FriendListActivity.onListItemSwiped;

public class FriendListActivity extends AppCompatActivity implements FriendAdapter.ListItemClickListener{

    private static int num_list_items = model.FriendTracker.listCount();
    private FriendAdapter mFriendAdapter;
    private RecyclerView mFriendList;

    private static final String EXTRA_ID = "EXTRA_ID";
//    private static final String EXTRA_EMAIL = "EXTRA_EMAIL";
 //   private static final String EXTRA_BIRTHDAY = "EXTRA_BIRTHDAY";

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mFriendList);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = FriendTracker.getFriendArrayList().get(clickedItemIndex).getId();
 //       String email = FriendTracker.getFriendArrayList().get(clickedItemIndex).getEmail();
 //       String birthday = FriendTracker.getFriendArrayList().get(clickedItemIndex).getBirthday();

        Context context = FriendListActivity.this;
        Class editFriend = EditFriendActivity.class;
        Intent startEditFriend = new Intent(context,editFriend);
        startEditFriend.putExtra(EXTRA_ID, id);
   //     startEditFriend.putExtra(EXTRA_EMAIL,email);
   //     startEditFriend.putExtra(EXTRA_BIRTHDAY, birthday);
   //     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //        getWindow().setEnterTransition(new Fade(Fade.IN));
    //        getWindow().setEnterTransition(new Fade(Fade.OUT));
         //    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
         //           new Pair<View, String>(findViewById(R.id.edit_name)),name),
         //           new Pair<View, String>(findViewById(R.id.edit_email),email),
         //           new Pair<View, String>(findViewById(R.id.edit_birthday),birthday));

          //  startActivity(startEditFriend, options.toBundle());
   //     } else {
            startActivity(startEditFriend);
    //    }
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
            startActivityForResult(startEditFriend,0);

        }
        return super.onOptionsItemSelected(item);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                onListItemSwiped(position);
            }
        };
        return simpleItemTouchCallback;
    }
    public static void onListItemSwiped (int position){
            FriendTracker.deleteFriend(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

     //   super.onActivityResult(requestCode, resultCode, data);
        this.mFriendAdapter.notifyDataSetChanged();
    }
}




