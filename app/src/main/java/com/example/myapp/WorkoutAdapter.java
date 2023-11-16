package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class WorkoutAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public static ArrayList<ListItem> exercises = new ArrayList<ListItem>();

    public WorkoutAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return exercises.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        switch (exercises.get(position).getItemViewType()) {
            case 1:
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.list_item, parent, false);
                    holder.nameView = convertView.findViewById(R.id.nameView);
                    holder.sets = convertView.findViewById(R.id.sets);
                    holder.reps = convertView.findViewById(R.id.reps);
                    holder.rest = convertView.findViewById(R.id.RestTime);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.nameView.setId(position);
                holder.sets.setId(position);
                holder.reps.setId(position);
                holder.rest.setId(position);


                holder.nameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).nameExercise = Caption.getText().toString();
                        }
                    }
                });
                holder.reps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).NumReps = Integer.parseInt(Caption.getText().toString());
                        }
                    }
                });
                holder.sets.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).NumSets = Integer.parseInt(Caption.getText().toString());
                        }
                    }
                });
                holder.rest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).restime = Integer.parseInt(Caption.getText().toString());
                        }
                    }
                });
                break;
            case 2:
                ViewHolder holder1;
                if (convertView == null) {
                    holder1 = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.list_item1, parent, false);
                    holder1.nameView1 = convertView.findViewById(R.id.nameView1);
                    holder1.time = convertView.findViewById(R.id.time);
                    holder1.typeTime = convertView.findViewById(R.id.typeTime);

                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder) convertView.getTag();
                }
                holder1.nameView1.setId(position);
                holder1.time.setId(position);
                holder1.typeTime.setId(position);


                holder1.nameView1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).nameExercise = Caption.getText().toString();
                        }
                    }
                });
                holder1.time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            exercises.get(position).RestTime = Integer.parseInt(Caption.getText().toString());
                        }
                    }
                });
                holder1.typeTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup arg0, int id) {
                        switch(id) {
                            case R.id.radio_sec:
                                exercises.get(position).timeType = "sec";
                                break;
                            case R.id.radio_min:
                                exercises.get(position).timeType = "min";
                                break;
                            default:
                                break;
                        }
                    }});
                break;
        }
        return convertView;
    }
}


class ViewHolder {
    EditText nameView;
    EditText sets;
    EditText reps;
    EditText rest;
    EditText nameView1;
    EditText time;
    RadioGroup typeTime;
}

class ListItem {
    private int type;
    ListItem(int i){
        if(i == 1){
          this.type = 1;
        }else{
          this.type = 2;
        }
    }
    public int getItemViewType() {
        return this.type;
    }
    String nameExercise;
    int NumReps;
    int NumSets;
    int restime;
    int RestTime;
    String timeType;
}
