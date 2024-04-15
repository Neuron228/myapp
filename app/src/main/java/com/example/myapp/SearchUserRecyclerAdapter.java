package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SearchUserRecyclerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<ApplicationAccount> UsersList;
    int resource;
    Context context;



    public SearchUserRecyclerAdapter(Context context,int resource ,ArrayList<ApplicationAccount> UsersList) {
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
        this.UsersList = UsersList;
    }

    public void setFilteredList(ArrayList<ApplicationAccount> filteredList){
        this.UsersList = filteredList;
        notifyDataSetChanged();
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
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new UserModelViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserModelViewHolder) convertView.getTag();
        }
        final ApplicationAccount User = UsersList.get(position);

        Typeface typeface = ResourcesCompat.getFont(convertView.getContext(), R.font.nunito_extrabold);
        Typeface typeface1 = ResourcesCompat.getFont(convertView.getContext(), R.font.nunito_bold_italic);

        Glide.with(convertView).load(User.getPictureUri()).into(viewHolder.profilePic);

        viewHolder.nickNameText.setTypeface(typeface);
        viewHolder.usernameText.setTypeface(typeface1);
        viewHolder.nickNameText.setText("@"+User.getNickname());
        viewHolder.usernameText.setText(User.getFirstName() +" "+ User.getLastName());

        return convertView;
    }




    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView nickNameText;
        CircleImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            nickNameText = itemView.findViewById(R.id.nickname_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}

