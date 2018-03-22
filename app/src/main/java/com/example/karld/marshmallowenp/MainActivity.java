package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle   mToggle;
    private GoogleSignInClient  mGoogleSignInClient;
    @Override
    //added activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//        // Fire BaseCode -----------------------------------------------------------------------------------------------
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

//        //mDatabase.setValue(currentUser.getUid());
        //TODO Gareth rating init
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users")/*.child(currentUser.getUid())*/;
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

    public void myJobs(View view) {
        Intent intent = new Intent(this, ViewActiveJobsWithBidsActivity.class);
        startActivity(intent);
    }

    public void myBids(View view) {
        Intent intent = new Intent(this, ViewMyBidsActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {
                        // ...
                    }
                });
    }


    public void goToJobsList(View view) {
        Intent intent = new Intent(this, ViewAvailableJobsActivity.class);
        startActivity(intent);
    }

}
