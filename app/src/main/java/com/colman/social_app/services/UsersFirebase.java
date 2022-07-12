package com.colman.social_app.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.colman.social_app.entities.User;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UsersFirebase {
    private static final UsersFirebase instance = new UsersFirebase();
    private final FirebaseAuth mAuth;
    private final String TAG = "UsersFirebase";

    public UsersFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static UsersFirebase getInstance() {
        return instance;
    }

    public void register(Context context, User user) {
        if (isValid(user)) {
            Log.d(TAG, "adding");
            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Created Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(context, "Weak Password", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(context, "User is already exists", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        } else
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
    }

    public void login(Context context, String email, String password) {
        if (isValid(new User(email, password, null, null))) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Invalid email address or password", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public FirebaseUser getUser() {
        return this.mAuth.getCurrentUser();
    }



    public void updatePassword(String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword);
        }
    }


    private boolean isValid(User user) {
        return !user.getEmail().isEmpty() && !user.getPassword().isEmpty();
    }
}


