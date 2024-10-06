package com.cst7335.lab03;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getSimpleName();  // Static String for logging
    private static final int REQUEST_CAMERA_PERMISSION = 100;  // Request code for camera permission
    private ImageView imgView;  // ImageView to display the captured picture
    private EditText emailInput, nameInput;
    private ActivityResultLauncher<Intent> myPictureTakerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Log onCreate
        Log.e(TAG, "In function: onCreate");

        // Initialize email input
        emailInput = findViewById(R.id.emailInput);

        // Get the Intent that started this activity and retrieve email passed from MainActivity
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra("EMAIL");

        // Set the email in the EditText field if available
        if (email != null) {
            emailInput.setText(email);
        } else {
            Log.e(TAG, "No email was passed in the Intent");
        }

        // Initialize other views
        nameInput = findViewById(R.id.nameInput);
        imgView = findViewById(R.id.imageView);  // ImageView to display captured image

        // Set up the ImageButton for taking a picture
        ImageButton pictureButton = findViewById(R.id.pictureButton);

        // Set up the click listener to dispatch the picture-taking intent
        pictureButton.setOnClickListener(v -> {
            Log.e(TAG, "Picture button clicked");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchTakePictureIntent();
            }
        });

        // Initialize ActivityResultLauncher for handling camera result
        myPictureTakerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e(TAG, "In function: onActivityResult");
                    if (result.getResultCode() == RESULT_OK) {
                        Log.e(TAG, "Camera result OK");
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null && data.getExtras().containsKey("data")) {
                            Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
                            if (imgBitmap != null) {
                                imgView.setImageBitmap(imgBitmap);  // Set the captured image in ImageView
                                Log.e(TAG, "Captured image set in ImageView");
                            } else {
                                Log.e(TAG, "Failed to capture the image");
                            }
                        } else {
                            Log.e(TAG, "No data returned from camera intent");
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        Log.i(TAG, "User refused to capture a picture.");
                    } else {
                        Log.e(TAG, "Unexpected result code: " + result.getResultCode());
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Log onStart
        Log.e(TAG, "In function: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log onResume
        Log.e(TAG, "In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log onPause
        Log.e(TAG, "In function: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Log onStop
        Log.e(TAG, "In function: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Log onDestroy
        Log.e(TAG, "In function: onDestroy");
    }

    // Method to dispatch the camera intent to take a picture
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Log the dispatch of the intent
            Log.e(TAG, "Dispatching take picture intent");
            myPictureTakerLauncher.launch(takePictureIntent);
        } else {
            Log.e(TAG, "No camera app found to handle the intent");
            Toast.makeText(this, "No camera app found to take a picture", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Camera permission granted");
                dispatchTakePictureIntent();
            } else {
                Log.e(TAG, "Camera permission denied");
                Toast.makeText(this, "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show();
            }
        }
    }
}