package com.colman.social_app.views.activities;

import static com.colman.social_app.constants.Constants.PICK_IMAGE_REQUEST_CODE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.colman.social_app.R;
import com.colman.social_app.services.utils.ImageUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email;
    EditText fullName;
    EditText password;
    Uri imageUri;
    ImageUtils imageUtils;
    ImageView profileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageUtils = new ImageUtils(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailET);
        fullName = findViewById(R.id.full_nameET);
        password = findViewById(R.id.passwordET);
        profileIV = findViewById(R.id.profileIV);
        Button signUpBtn = findViewById(R.id.signupBtn);
        TextView signInTV = findViewById(R.id.signInTV);
        mAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener((View v) -> {
            createUser();
        });
        signInTV.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        Button selectImageButton = findViewById(R.id.selectImageBtn);
        selectImageButton.setOnClickListener(v -> {
            imageUtils.selectImage();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                profileIV.setImageURI(imageUri);

            } else {
                Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createUser() {
        String emailString = email.getText().toString();
        String fullNameString = fullName.getText().toString();
        String passwordString = password.getText().toString();
        if (fullNameString.isEmpty()) {
            fullName.setError("Please enter your full name");
            fullName.requestFocus();
        } else if (emailString.isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (passwordString.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Upload To Storage
                    imageUtils.uploadToStorage(imageUri, (t -> {
                        if (t.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullNameString)
                                        .

                                        setPhotoUri(Uri.parse(t.getResult().toString()))
                                        .build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(finishedTask -> {
                                    if (finishedTask.isSuccessful()) {
                                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, MainActivity.class));
                                    }
                                });
                            }
                        } else if (!t.isSuccessful()) {
                            Log.d("Register", "uploadToStorage: " + Objects.requireNonNull(task.getException()).getMessage());
                            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }));
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        password.setError("Password needs to contain at least 6 characters");
                        password.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        email.setError("Invalid email");
                        email.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        email.setError("Email Already exists");
                        email.requestFocus();
                        email.requestFocus();
                    } catch (Exception e) {
                        Toast.makeText(this, "Some error occurred, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}


