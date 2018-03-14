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

public class ViewBidDetailsActivity extends AppCompatActivity {

    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid_details);

        final TextView bidPostTitle = findViewById(R.id.textView_BidDetailsJobTitle);
        final TextView bidder = findViewById(R.id.textView_BidDetailsBidder);
        final TextView bidValue = findViewById(R.id.textView_BidDetailsValue);
        Intent intent = getIntent();
        final String bid = intent.getStringExtra("id");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot ds = dataSnapshot.child("Bids").child(bid);
                String sPostTitle = ds.child("PostTitle").getValue(String.class);
                String sBidValue = "Bid Value: £" + ds.child("BidValue").getValue(Long.class).toString();
                String sBidID = ds.child("BidderID").getValue(String.class);
                String bidderName = dataSnapshot.child("Users").child(sBidID).child("Email").getValue(String.class);

                bidPostTitle.setText(sPostTitle);
                bidValue.setText(sBidValue);
                bidder.setText(bidderName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
