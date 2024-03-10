package com.example.myapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnotherProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnotherProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AdapterOfPublications adapter1;
    ListView PublicationsList;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    static String email;
    static String Nickname;
    static String name;
    static int NumOfPublications;
    public static ArrayList<String> publications = new ArrayList<String>();

    public AnotherProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnotherProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnotherProfileFragment newInstance(String param1, String param2) {
        AnotherProfileFragment fragment = new AnotherProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nunito_extrabold);
        Typeface typeface1 = ResourcesCompat.getFont(getContext(), R.font.nunito_bold_italic);

        View view = inflater.inflate(R.layout.fragment_another_profile, container, false);
        PublicationsList = view.findViewById(R.id.YourPublications);
        Bundle bundle = getArguments();
        String UIDOfUserSelected = bundle.getString("UID");

        TextView Profile = view.findViewById(R.id.Profile);
        Profile.setTypeface(typeface);
        TextView DisplayName = view.findViewById(R.id.DisplayName);
        DisplayName.setTypeface(typeface1);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("User");



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("UID").getValue().equals(UIDOfUserSelected)) {
                        int n =0;
                        DisplayName.setText(ds.child("firstName").getValue(String.class) +" "+ ds.child("lastName").getValue(String.class));
                        NumOfPublications = ds.child("numOfPublications").getValue(Integer.class);
                        publications.clear();
                        while (n < NumOfPublications && publications.size() != NumOfPublications+1) {
                            if(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class) != null) {

                                publications.add(ds.child("publications").child("publication" + String.valueOf(n)).getValue(String.class));
                                n++;
                            }
                        }
                        Nickname = ds.child("nickname").getValue(String.class);

                    }
                }
                Collections.reverse(publications);
                adapter1 = new AdapterOfPublications(getContext(), publications, Nickname);
                PublicationsList.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });


        return view;
    }
}