package com.example.karld.marshmallowenp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    //todo Get CreatePostActivity to write to the database
    //todo Get correct data from gareth of what needs to be in post
    //todo Use karl's authentication for the user info for foreign key
    //todo Decide how to layout data within database
    //todo Finish CreatePostActivity
    //create post page that is linked from home screen to allow user to create post
    //below is help and roughly what to do
    //https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/
    // com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41



    /*
    method with case statement to check for all potential login types
    calls get ID method associated with each login type
    for database management to link posts to user
     */
    public static String getUserID()
    {
        //todo make this method work off the firebase user id to actually check...
        //what is being used and get the ID instead of being hard coded
        return "TestUser1";


//        FirebaseUser user =  mAuth.getCurrentUser();
//        String userId = user.getUid();
    }


    private DatabaseReference mRef;

    String title;
    String details;
    String location;


    EditText titleInput;
    EditText detailInput;
    EditText locationInput;

    Button postButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
//        postButton.setOnClickListener(CreatePostActivity.this);
        //new View.OnClickListener()

//        //todo - figure out symbol issue - FIXED

        titleInput = (EditText) findViewById(R.id.editText_Title);
        detailInput = (EditText) findViewById(R.id.editText_Details);
        locationInput = (EditText) findViewById(R.id.editText_Location);


        postButton = (Button) findViewById(R.id.CreatePostButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title = titleInput.getText().toString();
                details = detailInput.getText().toString();
                location = locationInput.getText().toString();

                showToast(title);
                showToast(details);
                showToast(location);

                Post newPost = Post.CreatePost("0001", title, details, location);



                mRef =  FirebaseDatabase.getInstance().getReference();
                //todo get this working for the random id to create post values under
               // String postid = mRef.push().toString();
                String postid = "ikxsfbgksjdgb";
                mRef.child("Posts").setValue(postid);
                mRef.child("Posts").child(postid).child("title").setValue(title);
                mRef.child("Posts").child(postid).child("details").setValue(details);
                mRef.child("Posts").child(postid).child("location").setValue(location);





                //mRef.setValue(Post.CreatePost("0001", title, details, location));
            }
        });


    }

    private void showToast(String text)
    {
        Toast.makeText(CreatePostActivity.this, text, Toast.LENGTH_SHORT).show();
    }



//
//        FirebaseDatabase database =  FirebaseDatabase.getInstance();
//        Post newPost = new Post("TestPost1","TestDescription1","TestUser1");
//
//        //todo - add validation to prevent duplication of users in database - potentially not the way i'll be doing it
//
//        DatabaseReference mRef =  database.getReference().child("Posts");
//        mRef = mRef.push();
//        mRef.setValue("test");
//
//        DatabaseReference mRef =  database.getReference().child("Posts").child("post title");
//        mRef.setValue("test post title");
//
//        mRef =  database.getReference().child("testUser1").child("post description");
//        mRef.setValue("test description");
}

//.child("TestUser1")

//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
//Writing to database
//https://stackoverflow.com/questions/39536517/write-new-data-in-android-firebase-database