package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewMyDriverJobsActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView availableJobs;
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference("Posts");
    private int itemSelected = 0;
    private String[] jobID = new String[1000];
    public static final String MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_driver_jobs);

        //region Matts Nav Menu Code
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Email");

        String uEmail = currentUser.getEmail();
        String uName = currentUser.getDisplayName();

        // Slider Menu Code ----------------------------------------------------------------------------------------------
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Nav Menu linking - Links Activities From Nav Menu ---------------------------------------------------------------
//        NavigationView nV = (NavigationView) findViewById(R.id.nav_menu);
//        TextView txtProfileName = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavUser);
//        txtProfileName.setText(uName);
//        TextView txtProfileEmail = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavEmail);
//        txtProfileEmail.setText(uEmail);
//        nV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                // Handle navigation view item clicks here.
//                int id = menuItem.getItemId();
//
//                if (id == R.id.nav_home) {
//                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(in);
//                } else if (id == R.id.nav_create_post) {
//                    Intent in = new Intent(getApplicationContext(), CreatePostActivity.class);
//                    startActivity(in);
//                } else if (id == R.id.nav_view_jobs) {
//                    Intent in = new Intent(getApplicationContext(), ViewAvailableJobsActivity.class);
//                    startActivity(in);
//                } else if (id == R.id.nav_account) {
//                    Intent in = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
//                    startActivity(in);
//                } else if (id == R.id.nav_settings) {
//                    Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
//                    startActivity(in);
//                } else if (id == R.id.nav_Logout) {
//                    //todo figure a signout method that signs out locally
//                    //signOut();
//                    FirebaseAuth.getInstance().signOut();
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                } else if (id == R.id.nav_my_jobs) {
//                    Intent in = new Intent(getApplicationContext(), ViewActiveJobsWithBidsActivity.class);
//                    startActivity(in);
//                }
//                return true;
//            }
//        });
        //endregion

        Intent intent = new Intent(this, ViewAvailableJobDetailsActivity.class);

        availableJobs = (ListView) findViewById(R.id.ListView_viewMyDriverJobs);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        availableJobs.setAdapter(adapter);
        availableJobs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //availableJobs.setTextColor(Color.parseColor("#FFFFFF"));
//        availableJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                itemSelected = position;
//
//
//            }
//        });


        addChildEventListener();

        availableJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), ViewMyDriverJobDetailsActivity.class);
                String ident = jobID[position];
                intent.putExtra("id", ident);
                startActivity(intent);
            }
        });

    }

    private void addChildEventListener() {
        ChildEventListener cListener = new ChildEventListener() {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    jobID[itemSelected] = key;
                    System.out.println(jobID[itemSelected]);
                    String name = ds.getKey();
                    listKeys.add(name);

                    try {
                        if(name.equals("title")) {
                            if (dataSnapshot.child("Driver").getValue(String.class).equals(currentUser.getUid()) &&
                                    dataSnapshot.child("Completed").getValue(boolean.class) == false ||
                                    dataSnapshot.child("Completed").getValue(boolean.class) == null ) { //compelete check for view mydriverjobdetails
                                adapter.add(dataSnapshot.child(name).getValue(String.class));
                                itemSelected++;
                            }
                        }
                    } catch (NullPointerException e) { }

                }

                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //code from www.techotopia.com/index.php/A_Firebase_Realtime_Database_List_Data_Tutorial
                //deals with removal from database to update snapshot
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRef.addChildEventListener(cListener);
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
