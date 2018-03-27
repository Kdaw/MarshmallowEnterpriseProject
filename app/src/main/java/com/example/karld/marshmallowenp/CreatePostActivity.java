package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    //todo add comments and manage readability
    //todo Finish CreatePostActivity
    //create post page that is linked from home screen to allow user to create post

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //region Instance Variables
    private DatabaseReference mRef;

    String title;
    String details;
    String dropoff;
    String pickup;
    String distance;

    //Exists to remove this text from the beginning of the push link so when saving the postID from the push all we have is the ID itself
    String removeLink = "https://enpmarshmallow.firebaseio.com/Posts/";

    EditText titleInput;
    EditText detailInput;
    EditText PickupLocationInput;
    EditText DropoffLocationInput;
    EditText DistanceInput;

    Button postButton;
    //endregion


    /**
     * getUserID gets the Firebase ID from the database of the logged in user
     * and returns the ID as a String
     * @return
     */
    public static String getUserID()
    {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        return user.getUid().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        //region Matts Code

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
                }else if (id == R.id.nav_my_jobs) {
                    Intent in = new Intent (getApplicationContext(), ViewActiveJobsWithBidsActivity.class);
                    startActivity(in);
                }
                return true;
            }
        });
        //endregion

        titleInput = (EditText) findViewById(R.id.editText_Title);
        detailInput = (EditText) findViewById(R.id.editText_Details);
        PickupLocationInput = (EditText) findViewById(R.id.editText_Pickup);
        DropoffLocationInput = (EditText) findViewById(R.id.editText_Dropoff);
        DistanceInput = (EditText) findViewById(R.id.editText_Distance);

        postButton = (Button) findViewById(R.id.button_create_post);
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                mRef =  FirebaseDatabase.getInstance().getReference().child("Posts").push();


                //region Save push()value for postID
                String fullPostID = mRef.toString();
                    //this saves the push link with the firebase link before it, which needs removing
                String postID = fullPostID.replace(removeLink, "");
                    //this removes the firebase link and leaves postID as just the value required

//              Toast.makeText(CreatePostActivity.this, postID,
//              Toast.LENGTH_LONG).show();

                //endregion

                title = titleInput.getText().toString();
                details = detailInput.getText().toString();
                pickup = PickupLocationInput.getText().toString();
                dropoff = DropoffLocationInput.getText().toString();
                distance = DistanceInput.getText().toString();


                mRef.child("title").setValue(title);
                mRef.child("details").setValue(details);
                mRef.child("pickup").setValue(pickup);
                mRef.child("dropoff").setValue(dropoff);
                mRef.child("distance").setValue(distance);
                mRef.child("User").setValue(getUserID());
                mRef.child("Active").setValue(true);
                mRef.child("HasBids").setValue(false);
                mRef.child("Completed").setValue(false);    //Completed value for viewmydriverjobdetails

                //region Intent to send pushID
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("POST_ID", postID);
                startActivity(intent);
                //endregion
            }
        });
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

//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
//Writing to database
//https://stackoverflow.com/questions/39536517/write-new-data-in-android-firebase-database
//https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/
// com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41