package com.example.karld.marshmallowenp;

import android.content.Intent;
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



    //region Instance Variables
    private DatabaseReference mRef;

    String title;
    String details;
    String dropoff;
    String pickup;
    String distance;

    //Exists to remove this text from the beginning of the push link so when saving the postID from the push all we have is the ID itself
    String removeLink = "https://enpmarshmallow.firebaseio.com/Posts/";

    EditText titleInput;
    EditText detailInput;
    EditText PickupLocationInput;
    EditText DropoffLocationInput;
    EditText DistanceInput;

    Button postButton;
    //endregion


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
        PickupLocationInput = (EditText) findViewById(R.id.editText_Pickup);
        DropoffLocationInput = (EditText) findViewById(R.id.editText_Dropoff);
        DistanceInput = (EditText) findViewById(R.id.editText_Distance);

        postButton = (Button) findViewById(R.id.button_create_post);
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                mRef =  FirebaseDatabase.getInstance().getReference().child("Posts").push();


                //region Save push()value for postID
                String fullPostID = mRef.toString();
                    //this saves the push link with the firebase link before it, which needs removing
                String postID = fullPostID.replace(removeLink, "");
                    //this removes the firebase link and leaves postID as just the value required


//              Toast.makeText(CreatePostActivity.this, postID,
//              Toast.LENGTH_LONG).show();
                //endregion

                title = titleInput.getText().toString();
                details = detailInput.getText().toString();
                pickup = PickupLocationInput.getText().toString();
                dropoff = DropoffLocationInput.getText().toString();
                distance = DistanceInput.getText().toString();


                mRef.child("title").setValue(title);
                mRef.child("details").setValue(details);
                mRef.child("pickup").setValue(pickup);
                mRef.child("dropoff").setValue(dropoff);
                mRef.child("distance").setValue(distance);
                mRef.child("User").setValue(getUserID());

                //region Intent to send pushID
                Intent intent = new Intent(getBaseContext(), ViewPostActivity.class);
                intent.putExtra("POST_ID", postID);
                startActivity(intent);
                //endregion
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