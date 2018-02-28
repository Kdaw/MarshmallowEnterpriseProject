package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle   mToggle;
    @Override
    //added activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // --------------------------------------------------------------------------------------------------------------

        // Fire BaseCode -----------------------------------------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users")/*.child(currentUser.getUid())*/;
        //mDatabase.setValue(currentUser.getUid());
        mDatabase.child(currentUser.getUid()).child("Rate").setValue(0);
        mDatabase.child(currentUser.getUid()).child("Email").setValue(currentUser.getEmail());

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

    @Override
    public void onClick(View v){

        FirebaseAuth.getInstance().signOut();
    }

    //links button on homepage to the create post activity
    public void goToAnActivity(View view) {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(this, ProfileSettingsActivity.class);
        startActivity(intent);
    }


    public void goToJobsList(View view) {
        Intent intent = new Intent(this, ViewAvailableJobsActivity.class);
        startActivity(intent);
    }

}
