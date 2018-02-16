package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    //added activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onClick(View v){

        FirebaseAuth.getInstance().signOut();
    }

    //links button on homepage to the create post activity
    public void goToAnActivity(View view) {
        Intent Intent = new Intent(this, CreatePostActivity.class);
        startActivity(Intent);
    }


}
