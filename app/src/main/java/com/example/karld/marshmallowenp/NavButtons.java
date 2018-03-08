package com.example.karld.marshmallowenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class NavButtons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_buttons);

    }

//    public static void logout() {
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent( getApplicationContext(), LoginActivity.class);
//        startActivity(intent);
//    }
}
