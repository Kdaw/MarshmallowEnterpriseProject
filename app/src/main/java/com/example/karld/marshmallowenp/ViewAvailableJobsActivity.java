package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAvailableJobsActivity extends AppCompatActivity {

    /**
     * Implementation of the following was done using the information found at
     * http://www.techotopia.com/index.php/A_Firebase_Realtime_Database_List_Data_Tutorial
     * This describes how to fill a list view from a Firebase Realtime Database
     */

    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<String> listKeys = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView availableJobs;
    private FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = fbDatabase.getReference("Posts");
    private int itemSelected;
    private String[] jobID = new String[1000];
    public static final String MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_jobs);

        Intent intent = new Intent(this, ViewAvailableJobDetailsActivity.class);

        availableJobs = (ListView) findViewById(R.id.ListView_AvailableJobs);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listItems);
        availableJobs.setAdapter(adapter);
        availableJobs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        availableJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = position;

            }
        });


        addChildEventListener();

        availableJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
            Intent intent = new Intent(getApplicationContext(), ViewAvailableJobDetailsActivity.class);
            System.out.println("ID just before adding to intent " + jobID[position]);
            String ident = jobID[position];
            intent.putExtra("id", ident);
            startActivity(intent);
            }
        });
    }

    private void addChildEventListener() {
        ChildEventListener cListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                itemSelected = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    jobID[itemSelected] = key;
                    System.out.println(jobID[itemSelected]);
                    String name = ds.getKey();
                    listKeys.add(name);
                    if(name.equals("title"))
                        adapter.add(dataSnapshot.child(name).getValue(String.class));
                    itemSelected++;
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
}