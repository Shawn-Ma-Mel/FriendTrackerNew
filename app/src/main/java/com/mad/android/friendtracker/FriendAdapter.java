package com.mad.android.friendtracker;

import android.content.Context;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import model.Friend;

/**
 * Created by shawn on 2017/8/16.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>{


    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }
    private ListItemClickListener mOnclickListener;

    class FriendAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listItemName;
        TextView listItemEmail;
        ImageButton listItemImage;
        public FriendAdapterViewHolder(View view) {
            super(view);
            listItemName = (TextView)view.findViewById(R.id.friend_name);
            listItemEmail = (TextView)view.findViewById(R.id.friend_email);
            listItemImage = (ImageButton) view.findViewById(R.id.friend_photo);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnclickListener.onListItemClick(clickedPosition);

        }
        }


    private ArrayList<model.Friend> friends;
    public FriendAdapter(ArrayList<model.Friend> list,ListItemClickListener listener) {
        friends = list;
        mOnclickListener = listener;
    }

    @Override
    public int getItemCount(){
       return friends.size();
    }

    @Override
    public void onBindViewHolder(FriendAdapterViewHolder holder, int position) {
            Friend friend = friends.get(position);
            String name = friend.getName();
            String email = friend.getEmail();
            holder.listItemName.setText(name);
            holder.listItemEmail.setText(email);
    }

    @Override
    public FriendAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.friend_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttatchedToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttatchedToParentImmediately);
        FriendAdapterViewHolder fViewHolder = new FriendAdapterViewHolder(view);
        return fViewHolder;
    }
}
