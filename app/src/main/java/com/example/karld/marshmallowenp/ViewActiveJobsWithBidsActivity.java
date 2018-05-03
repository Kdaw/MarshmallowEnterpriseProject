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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewActiveJobsWithBidsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle   mToggle;
    private ArrayList<String> listItemsWithBids = new ArrayList<>();
    private ArrayList<String> listKeysWithBids = new ArrayList<>();
    private ArrayList<String> listItemsNoBids = new ArrayList<>();
    private ArrayList<String> listKeysNoBids = new ArrayList<>();
    private ArrayAdapter<String> adapterWithBids;
    private ArrayAdapter<String> adapterNoBids;
    private ListView availableJobsWithBids;
    private ListView availableJobsNoBids;
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    //todo check using one reference and snapshot to allow checks between tables.
    private DatabaseReference dbRefPosts = fbDatabase.getReference("Posts");
    private DatabaseReference dbRefBids = fbDatabase.getReference("Bids");
    private int itemSelectedWithBids = 0;
    private int itemSelectedNoBids = 0;
    private String[] jobIDWithBids = new String[1000];
    private String[] jobIDNoBids = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_active_jobs_with_bids);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Email");

        String uEmail = currentUser.getEmail();
        String uName = currentUser.getDisplayName();

        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
                } else if (id == R.id.nav_settings) {
                    Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_Logout) {
                    //todo figure a signout method that signs out locally
                    //signOut();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
//                else if (id == R.id.nav_my_jobs) {
//                    Intent in = new Intent (getApplicationContext(), ViewActiveJobsWithBidsActivity.class);
//                    startActivity(in);
//                }
                return true;
            }
        });

        //Set up listView for one option choice without any radial buttons
        availableJobsWithBids = (ListView) findViewById(R.id.ListView_ViewActiveWithBids);
        adapterWithBids = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemsWithBids);
        availableJobsWithBids.setAdapter(adapterWithBids);
        availableJobsWithBids.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        addChildEventListenerBids();

        //get JobID and load details when item in list is clicked
        availableJobsWithBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), ViewBidDetailsActivity.class);
                System.out.println("ID just before adding to intent " + jobIDWithBids[position]);
                String ident = jobIDWithBids[position];
                intent.putExtra("id", ident);
                startActivity(intent);
            }
        });

        //Set up listView for one option choice without any radial buttons
        availableJobsNoBids = (ListView) findViewById(R.id.ListView_ViewActiveNoBids);
        adapterNoBids = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemsNoBids);
        availableJobsNoBids.setAdapter(adapterNoBids);
        availableJobsNoBids.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        addChildEventListenerNoBids();

        //Get JobID and load details when item in list is clicked
        availableJobsNoBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(),ViewMyJobDetailsActivity.class );
                System.out.println("ID just before adding to intent " + jobIDNoBids[position]);
                String ident = jobIDNoBids[position];
                intent.putExtra("id", ident);
                startActivity(intent);
            }
        });
    }

    private void addChildEventListenerBids() {
        ChildEventListener cListener = new ChildEventListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            //gets all PostIDs that appear in the bids table and adds them to jobIDWithBids
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    jobIDWithBids[itemSelectedWithBids] = key;

                    String name = ds.getKey();
                    listKeysWithBids.add(name);
                    if(dataSnapshot.child("PostOwner").getValue(String.class).equals(currentUser.getUid())) {
                        if (name.equals("PostTitle")) {
                            //Format String, could be done better with a String builder - look at when refactoring
                            adapterWithBids.add(dataSnapshot.child(name).getValue(String.class) + "\t \t \t BID = £" + dataSnapshot.child("BidValue").getValue(Long.class));
                            itemSelectedWithBids++;
                        }
                    }
                }

                listKeysWithBids.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //code from www.techotopia.com/index.php/A_Firebase_Realtime_Database_List_Data_Tutorial
                //deals with removal from database to update snapshot
                String key = dataSnapshot.getKey();
                int index = listKeysWithBids.indexOf(key);

                if (index != -1) {
                    listItemsWithBids.remove(index);
                    listKeysWithBids.remove(index);
                    adapterWithBids.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRefBids.addChildEventListener(cListener);
    }

    private void addChildEventListenerNoBids() {
        ChildEventListener cListener = new ChildEventListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //gets all PostIDs that appear in the bids table and adds them to jobIDWithBids
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    jobIDNoBids[itemSelectedNoBids] = key;

                    String name = ds.getKey();
                    listKeysNoBids.add(name);

                    //try catch to solve issue where snapshot can be checked from elsewhere before it exists
                    try {
                        if (dataSnapshot.child("User").getValue(String.class).equals(currentUser.getUid())
                                && dataSnapshot.child("HasBids").getValue(boolean.class) == false) {
                            if (name.equals("title")) {
                                adapterNoBids.add(dataSnapshot.child(name).getValue(String.class));
                                itemSelectedNoBids++;
                            }
                        }
                    } catch (NullPointerException e) { }
                }

                listKeysNoBids.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //code from www.techotopia.com/index.php/A_Firebase_Realtime_Database_List_Data_Tutorial
                //deals with removal from database to update snapshot
                String key = dataSnapshot.getKey();
                int index = listKeysNoBids.indexOf(key);

                if (index != -1) {
                    listItemsNoBids.remove(index);
                    listKeysNoBids.remove(index);
                    adapterNoBids.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRefPosts.addChildEventListener(cListener);
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
