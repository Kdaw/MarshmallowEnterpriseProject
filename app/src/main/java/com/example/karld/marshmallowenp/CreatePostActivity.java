package com.example.karld.marshmallowenp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    //todo add comments and manage readability
    //todo Finish CreatePostActivity
    //create post page that is linked from home screen to allow user to create post
    //below is help and roughly what to do


    private DatabaseReference mRef;


    String title;
    String details;
    String location;


    EditText titleInput;
    EditText detailInput;
    EditText locationInput;

    Button postButton;


    /**
     * getUserID gets the Firebase ID from the database of the logged in user
     * and returns the ID as a String
     * @return
     */
    public static String getUserID()
    {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        return user.getUid().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        titleInput = (EditText) findViewById(R.id.editText_Title);
        detailInput = (EditText) findViewById(R.id.editText_Details);
        locationInput = (EditText) findViewById(R.id.editText_Location);

        postButton = (Button) findViewById(R.id.button_create_post);
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                mRef =  FirebaseDatabase.getInstance().getReference().child("Posts").push();
                title = titleInput.getText().toString();
                details = detailInput.getText().toString();
                location = locationInput.getText().toString();

                mRef.child("title").setValue(title);
                mRef.child("details").setValue(details);
                mRef.child("location").setValue(location);
                mRef.child("User").setValue(getUserID());
            }
        });
    }
}

//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
//Writing to database
//https://stackoverflow.com/questions/39536517/write-new-data-in-android-firebase-database
//https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/
// com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41