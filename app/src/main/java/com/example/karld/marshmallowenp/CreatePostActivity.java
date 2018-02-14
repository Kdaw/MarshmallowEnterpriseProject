package com.example.karld.marshmallowenp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener{

    //todo Get CreatePostActivity to write to the database
    //todo Get correct data from gareth of what needs to be in post
    //todo Use karl's authentication for the user info for foreign key
    //todo Decide how to layout data within database
    //todo Finish CreatePostActivity
    //create post page that is linked from home screen to allow user to create post
    //below is help and roughly what to do
    //https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/
    // com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41


    public class Post
    {
        public String postTitle;
        public String postDescription;
        public String PostOwner; //foreign key (kinda)


        //Default Constructor
        public Post()
        {

        }

        //Constructor with parameters
        public Post(String title, String description, String owner)
        {
            title = postTitle;
            description = postDescription;
            owner = PostOwner;
        }

        public String getPostTitle()
        {
            return postTitle;
        }

        public void setPostTitle(String title)
        {
            title = postTitle;
        }

        public String getPostDescription()
        {
            return postDescription;
        }

        public void setPostDescription(String description)
        {
            description = postDescription;
        }

        public String getPostOwner()
        {
            return PostOwner;
        }

        public void setPostOwner(String owner)
        {
            owner = PostOwner;
        }
    }


    /*
    method with case statement to check for all potential login types
    calls get ID method associated with each login type
    for database management to link posts to user
     */
    public static String getUserID()
    {
        //todo make this method work off the firebase user id to actually check...
        //what is being used and get the ID instead of being hard coded
        //return "TestUser1";


        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        Button postButton;
        //todo - figure out symbol issue - FIXED
        postButton = (Button) findViewById(R.id.CreatePostButton);
        postButton.setOnClickListener(CreatePostActivity.this);
    }


    @Override
    public void onClick(View v)
    {

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        Post post = new Post("TestPost1","TestDescription1",getLoginID());

        //todo - add validation to prevent duplication of users in database - potentially not the way i'll be doing it

        DatabaseReference mRef =  database.getReference().child("Posts")/*.child(getLoginID)*/;
        mRef.setValue(post);
    }
}



//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
//Writing to database
//https://stackoverflow.com/questions/39536517/write-new-data-in-android-firebase-database