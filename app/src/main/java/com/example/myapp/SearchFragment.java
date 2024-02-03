package com.example.myapp;

import android.app.appsearch.SearchResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText searchInput;
    ImageButton searchButton;
    ListView PublicationsList;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    AdapterOfPublications adapter1;
    public ArrayList<ApplicationAccount> UsersList = new ArrayList<>();
    ArrayList<ApplicationAccount> UsersList1;

    boolean userList1Activated = false;
    public int PositionOfSelectedItem;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ApplicationAccount account = new ApplicationAccount(ds.child("firstName").getValue(String.class), ds.child("lastName").getValue(String.class), ds.child("nickname").getValue(String.class), ds.child("UID").getValue(String.class));
                    if (!account.getUid().equals(fUser.getUid())) {
                        UsersList.add(account);
                    }
                    if (getActivity() != null) {
                        SearchUserRecyclerAdapter adapter = new SearchUserRecyclerAdapter(getActivity(), UsersList);
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        searchInput = view.findViewById(R.id.seach_username_input);
        searchButton = view.findViewById(R.id.search_user_btn);
        listView = view.findViewById(R.id.search_user_listview);

        searchInput.requestFocus();

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() < 3) {
                searchInput.setError("Invalid Username");
                return;
            }
            SearchPeopleAndFriends(searchTerm);
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String UidOfUserSelected = UsersList.get(position).getUid();

                if(userList1Activated){
                    UidOfUserSelected =UsersList1.get(position).getUid();
                }
                AnotherProfileFragment f = new AnotherProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("UID",UidOfUserSelected);
                f.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, f);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    private void SearchPeopleAndFriends(String searchBoxInput) {
        UsersList1 = new ArrayList<>();
        userList1Activated = false;
        for(int i=0;i<UsersList.size();i++){
            ApplicationAccount user = UsersList.get(i);
            if(user.getNickname().startsWith(searchBoxInput) || user.getLastName().startsWith(searchBoxInput)||user.getFirstName().startsWith(searchBoxInput)){
                UsersList1.add(user);
            }
        }
            if(UsersList1.size() ==0){
                searchInput.setError("User is not found");
            }

        SearchUserRecyclerAdapter adapter = new SearchUserRecyclerAdapter(getContext(), UsersList1);
        userList1Activated =true;
        listView.setAdapter(adapter);

    }

}

