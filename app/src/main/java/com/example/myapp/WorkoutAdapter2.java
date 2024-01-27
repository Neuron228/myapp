package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutAdapter2 extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Workout> workouts2;

        WorkoutAdapter2(Context context, ArrayList<Workout> workouts) {
            this.workouts2 = workouts;
            this.inflater = LayoutInflater.from(context);
        }
        public int getCount() {
        return workouts2.size();
    }

        public Object getItem(int position) {
        return position;
    }

        public long getItemId(int position) {
        return position;
    }

        public View getView(int position, View convertView, ViewGroup parent) {
            switch (workouts2.get(position).getItemViewType()) {
                case 1:
                    final ViewHolder2 viewHolder;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.ready_exercise_reps, parent, false);
                        viewHolder = new ViewHolder2(convertView);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder2) convertView.getTag();
                    }
                    final Workout workout = workouts2.get(position);

                    viewHolder.nameViewText.setText(workout.getName());
                    viewHolder.setsText.setText(Integer.toString(workout.getSets()) + " sets");
                    viewHolder.repsText.setText(Integer.toString(workout.getRepetitions()) + " reps");
                    viewHolder.restTimeText.setText(Integer.toString(workout.getResttime()) + " min");
                    break;
                case 2:
                    final ViewHolder2 viewHolder1;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.ready_exercise_time, parent, false);
                        viewHolder1 = new ViewHolder2(convertView);
                        convertView.setTag(viewHolder1);
                    } else {
                        viewHolder1 = (ViewHolder2) convertView.getTag();
                    }
                    final Workout workout1 = workouts2.get(position);

                    viewHolder1.nameView1Text.setText(workout1.getName());
                    viewHolder1.timeText.setText(Integer.toString(workout1.getResttime()));
                    viewHolder1.timeTypeText.setText(workout1.getTimeType());
            }
            return convertView;
        }

        }
class ViewHolder2 {
    final TextView nameViewText, repsText, setsText, restTimeText;
    final TextView nameView1Text,timeText,timeTypeText;

    ViewHolder2(View view) {
        nameViewText = view.findViewById(R.id.nameViewText);
        repsText = view.findViewById(R.id.repsText);
        setsText = view.findViewById(R.id.setsText);
        restTimeText = view.findViewById(R.id.RestTime2);
        nameView1Text = view.findViewById(R.id.nameView1Text);
        timeText = view.findViewById(R.id.timeText);
        timeTypeText = view.findViewById(R.id.timeTypeText);
    }
}
