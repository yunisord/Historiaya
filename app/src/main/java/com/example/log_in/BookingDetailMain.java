package com.example.log_in;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class BookingDetailMain extends AppCompatActivity {

    //FOR UPDATE ONLY
    // Declaring variables
    Button notnowbtn, confirmbtn, Nextbtn, Donebut;
    ImageButton backbutton, reschedbtn, cancelbtn, refundbtn;
    TextView selectedTourText, totalText, selectedtouristsText, datetext;
    Dialog dialog;

    FirebaseAuth auth;
    private FirebaseFirestore db;
    public ListenerRegistration userDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail_main);

        // Initializing FirebaseFirestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initializing TextViews and Buttons
        backbutton = findViewById(R.id.backbtnDetailMain);
        selectedTourText = findViewById(R.id.selectedTourText);
        totalText = findViewById(R.id.totalText);
        selectedtouristsText = findViewById(R.id.selectedtouristsText);
        datetext = findViewById(R.id.datetext);
        refundbtn = findViewById(R.id.refundbtn);
        reschedbtn = findViewById(R.id.reschedbtn);
        cancelbtn = findViewById(R.id.cancelbtn);
        Donebut = findViewById(R.id.Donebut);

   // Initializing the dialog
        dialog = new Dialog(BookingDetailMain.this);
        dialog.setContentView(R.layout.dialog_cancellation);
        dialog.setCancelable(false);

        notnowbtn = dialog.findViewById(R.id.notnowbtn);
        confirmbtn = dialog.findViewById(R.id.confirmbtn);

        // Click listeners for dialog buttons
        notnowbtn.setOnClickListener(v -> {
            Intent backIntent = new Intent(BookingDetailMain.this, Main2.class);
            startActivity(backIntent);
            Toast.makeText(BookingDetailMain.this, "Not Now", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });


        confirmbtn.setOnClickListener(v -> {
            Intent backIntent = new Intent(BookingDetailMain.this, Main2.class);
            startActivity(backIntent);
            Toast.makeText(BookingDetailMain.this, "Confirm Cancellation, please wait for approval", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // Click listeners for navigation buttons
        reschedbtn.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetailMain.this, BookingDetails.class);
            startActivity(intent);
        });

        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetailMain.this, Profile.class);
            startActivity(intent);
        });

        refundbtn.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetailMain.this, BookingRefund.class);
            startActivity(intent);
        });

        cancelbtn.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetailMain.this, BookingCancellation.class);
            startActivity(intent);
        });

        Donebut.setOnClickListener(v -> {
            Intent intent = new Intent(BookingDetailMain.this, Profile.class);
            startActivity(intent);
        });

        // Retrieve and display data from Firestore
        retrieveDataFromFirestore();
    }

    // Method to retrieve data from Firestore
    @SuppressLint("DefaultLocale")
    private void retrieveDataFromFirestore() {
        userDataListener = db.collection("users")
                .document(auth.getCurrentUser().getUid())
                .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Log.e("BookingDetailMainActivity", "Error fetching user data: " + error.getMessage());
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String selectedTour = documentSnapshot.getString("selectedTour");

                        // Use getDouble for numeric values
                        double total = documentSnapshot.getDouble("totalAmount");

                        String selectedTouristNum = documentSnapshot.getString("selectedTouristNum");
                        String reservedDate = documentSnapshot.getString("reservedDate");

                        // Set TextViews with the retrieved data
                        datetext.setText(reservedDate);
                        selectedtouristsText.setText(selectedTouristNum);
                        totalText.setText(String.format("₱%.2f", total));
                        selectedTourText.setText(selectedTour);
                    } else {
                        showToast("No booking data found in Firestore.");
                    }
                });
    }

    // Method to display toast messages
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}