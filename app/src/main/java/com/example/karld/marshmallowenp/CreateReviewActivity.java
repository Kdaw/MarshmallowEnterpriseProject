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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateReviewActivity extends AppCompatActivity {
    //region Description
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle   mToggle;
    private DatabaseReference mRef;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int numOfStars = ratingBar.getNumStars();
        ratingBar.setRating((float) 3.5);

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
                // Handle navigation view item clicks.
                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_account) {
                    Intent in = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_TOS) {
                    Intent in = new Intent(getApplicationContext(), TermsOfService.class);
                    startActivity(in);
                }
                return true;
            }
        });


        //reviewInput = (EditText) findViewById(R.id.Review_Text);
        //submitBtn = (Button) findViewById(R.id.Submit_Review);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRef = FirebaseDatabase.getInstance().getReference().child("Review").push();

                String reviewId = mRef.toString();
                String review_id = reviewId.replace(removeLink,"");

                review = reviewInput.getText().toString();

                mRef.child("review").setValue(review);
                mRef.child("User").setValue(getUserID());

                String totalStars = "Total Stars:: " + ratingBar.getNumStars();
                String rating = "Rating:: " + ratingBar.getRating();
                Toast.makeText(getApplicationContext(),totalStars + "\n" + rating, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), ViewPostActivity.class);
                intent.putExtra("Review_ID", review_id);
                startActivity(intent);
                finish();
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