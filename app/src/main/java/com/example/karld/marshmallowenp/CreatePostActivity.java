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
        //todo
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        Button postButton;
        postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(CreatePostActivity.this);
    }


    @Override
    public void onClick(View v)
    {

        //read from text boxes and set to variable for parameter for new post
        FirebaseDatabase database =  FirebaseDatabase.getInstance();

//        FirebaseUser user =  mAuth.getCurrentUser();
//        String userId = user.getUid();

        Post post = new Post("TestPost1","TestDescription1");
        DatabaseReference mRef =  database.getReference().child("Posts")/*.child(getLoginID)*/;
        mRef.setValue(post);
    }
}



//button click listener
// https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio