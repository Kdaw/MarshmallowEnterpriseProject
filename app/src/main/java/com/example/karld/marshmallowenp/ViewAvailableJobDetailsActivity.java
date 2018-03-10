package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAvailableJobDetailsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference("Posts");
    private String postedBy;
    private DatabaseReference mRef;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_job_details);

        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Nav Menu linking - Links Activities From Nav Menu ---------------------------------------------------------------
        NavigationView nV =(NavigationView)findViewById(R.id.nav_menu);
        nV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_create_post) {
                    Intent in = new Intent(getApplicationContext(), CreatePostActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_view_jobs) {
                    Intent in = new Intent(getApplicationContext(), ViewAvailableJobsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_account) {
                    Intent in = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
                    startActivity(in);
                }else if (id == R.id.nav_settings) {
                    Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(in);
                }else if (id == R.id.nav_Logout) {
                    //todo figure a signout method that signs out locally
                    //signOut();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent( getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        final TextView title = findViewById(R.id.textView_JobDetailTitle);
        final TextView detail = findViewById(R.id.textView_JobDetailsDescription);
        final TextView pickup = findViewById(R.id.textView_JobDetailsPickup);
        final TextView dropoff = findViewById(R.id.textView_JobDetailsDropoff);
        final TextView distance = findViewById(R.id.textView_JobDetailsDistance);

        button = findViewById(R.id.button_JobDetailsBidNow);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bidOnJob();
            }
        });

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
                postedBy = ds.child("User").getValue(String.class);
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

    public void bidOnJob() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intentExtra = getIntent();

        String cUserID = currentUser.getUid();
        String cPostID = intentExtra.getStringExtra("id");
        EditText bidValue = findViewById(R.id.editText_JobDetailsBidValue);
        int cBidValue = Integer.parseInt(bidValue.getText().toString());

        mRef =  FirebaseDatabase.getInstance().getReference().child("Bids").push();
        mRef.child("BidderID").setValue(cUserID);
        mRef.child("PostID").setValue(cPostID);
        mRef.child("BidValue").setValue(cBidValue);
        mRef.child("PostOwner").setValue(postedBy);

        Toast.makeText(this, "Your bid has been posted successfully", Toast.LENGTH_LONG).show();


        Intent returnIntent = new Intent(this, ViewAvailableJobsActivity.class);
        startActivity(returnIntent);
    }

    // Enables Nav menu click -  Allows for both slide and on click access.
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
