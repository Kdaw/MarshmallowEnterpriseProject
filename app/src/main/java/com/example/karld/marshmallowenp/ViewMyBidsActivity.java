package com.example.karld.marshmallowenp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewMyBidsActivity extends AppCompatActivity {


    //region Variables
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
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_bids);


        //todo steal code from the view available jobs activity from karl

        //stolen variables required

        //todo start at line 99



    }



}
