package com.colman.social_app.fragments;

import static com.colman.social_app.constants.Constants.PICK_IMAGE_REQUEST_CODE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


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
        FirebaseAuth.getInstance().signOut();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_name, null);
        AlertDialog nameDialog = DialogUtils.dialog(this.getContext(), dialogView);
        Button change = dialogView.findViewById(R.id.change_button);
        EditText emailET = dialogView.findViewById(R.id.emailET);
        EditText old_passwordET = dialogView.findViewById(R.id.old_passwordET);
        EditText new_passwordET = dialogView.findViewById(R.id.new_passwordET);
        change.setOnClickListener(v -> {
            AuthCredential credential = EmailAuthProvider.getCredential(emailET.getText().toString(), old_passwordET.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(auth -> {
                if (auth.isSuccessful()) {
                    user.updatePassword(new_passwordET.getText().toString()).addOnCompleteListener(t -> {
                                if (t.isSuccessful()) {
                                    nameDialog.dismiss();
                                    Log.d("TAG", "onClickEditEmail:changed ");
                                } else {
                                    Log.d("Profile", t.getException().getMessage());
                                    emailET.setError("Invalid mail");
                                }
                            }
                    );
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
                                    Log.d("TAG", "onClickEditEmail:changed ");
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

}