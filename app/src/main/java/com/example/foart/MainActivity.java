package com.example.foart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foart.models.Constants;
import com.example.foart.models.Viewer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://views-on-sports.firebaseio.com/");
    DatabaseReference viewersReference = mDatabase.getReference("viewers");

    FirebaseStorage mStorage = FirebaseStorage.getInstance("gs://views-on-sports");
    //FirebaseStorage mStorage = FirebaseStorage.getInstance();
    /*
        * Types of files:
        * 1) Images
        *   - avatars/userId,
        *   - logos
        *       # teams/teamId,
        *       # leagues/leagueId,
        *       # sportsCodes/codeId
        * 2) Videos
        *   - posts/postId
     */

    ImageView userAvatar;
    int PICK_IMAGE = 1;
    Uri imageUri = null;
    Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAvatar = (ImageView) findViewById(R.id.imageView);

        // check if this is a new user or device
        final String viewerID = getStringFromLocalDevice(Constants.VIEWER_ID);

        if(viewerID == null) {
            Viewer mViewer = new Viewer("", "xman",
                    "xman@email.com",
                    "0811231234",
                    "https://google.com");
            addNewViewer(mViewer);
        }

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null && viewerID != null) {
                    String destinationFolder = "avatars/" + viewerID;
                    uploadFileToStorage(destinationFolder, imageUri);
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();

            try {
                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                userAvatar.setImageBitmap(mBitmap);
                imageBitmap = mBitmap;
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void addNewViewer(final Viewer mViewer) {
        Map<String, Object> viewerValues = mViewer.toMap();

        // get viewer's newly created ID
        final String viewerKey = viewersReference.push().getKey();
        mViewer.setId(viewerKey);

        viewersReference.child(viewerKey).setValue(viewerValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        saveStringToLocalDevice(Constants.VIEWER_ID, viewerKey);
                        Toast.makeText(MainActivity.this,
                                "Viewer added successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,
                                "Error: couldn't add viewer", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getViewerdata(String viewerID) {
        viewersReference.child(viewerID).addValueEventListener(singleViewerListener);
    }

    public void getAllViewers() {
        viewersReference.addChildEventListener(childViewerListener);
    }

    public void uploadFileToStorage(String destinationFolder, Uri fileUri) {
        Uri file = fileUri;
        if(file == null) {return;}

        // create reference to where file will be stored in the storage bucket
        final StorageReference avatarsReference = mStorage.getReference().child(destinationFolder + "/" +
                file.getLastPathSegment());

        // initiate an upload task for the specified file
        UploadTask mUploadTask = avatarsReference.putFile(file);

        // register observers to listen for upload progress
        Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()) {
                    throw task.getException();
                }

                // continue to get download url
                return avatarsReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();

                    // make use of the url: e.g. update avatar link in database
                    // Note: when avatar is successfully saved in database,
                    // save its encoded-to-base-64 bitmap in shared preferences so that
                    // it can be automatically loaded

                }
            }
        });
    }

    private ValueEventListener singleViewerListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String viewerID = dataSnapshot.getKey(); // get user id from datasnapshot key
            Viewer mViewer = dataSnapshot.getValue(Viewer.class);
            if(mViewer == null) {return;}
            // update id value of viewer object
            // (remember that its not initialized in the constructor)
            mViewer.setId(viewerID);
            Toast.makeText(MainActivity.this, "Nickame: " +
                    mViewer.getNickname(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w(TAG, "loadViewer:onCancelled", databaseError.toException());
        }
    };

    private ChildEventListener childViewerListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            // when child is created (onCreate() equivalent)
            String viewerID = dataSnapshot.getKey();
            Viewer mViewer = dataSnapshot.getValue(Viewer.class);
            mViewer.setId(viewerID);
            Toast.makeText(MainActivity.this, "key: " +
                    viewerID, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            // when child is updated (onUpdate() equivalent)
            String viewerID = dataSnapshot.getKey();
            Viewer mViewer = dataSnapshot.getValue(Viewer.class);
            mViewer.setId(viewerID);
            Toast.makeText(MainActivity.this, "key: " +
                    viewerID + ", new name: " + mViewer.getNickname(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            // when child is deleted (onDelete() equivalent)
            String viewerID = dataSnapshot.getKey();
            Toast.makeText(MainActivity.this, "removed: " +
                    viewerID, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void saveStringToLocalDevice(String key, String value) {
        SharedPreferences mPreferences = getSharedPreferences(
                Constants.PREFERENCES_FILE_NAME, MODE_PRIVATE);

        // get shared preferences editor
        SharedPreferences.Editor mEditor  = mPreferences.edit();
        // add/put value (in this case its a string) and commit
        mEditor.putString(key, value);
        mEditor.apply();
    }

    // inspired by Manish Srivastava (Stack Overflow)
    public void saveImageToLocalDevice(Bitmap img) {
        Bitmap image = img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        saveStringToLocalDevice(Constants.AVATAR_ENCODED, imageEncoded);
    }

    public String getStringFromLocalDevice(String key) {
        SharedPreferences mPreferences = getSharedPreferences(
                Constants.PREFERENCES_FILE_NAME, MODE_PRIVATE);

        return mPreferences.getString(key,null);
    }

    public Bitmap getImageFromLocalDevice(String key) {
        // get encoded string from local storage
        SharedPreferences mPreferences = getSharedPreferences(
                Constants.PREFERENCES_FILE_NAME, MODE_PRIVATE);

        String encodedString =  mPreferences.getString(key,null);
        if(encodedString == null) {return null;}

        // decode string from Base 64
        byte[] decodeByte = Base64.decode(encodedString, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }

    public void removeStringFromLocalDevice(String key) {
        SharedPreferences mPreferences = getSharedPreferences(
                Constants.PREFERENCES_FILE_NAME, MODE_PRIVATE);

        mPreferences.edit().remove(key).apply();
    }

}
