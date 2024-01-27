package com.example.myapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterOfPublications extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<String> publications2;
    private String NameUser;

    AdapterOfPublications(Context context, ArrayList<String> publications,String name) {
        this.publications2 = publications;
        this.inflater = LayoutInflater.from(context);
        this.NameUser = name;
    }
    public int getCount() {
        return publications2.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder4 viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.publication, parent, false);
            viewHolder = new ViewHolder4(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder4) convertView.getTag();
        }
        final String publications3 = publications2.get(position);

        viewHolder.TextOfPublication.setText(publications3);
        viewHolder.UserName.setText(NameUser);

        return convertView;
    }
}
class ViewHolder4 {
    final TextView UserName,TextOfPublication;


    ViewHolder4(View view) {
      TextOfPublication = view.findViewById(R.id.messagePublication);
      UserName = view.findViewById(R.id.UserNamePublication);

    }
}
