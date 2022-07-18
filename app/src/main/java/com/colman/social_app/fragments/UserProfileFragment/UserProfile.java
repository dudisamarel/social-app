package com.colman.social_app.fragments.UserProfileFragment;

import static android.app.Activity.RESULT_OK;
import static com.colman.social_app.constants.Constants.PICK_MEDIA_REQUEST_CODE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.services.utils.DialogUtils;
import com.colman.social_app.services.utils.ImageUtils;
import com.colman.social_app.views.activities.Login;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class UserProfile extends Fragment {
    ImageView profileIV;
    UserProfileViewModel viewModel;
    FirebaseUser user;
    TextView emailTV;
    TextView fullNameTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelFactory factory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(UserProfileViewModel.class);
        user = viewModel.getUser();
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (user != null) {
            profileIV = v.findViewById(R.id.profileIV);
            emailTV = v.findViewById(R.id.emailTV);
            fullNameTV = v.findViewById(R.id.full_nameTV);
            emailTV.setText(user.getEmail());
            fullNameTV.setText(user.getDisplayName());
            Picasso.get().load(user.getPhotoUrl()).into(profileIV);
        } else {
            Intent intent = new Intent(v.getContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        v.findViewById(R.id.editNameIB).setOnClickListener(this::onClickEditName);
        v.findViewById(R.id.editPasswordIB).setOnClickListener(this::onClickEditPassword);
        profileIV.setOnClickListener(this::onClickEditImage);
        v.findViewById(R.id.logoutBtn).setOnClickListener(view -> {
            viewModel.signOut();
            Intent intent = new Intent(v.getContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
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
            viewModel.userReAuth(emailET.getText().toString(), passwordET.getText().toString(), auth -> {
                if (auth.isSuccessful()) {
                    viewModel.updateName(full_nameET.getText().toString(), nameTask -> {
                        if (nameTask.isSuccessful()) {
                            fullNameTV.setText(full_nameET.getText().toString());
                            Toast.makeText(this.getContext(), "Updated name", Toast.LENGTH_SHORT).show();
                        }
                        nameDialog.dismiss();
                    });
                    Log.d("Profile", "name changed ");
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
            String email = emailET.getText().toString();
            String password = old_passwordET.getText().toString();
            String newPassword = new_passwordET.getText().toString();
            viewModel.userReAuth(email, password, auth -> {
                if (auth.isSuccessful()) {
                    viewModel.updatePassword(newPassword, t -> {
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
        ImageUtils.selectImage(this.getContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ProgressDialog dialog = new ProgressDialog(this.getContext());
            dialog.setMessage("Loading Image");
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
            Uri imageUri = data.getData();
            if (imageUri != null) {
                String imageName = UUID.randomUUID().toString() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(getActivity().getContentResolver().getType(imageUri));
                viewModel.uploadImage(imageName, imageUri, t -> {
                    if (t.isSuccessful()) {
                        viewModel.setNewImage(t.getResult().toString(), finishedTask -> {
                            if (finishedTask.isSuccessful()) {
                                profileIV.setImageURI(imageUri);
                                Toast.makeText(this.getContext(), "Updated profile Image", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(this.getContext(), "Failed updating profile Image", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(this.getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                dialog.dismiss();
                Toast.makeText(this.getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}