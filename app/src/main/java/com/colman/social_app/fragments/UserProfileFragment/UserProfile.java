package com.colman.social_app.fragments.UserProfileFragment;

import static android.app.Activity.RESULT_OK;
import static com.colman.social_app.constants.Constants.PICK_IMAGE_REQUEST_CODE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colman.social_app.R;
import com.colman.social_app.services.utils.DialogUtils;
import com.colman.social_app.services.utils.ImageUtils;
import com.colman.social_app.views.activities.Login;
import com.colman.social_app.views.activities.MainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class UserProfile extends Fragment {
    ImageView profileIV;
    ImageButton editPasswordIB;
    ImageButton editNameIB;
    ImageButton editEmailIB;
    ImageUtils iu;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        iu = new ImageUtils(getActivity());
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (user != null) {
            profileIV = v.findViewById(R.id.profileIV);
            TextView email = v.findViewById(R.id.emailTV);
            email.setText(user.getEmail());
            TextView fullName = v.findViewById(R.id.full_nameTV);
            fullName.setText(user.getDisplayName());
            Picasso.get().load(user.getPhotoUrl()).into(profileIV);
        } else {
            startActivity(new Intent(v.getContext(), Login.class));
        }
        v.findViewById(R.id.editNameIB).setOnClickListener(this::onClickEditName);
        v.findViewById(R.id.editPasswordIB).setOnClickListener(this::onClickEditPassword);
        profileIV.setOnClickListener(this::onClickEditImage);
        return v;
    }

    private void onClickEditName(View view) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_name, null);
        AlertDialog nameDialog = DialogUtils.dialog(this.getContext(), dialogView);
        Button change = dialogView.findViewById(R.id.change_button);
        EditText emailET = dialogView.findViewById(R.id.emailET);
        EditText full_nameET = dialogView.findViewById(R.id.full_nameET);
        EditText passwordET = dialogView.findViewById(R.id.passwordET);
        change.setOnClickListener(v -> {
            AuthCredential credential = EmailAuthProvider.getCredential(emailET.getText().toString(), passwordET.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(auth -> {
                if (auth.isSuccessful()) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(full_nameET.getText().toString())
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(updateProfile -> {
                                if (updateProfile.isSuccessful()) {
                                    Toast.makeText(this.getContext(), "Updated name", Toast.LENGTH_SHORT).show();
                                }
                            });
                    nameDialog.dismiss();
                    Log.d("TAG", "onClickEditName: changed ");
                } else {
                    Toast.makeText(this.getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
        nameDialog.show();
    }

    private void onClickEditPassword(View view) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_password, null);
        AlertDialog passwordDialog = DialogUtils.dialog(this.getContext(), dialogView);
        Button change = dialogView.findViewById(R.id.change_button);
        EditText emailET = dialogView.findViewById(R.id.emailET);
        EditText old_passwordET = dialogView.findViewById(R.id.old_passwordET);
        EditText new_passwordET = dialogView.findViewById(R.id.new_passwordET);
        change.setOnClickListener(v -> {
            String em = emailET.getText().toString();
            String pw = old_passwordET.getText().toString();
            AuthCredential credential = EmailAuthProvider.getCredential(emailET.getText().toString(), old_passwordET.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(auth -> {
                if (auth.isSuccessful()) {
                    user.updatePassword(new_passwordET.getText().toString()).addOnCompleteListener(t -> {
                                if (t.isSuccessful()) {
                                    passwordDialog.dismiss();
                                    Toast.makeText(this.getContext(), "Updated password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("Profile", t.getException().getMessage());
                                    emailET.setError("Invalid Password");
                                }
                            }
                    );
                } else {
                    Log.d("Profile", auth.getException().getMessage());

                    Toast.makeText(this.getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
        passwordDialog.show();
    }

    private void onClickEditImage(View view) {
        iu.selectImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                profileIV.setImageURI(imageUri);
                iu.uploadToStorage(imageUri, (t -> {
                    if (t.isSuccessful()) {
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(t.getResult().toString()))
                                    .build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(finishedTask -> {
                                if (finishedTask.isSuccessful()) {
                                    Toast.makeText(this.getContext(), "Updated profile Image", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(this.getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                }));
            } else {
                Toast.makeText(this.getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}