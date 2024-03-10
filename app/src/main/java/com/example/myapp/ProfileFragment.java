package com.example.myapp;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView PublicationsList;
    int LAUNCH_SECOND_ACTIVITY = 1;
    AdapterOfPublications adapter1;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nunito_extrabold);
        Typeface typeface1 = ResourcesCompat.getFont(getContext(), R.font.nunito_bold_italic);

        PublicationsList = view.findViewById(R.id.YourPublications);
        Button CreatePublication = view.findViewById(R.id.CreatePublication);
        TextView Profile = view.findViewById(R.id.Profile);
        TextView DisplayName = view.findViewById(R.id.DisplayName);
        ImageView profilePic = view.findViewById(R.id.imageView2);

        DisplayName.setTypeface(typeface1);
        Profile.setTypeface(typeface);
        CreatePublication.setTypeface(typeface);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Glide.with(getContext()).load(MainActivity.profilePicUri).into(profilePic);
        DisplayName.setText(MainActivity.Username);

        adapter1 = new AdapterOfPublications(getActivity(), MainActivity.publications, MainActivity.Nickname);
        PublicationsList.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        CreatePublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".CreatePublicationActivity");
                MainActivity.publications.clear();
                adapter1.notifyDataSetChanged();

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new CreatePublicationFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {

                adapter1.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}
