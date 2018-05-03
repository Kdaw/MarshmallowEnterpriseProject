package com.example.karld.marshmallowenp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileSettingsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid()).child("Email");

        final TextView vEmail = findViewById(R.id.textview_email);
        final String email = mDatabaseUsers.getKey();
        vEmail.setText(email);
        mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                vEmail.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //set user and email
        String uEmail = currentUser.getEmail();
        String uName = currentUser.getDisplayName();


        // Slider Menu Code ----------------------------------------------------------------------------------------------
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Nav Menu linking - Links Activities From Nav Menu ---------------------------------------------------------------
        NavigationView nV = (NavigationView) findViewById(R.id.nav_menu);
        TextView txtProfileName = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavUser);
        txtProfileName.setText(uName);
        TextView txtProfileEmail = (TextView) nV.getHeaderView(0).findViewById(R.id.textView_NavEmail);
        txtProfileEmail.setText(uEmail);
        nV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks.
                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_create_post) {
                    Intent in = new Intent(getApplicationContext(), CreatePostActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_view_jobs) {
                    Intent in = new Intent(getApplicationContext(), ViewAvailableJobsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_account) {
                    Intent in = new Intent(getApplicationContext(), ProfileSettingsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_settings) {
                    Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(in);
                } else if (id == R.id.nav_Logout) {
                    //todo figure a signout method that signs out locally
                    //signOut();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_my_jobs) {
                    Intent in = new Intent(getApplicationContext(), ViewActiveJobsWithBidsActivity.class);
                    startActivity(in);
                }
                return true;
            }
        });

        // Profile Picture Uploads ---------------------------------------------------------------

//        mStorageRef = FirebaseStorage.getInstance().getReference("UserPictures").child(currentUser.getUid());
//
//
//        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openFileChooser();
//
//            }
//        });
//        mButtonUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                uploadFile();
//
//            }
//        });
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef;

            mUploadTask= fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(ProfileSettingsActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            ImageUpload upload = new ImageUpload(mEditTextFileName.getText().toString().trim(),
//                                    taskSnapshot.getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileSettingsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else  {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();

        }
    }


    public void openJobHistory(View v){
        Intent intent = new Intent(this, MyJobsHistoryActivity.class);
        startActivity(intent);
    }

    public void openBidHistory(View v){
        Intent intent = new Intent(this, MyBidsHistoryActivity.class);
        startActivity(intent);
    }
    private void openImagesActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // Enables Nav menu click -  Allows for both slide and on click access.
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
