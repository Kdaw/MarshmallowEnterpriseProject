package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAvailableJobDetailsActivity extends AppCompatActivity {

    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference("Posts");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_job_details);

        final TextView title = findViewById(R.id.textView_JobDetailTitle);
        final TextView detail = findViewById(R.id.textView_JobDetailsDescription);
        final TextView pickup = findViewById(R.id.textView_JobDetailsPickup);
        final TextView dropoff = findViewById(R.id.textView_JobDetailsDropoff);
        final TextView distance = findViewById(R.id.textView_JobDetailsDistance);



        Intent intentExtra = getIntent();

        final String postID = intentExtra.getStringExtra("id");
        System.out.println("Intent section " + postID);


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(postID);
                DataSnapshot ds = dataSnapshot.child(postID);
                String sTitle ="Title: " + ds.child("title").getValue(String.class);
                String sDetails ="Details: " + ds.child("details").getValue(String.class);
                String sPickup ="Pickup From: " + ds.child("pickup").getValue(String.class);
                String sDropoff ="Deliver to: " + ds.child("dropoff").getValue(String.class);
                String sDistance ="Route Distance: " + ds.child("distance").getValue(String.class);
                System.out.println(sTitle);
                title.setText(sTitle);
                detail.setText(sDetails);
                pickup.setText(sPickup);
                dropoff.setText(sDropoff);
                distance.setText(sDistance);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
