package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ViewMyBidsActivity extends AppCompatActivity {


    //region Variables
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private ArrayList<String> openListItems = new ArrayList<>();
    private ArrayList<String> openListKeys = new ArrayList<>();

    private ArrayList<String> acceptedListItems = new ArrayList<>();
    private ArrayList<String> acceptedListKeys = new ArrayList<>();

    private ArrayAdapter<String> openAdapter;
    private ArrayAdapter<String> acceptedAdapter;

    private ListView openJobs;
    private ListView acceptedJobs;

    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference("Bids");

    private int itemSelectedOpen = 0;
    private int itemSelectedAccepted = 0;

    private String[] openJobID = new String[1000];
    private String[] acceptedJobID = new String[1000];
    public static final String MESSAGE = "message";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_bids);

        //todo steal code from the view available jobs activity from karl

        //stolen variables required

        //todo start at line 130

        Intent intent = new Intent(this, ViewMyBidsActivity.class);

        openJobs = (ListView) findViewById(R.id.ListView_viewMyOpenBids);
            openAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, openListItems);
            openJobs.setAdapter(openAdapter);
            openJobs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        acceptedJobs = (ListView) findViewById(R.id.ListView_viewMyAcceptedBids);
            acceptedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, acceptedListItems);
            acceptedJobs.setAdapter(acceptedAdapter);
            acceptedJobs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);





        addOpenChildEventListener();

        //addAcceptedChildEventListener();

        acceptedJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), ViewAvailableJobDetailsActivity.class);
                System.out.println("USE FOR TESTING WHEN I UNDERSTAND WHATS GOING ON");
                String ident = acceptedJobID[position];
                intent.putExtra("id", ident);
                startActivity(intent);
            }
        });

//        acceptedJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//                Intent intent = new Intent(getApplicationContext(), ViewAvailableJobDetailsActivity.class);
//                System.out.println("USE FOR TESTING WHEN I UNDERSTAND WHATS GOING ON");
//                String ident = acceptedJobID[position];
//                intent.putExtra("id", ident);
//                startActivity(intent);
//            }
//        });

    }


    /**
     * Child event Listener for the Open Jobs list view
     */
    //todo MAKE THIS WORK FOR WHAT I NEED IT TO -- NEEDS MAJOR CHANGING -- EG LINE 130 IN VIEWAVAILABLEJOBSACTIVITY

    private void addOpenChildEventListener() {
        ChildEventListener cListener = new ChildEventListener() {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    acceptedJobID[itemSelectedOpen] = key;
                    System.out.println(openJobID[itemSelectedOpen]);
                    String name = ds.getKey();
                    openListKeys.add(name);
                    if(dataSnapshot.child("BidderID").getValue(String.class).equals(currentUser.getUid())
                            && dataSnapshot.child("Accepted").getValue(boolean.class) == true)
                    {
                        if(name.equals("PostTitle")) {
                            openAdapter.add(dataSnapshot.child(name).getValue(String.class));
                            itemSelectedOpen++;
                        }
                    }

                }

                openListKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //code from www.techotopia.com/index.php/A_Firebase_Realtime_Database_List_Data_Tutorial
                //deals with removal from database to update snapshot
                String key = dataSnapshot.getKey();
                int index = openListKeys.indexOf(key);

                if (index != -1) {
                    openListItems.remove(index);
                    openListKeys.remove(index);
                    openAdapter.notifyDataSetChanged();
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




}




