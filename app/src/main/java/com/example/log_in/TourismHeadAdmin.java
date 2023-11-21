package com.example.log_in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TourismHeadAdmin extends AppCompatActivity {
    RadioButton Upcoming_Tab, History_tab, Pending_Tab;
    ScrollView Upcoming_ScrollView, History_ScrollView, Pending_ScrollView;
    View wan, to, tre;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    ImageButton backbutton;
    ProgressDialog progressDialog;

    RecyclerView Pending_RecyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_head_admin);

        // Initialize Firebase Authentication and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        // Example assuming these are View objects in your layout
        wan = findViewById(R.id.wan);
        to = findViewById(R.id.to);
        tre = findViewById(R.id.tre);


        backbutton = findViewById(R.id.backbutton);
        Pending_RecyclerView = findViewById(R.id.Pending_RecyclerView);
        setUpRecyclerView();

        EventChangeListener();

        // Back button
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(TourismHeadAdmin.this, Admin.class);
            startActivity(intent);
            finish();
        });

        setUpTabsAndViews();
    }

    private void EventChangeListener() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        db.collection("users").orderBy("reservedDate", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error...", error.getMessage());
                            return;
                        }

                        userArrayList.clear(); // Clear the list before adding new data
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                        }

                        myAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

    private void setUpRecyclerView() {
        // RecyclerView setup
        Pending_RecyclerView.setHasFixedSize(true);
        Pending_RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set the adapter
        userArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, userArrayList);
        Pending_RecyclerView.setAdapter(myAdapter);
    }

    private void setUpTabsAndViews() {
        // Set up tabs and views
        Upcoming_ScrollView = findViewById(R.id.Upcoming_ScrollView);
        Upcoming_Tab = findViewById(R.id.Upcoming_Tab);
        Upcoming_Tab.setOnClickListener(v -> Upcoming_Tab());
        History_ScrollView = findViewById(R.id.History_ScrollView);
        History_tab = findViewById(R.id.History_tab);
        History_tab.setOnClickListener(v -> History_tab());
        Pending_ScrollView = findViewById(R.id.Pending_ScrollView);
        Pending_Tab = findViewById(R.id.Pending_Tab);
        Pending_Tab.setOnClickListener(v -> Pending_Tab());
    }

    // tabs
    private void Upcoming_Tab() {
        Upcoming_Tab.setChecked(true);
        Upcoming_Tab.setTextColor(ContextCompat.getColor(this, R.color.green));
        Upcoming_ScrollView.setVisibility(View.VISIBLE);
        History_tab.setChecked(false);
        History_tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        History_ScrollView.setVisibility(View.GONE);
        Pending_Tab.setChecked(false);
        Pending_Tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        Pending_ScrollView.setVisibility(View.GONE);
        wan.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        to.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
        tre.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
    }

    private void History_tab() {
        Upcoming_Tab.setChecked(false);
        Upcoming_Tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        Upcoming_ScrollView.setVisibility(View.GONE);
        History_tab.setChecked(true);
        History_tab.setTextColor(ContextCompat.getColor(this, R.color.green));
        History_ScrollView.setVisibility(View.VISIBLE);
        Pending_Tab.setChecked(false);
        Pending_Tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        Pending_ScrollView.setVisibility(View.GONE);
        wan.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
        to.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        tre.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
    }

    private void Pending_Tab() {
        Upcoming_Tab.setChecked(false);
        Upcoming_Tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        Upcoming_ScrollView.setVisibility(View.GONE);
        History_tab.setChecked(false);
        History_tab.setTextColor(ContextCompat.getColor(this, R.color.fadedgreen));
        History_ScrollView.setVisibility(View.GONE);
        Pending_Tab.setChecked(true);
        Pending_Tab.setTextColor(ContextCompat.getColor(this, R.color.green));
        Pending_ScrollView.setVisibility(View.VISIBLE);
        wan.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
        to.setBackgroundColor(ContextCompat.getColor(this, R.color.fadedgreen));
        tre.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
    }
}
