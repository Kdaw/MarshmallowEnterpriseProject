package com.example.karld.marshmallowenp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //todo-all of this
        //create post page that is linked from home screen to allow user to create post
        //below is help and roughly what to do
        //https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/NewPostActivity.java#L41-L41


    }

}
