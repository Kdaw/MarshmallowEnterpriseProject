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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //user details in nav bar BEGIN
        //Textviews are found and changed as can be seen with debug but dont show up on the screen.
        //If placeholders are removed then comment out all code below up to END comment
        FirebaseAuth nAuth = FirebaseAuth.getInstance();
        FirebaseUser navUser = nAuth.getCurrentUser();

        setContentView(R.layout.navigation_header);
        TextView navUserName = findViewById(R.id.textView_NavUser);
        TextView navUserEmail = findViewById(R.id.textView_NavEmail);

        String uEmail = navUser.getEmail();
        String uName = navUser.getDisplayName();

        setContentView(R.layout.activity_main);

        navUserEmail.setText(uEmail);
        navUserName.setText(uName);

        //user details in nav bar END

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
                } else if (id == R.id.nav_account) {
                    Intent in = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_TOS) {
                    Intent in = new Intent(getApplicationContext(), TermsOfService.class);
                    startActivity(in);
                } else if (id == R.id.nav_Logout) {
                    //todo figure a signout method that signs out locally
                    //signOut();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent( getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
                return true;
            }
        });

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
