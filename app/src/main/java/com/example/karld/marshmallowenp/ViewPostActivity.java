package com.example.karld.marshmallowenp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ViewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        String pushID = getIntent().getStringExtra("POST_ID");       //should hopefully catch the postID
            // Toast.makeText(ViewPostActivity.this, pushID,
            // Toast.LENGTH_LONG).show();

//todo code from profile settings to read from database, needs a lot of changing

//          FirebaseAuth mAuth = FirebaseAuth.getInstance();
//          FirebaseUser currentUser = mAuth.getCurrentUser();
//


//          DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Email");
//
//          final TextView vEmail = findViewById(R.id.textview_email);
//          final String email = mDatabaseUsers.getKey();
//          vEmail.setText(email);


//          mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(DataSnapshot dataSnapshot) {
//           String value = dataSnapshot.getValue(String.class);
//           vEmail.setText(value);
//           }
//           @Override
//           public void onCancelled(DatabaseError databaseError) {
//           }



    };
}


