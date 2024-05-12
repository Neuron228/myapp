package com.example.myapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment implements ArrayAdapterRecyclerView.ItemClickListener{

    private FloatingActionButton AddButton1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase database;
    DatabaseReference mDatabaseW;
    DatabaseReference mDatabaseU;
    Context context;
    ArrayAdapterRecyclerView adapter;

    RecyclerView recyclerView;


    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout, container,false);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nunito_extrabold);
        AddButton1 = view.findViewById(R.id.AddButton1);
        recyclerView = view.findViewById(R.id.ListView);
        TextView MyWorkouts = view.findViewById(R.id.textView2);
        MyWorkouts.setTypeface(typeface);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        context = getContext();

        if (MainActivity.list.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Your code here
                    // This code will run in a separate thread
                    // For example:
                    try {
                        System.out.println("Жду");
                        Thread.sleep(2000);
                        System.out.println("Не жду");
                        ;// Wait for 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (NullPointerException d) {

                    }
                }
            }).start();
        }else{
            progressBar.setVisibility(View.GONE);
        }


        database = FirebaseDatabase.getInstance();
        mDatabaseW = database.getReference("Workouts");
        mDatabaseU = database.getReference("User");


        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ArrayAdapterRecyclerView(context,MainActivity.list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        AddButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(".WorkoutMakerActivity");
                intent1.putExtra("view","set");
                startActivity(intent1);
            }
        });



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position =  viewHolder.getBindingAdapterPosition();
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete workout")
                        .setMessage("Are you sure you want to delete this workout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabaseW.child(MainActivity.IDlist.get(position)).removeValue();
                                MainActivity.IDlist.remove(position);
                                MainActivity.list.remove(position);
                                MainActivity.ALLUserWorkouts.remove(position);
                                mDatabaseU.child(MainActivity.Nickname).child("NumOfWorkouts").setValue(MainActivity.NumOfWorkouts - 1);

                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(position);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);


        return view;
    }
    @Override
    public void onItemClick(View view, int position) {

        Intent intent2 = new Intent(getContext(),CreatedWorkoutActivity.class);
        intent2.putExtra("NamePosition",position);
        startActivity(intent2);
    }

}
