package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HomePageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Publication> publici;

    HomePageAdapter(Context context, ArrayList<Publication> publici) {
        this.publici = publici;
        this.inflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return publici.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder3 viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.publication, parent, false);
            viewHolder = new ViewHolder3(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder3) convertView.getTag();
        }
        final Publication publicazia = publici.get(position);

        viewHolder.datetime.setText(publicazia.getDateTime());
        viewHolder.message.setText(publicazia.getMessage());
        viewHolder.userName.setText(publicazia.getUserName());

        return convertView;
    }
}

class ViewHolder3 {
    final TextView userName, message,datetime;

    ViewHolder3(View view) {
        userName = view.findViewById(R.id.UserNamePublication);
        message = view.findViewById(R.id.messagePublication);
        datetime = view.findViewById(R.id.datetime);
    }
}
