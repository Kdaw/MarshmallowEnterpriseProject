package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewBidDetailsActivity extends AppCompatActivity {

    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference();
    private String bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid_details);

        final TextView bidPostTitle = findViewById(R.id.textView_BidDetailsJobTitle);
        final TextView bidder = findViewById(R.id.textView_BidDetailsBidder);
        final TextView bidValue = findViewById(R.id.textView_BidDetailsValue);
        final TextView bidComment = findViewById(R.id.textView_BidComment);
        Intent intent = getIntent();
        bid = intent.getStringExtra("id");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot ds = dataSnapshot.child("Bids").child(bid);
                String sPostTitle = ds.child("PostTitle").getValue(String.class);
                String sBidValue = "Bid Value: £" + ds.child("BidValue").getValue(Long.class).toString();
                String sBidID = ds.child("BidderID").getValue(String.class);
                String bidderName = dataSnapshot.child("Users").child(sBidID).child("Email").getValue(String.class);
                String sBidComment = ds.child("BidComment").getValue(String.class);

                bidComment.setText(sBidComment);
                bidPostTitle.setText(sPostTitle);
                bidValue.setText(sBidValue);
                bidder.setText(bidderName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void acceptBid(View v) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child("Bids").child(bid);
                String aPostTitle = ds.child("PostID").getValue(String.class);
                dbRef.child("Bids").child(bid).child("Accepted").setValue(true);
                for (DataSnapshot loopDs : dataSnapshot.child("Bids").getChildren()){

                    String bidId = loopDs.child("PostID").getValue(String.class);
                    if(bidId.equals(aPostTitle))
                        dbRef.child("Bids").child(loopDs.getKey()).child("Active").setValue(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
