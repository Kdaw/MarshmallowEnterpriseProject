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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateReviewActivity extends AppCompatActivity {
    //region Description
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle   mToggle;
    private DatabaseReference mRef;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date = new Date();

    String review;


    EditText reviewInput;
    Button submitBtn;
    String removeLink = "https://enpmarshmallow.firebaseio.com/Review/";

    public static String getUserID()
    {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        return user.getUid().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creates the create review activity after the completion of delivery to it's intended destination

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int numOfStars = ratingBar.getNumStars();   // holds the star value
        ratingBar.setRating((float) 1);     //Sets rating to 1 star as default

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Email");

        String uEmail = currentUser.getEmail();
        String uName = currentUser.getDisplayName();

        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Nav Menu linking - Links Activities From Nav Menu ---------------------------------------------------------------
        NavigationView nV =(NavigationView)findViewById(R.id.nav_menu);
        TextView txtProfileName = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavUser);
        txtProfileName.setText(uName);
        TextView txtProfileEmail = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavEmail);
        txtProfileEmail.setText(uEmail);
        nV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks.
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
                }else if (id == R.id.nav_my_jobs) {
                    Intent in = new Intent (getApplicationContext(), ViewActiveJobsWithBidsActivity.class);
                    startActivity(in);
                }
                return true;
            }
        });


        //Stores the information in the edit text field
        reviewInput = (EditText) findViewById(R.id.Review_Text);
        //Button Submission of the review
        submitBtn = (Button) findViewById(R.id.Submit_Review);

        //Onclick listener to send the review page to the firebase instance and store the data once the submit button has been pressed
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pushes the data to the named node in the database
                mRef = FirebaseDatabase.getInstance().getReference().child("Review").push();

                String reviewId = mRef.toString(); //Converts the data to a string for human understanding
                String review_id = reviewId.replace(removeLink,"");

                review = reviewInput.getText().toString();
                //Sets the values under each node in the database
                mRef.child("Review").setValue(review);
                mRef.child("User").setValue(getUserID());
                mRef.child("Rating").setValue(ratingBar.getRating());
                mRef.child("DatePosted").setValue(simpleDateFormat.format(date).toString()); //Adds a timestamp

                //Toast variables to show the user that the review has been updated to the database
                String totalStars = "Total Stars:: " + ratingBar.getNumStars();
                String rating = "Rating:: " + ratingBar.getRating();
                Toast.makeText(getApplicationContext(),totalStars + "\n" + rating, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), ViewPostActivity.class);
                intent.putExtra("Review_ID", review_id);
                startActivity(intent);
                finish();   //Finishes the activity and removes it from memory
            }
        });
    }
    //endregion
    //Enables Nav menu click -  Allows for both slide and on click access.

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