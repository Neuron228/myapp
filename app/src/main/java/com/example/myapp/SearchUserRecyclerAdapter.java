package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SearchUserRecyclerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<ApplicationAccount> UsersList;



    public SearchUserRecyclerAdapter(Context context, ArrayList<ApplicationAccount> UsersList) {
        this.inflater = LayoutInflater.from(context);
        this.UsersList = UsersList;
    }
    public int getCount() {
        return UsersList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final UserModelViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_user_recycler_row, parent, false);
            viewHolder = new UserModelViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserModelViewHolder) convertView.getTag();
        }
        final ApplicationAccount User = UsersList.get(position);


        viewHolder.nickNameText.setText("@"+User.getNickname());
        viewHolder.usernameText.setText(User.getFirstName() +" "+ User.getLastName());

        return convertView;
    }




    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView nickNameText;
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            nickNameText = itemView.findViewById(R.id.nickname_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}

