package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RankingActivity extends AppCompatActivity {

    private ListView rankingPosition;
    private FirebaseDatabase rankingDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rankingref = rankingDatabase.getReference("Review");
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }

    Intent intent = new Intent(this, RankingActivity.class);


}
