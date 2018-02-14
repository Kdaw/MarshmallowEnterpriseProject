package com.example.karld.marshmallowenp;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener{

    //todo-all of this
    //create post page that is linked from home screen to allow user to create post
    //below is help and roughly what to do
    //https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/
    // com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41


    public class Post
    {
        public String postTitle;
        public String postDescription;


        //Default Constructor
        public post()
        {

        }

        //Constructor with parameters
        public post(String title, String description)
        {
            this.title = postTitle;
            this.description = postDescription;
        }

        public String getPostTitle()
        {
            return postTitle;
        }

        public void setPostTitle(String title)
        {
            this.title = postTitle;
        }

        public String getPostDescription()
        {
            return postDescription;
        }

        public void setPostDescription(String description)
        {
            this description = postDescription;
        }
    }


    /*
    method with case statement to check for all potential login types
    calls get ID method associated with each login type
    for database management to link posts to user
     */
    public static void getLoginID
    {
        //todo - hard code in user values for now so this method can be used in onclick
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        Button postButton;
        //todo - figure out symbol issue
        postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(CreatePostActivity.this);
    }


    @Override
    public void onClick(View v)
    {

        //read from text boxes and set to variable for parameter for new post
        FirebaseDatabase database =  FirebaseDatabase.getInstance();

        //this is where the onclick method would call the getloginId method to get the correct id
        // depending on login type, ie google, email etc
        //would then be saved to be passed through to the .child below so that posts are linked beneath user
//        FirebaseUser user =  mAuth.getCurrentUser();
//        String userId = user.getUid();

        Post post = new Post("TestPost1","TestDescription1");

        //todo - add validation to prevent duplication of users in database - potenially not the way i'll be doing it
        //todo - pass userid through as a column in the database instead of a heading in db
        // will reduce post load times and might be neater
        // think more about how to layout the posts in the database
        DatabaseReference mRef =  database.getReference().child("Posts")/*.child(getLoginID)*/;
        mRef.setValue(post);
    }
}



//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio