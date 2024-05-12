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

    public Workout workout;
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


    public View getView1(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view  = mInflater.inflate(R.layout.exercise_reps, parent, false);
        }
        if(workout.getItemViewType() == 1){
            EditText nameView = view.findViewById(R.id.nameView);
            EditText sets =  view.findViewById(R.id.sets);
            EditText reps = view.findViewById(R.id.reps);
            EditText rest = view.findViewById(R.id.RestTime);

            nameView.setText("suka");
        }else if(workout.getItemViewType() == 2) {

            EditText nameView1 = view.findViewById(R.id.nameView1);
            EditText time = view.findViewById(R.id.time);
            RadioGroup typeTime = view.findViewById(R.id.typeTime);

            nameView1.setText("suka");
        }

        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        switch (exercises.get(position).getItemViewType()) {
            case 1:
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.exercise_reps, parent, false);
                    holder.nameView = convertView.findViewById(R.id.nameView);
                    holder.sets = convertView.findViewById(R.id.sets);
                    holder.reps = convertView.findViewById(R.id.reps);
                    holder.rest = convertView.findViewById(R.id.RestTime);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if(exercises.get(position).getNameExercise() != null) {
                    holder.nameView.setText(exercises.get(position).getNameExercise());
                    holder.sets.setText(Integer.toString(exercises.get(position).NumSets));
                    holder.reps.setText(Integer.toString(exercises.get(position).NumReps));
                    holder.rest.setText(Integer.toString(exercises.get(position).restime));
                }
                holder.nameView.setId(position);
                holder.sets.setId(position);
                holder.reps.setId(position);
                holder.rest.setId(position);


                holder.nameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                            exercises.get(position).nameExercise = Caption.getText().toString();
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("suka");
                            }
                        }
                    }
                });
                holder.reps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                            exercises.get(position).NumReps = Integer.parseInt(Caption.getText().toString());
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("suka");
                            }
                        }
                    }
                });
                holder.sets.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                            exercises.get(position).NumSets = Integer.parseInt(Caption.getText().toString());
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("suka");
                            }
                        }
                    }
                });
                holder.rest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                            exercises.get(position).restime = Integer.parseInt(Caption.getText().toString());
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("suka");
                            }
                        }
                    }
                });
                break;
            case 2:
                ViewHolder holder1;
                if (convertView == null) {
                    holder1 = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.exercise_time, parent, false);
                    holder1.nameView1 = convertView.findViewById(R.id.nameView1);
                    holder1.time = convertView.findViewById(R.id.time);
                    holder1.typeTime = convertView.findViewById(R.id.typeTime);
                    holder1.RadioMin = convertView.findViewById(R.id.radio_min);
                    holder1.RadioSec = convertView.findViewById(R.id.radio_sec);
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder) convertView.getTag();
                }
                if(exercises.get(position).getNameExercise() != null) {
                    holder1.nameView1.setText(exercises.get(position).getNameExercise());
                    holder1.time.setText(Integer.toString(exercises.get(position).Time));
                    if (exercises.get(position).timeType.equals("sec")){
                        holder1.RadioSec.setChecked(true);
                    }else{
                        holder1.RadioMin.setChecked(true);
                    }
                }
                holder1.nameView1.setId(position);
                holder1.time.setId(position);
                holder1.typeTime.setId(position);


                holder1.nameView1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                                exercises.get(position).nameExercise = Caption.getText().toString();
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("suka");
                            }

                        }
                    }
                });
                holder1.time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText Caption = (EditText) v;
                        if (!hasFocus && !Caption.getText().toString().equals("")) {
                            final int position = v.getId();
                            try {
                            exercises.get(position).Time = Integer.parseInt(Caption.getText().toString());
                        }catch (IndexOutOfBoundsException e){
                            System.out.println("suka");
                        }
                        }
                    }
                });
                holder1.typeTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup arg0, int id) {
                        switch(id) {
                            case R.id.radio_sec:
                                try {
                                exercises.get(position).timeType = "sec";
                                }catch (IndexOutOfBoundsException e){
                                    System.out.println("suka");
                                }
                                break;
                            case R.id.radio_min:
                                try {
                                exercises.get(position).timeType = "min";
                                }catch (IndexOutOfBoundsException e){
                                    System.out.println("suka");
                                }
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
    RadioButton RadioSec;
    RadioButton RadioMin;
}

class ListItem {
    String nameExercise;
    int NumReps;
    int NumSets;
    int restime;
    int Time;
    String timeType;
    private int type;
    ListItem(int i){
        this.type = i;
    }

    public ListItem(int type,String nameExercise, int numReps, int numSets, int restime) {
        this.type = type;
        this.nameExercise = nameExercise;
        this.NumReps = numReps;
        this.NumSets = numSets;
        this.restime = restime;
    }

    public ListItem( int type,String nameExercise, int time, String timeType) {
        this.nameExercise = nameExercise;
        Time = time;
        this.timeType = timeType;
        this.type = type;
    }

    public int getItemViewType() {
        return this.type;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(String nameExercise) {
        this.nameExercise = nameExercise;
    }

    public int getNumReps() {
        return NumReps;
    }

    public void setNumReps(int numReps) {
        NumReps = numReps;
    }

    public int getNumSets() {
        return NumSets;
    }

    public void setNumSets(int numSets) {
        NumSets = numSets;
    }

    public int getRestime() {
        return restime;
    }

    public void setRestime(int restime) {
        this.restime = restime;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
}
