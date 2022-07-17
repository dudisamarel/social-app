package com.colman.social_app.services.utils;

import static com.colman.social_app.constants.Constants.PICK_IMAGE_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ImageUtils {
    private static final String TAG = "ImageUtils";
    Activity activity;

    public ImageUtils(Activity activity) {
        this.activity = activity;
    }

    public void uploadToStorage(Uri imageUri, OnCompleteListener<Uri> listener) {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("photos");
            String imageName = UUID.randomUUID().toString() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.getApplicationContext().getContentResolver().getType(imageUri));
            final StorageReference imageRef = storageReference.child(imageName);
            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.continueWithTask(task -> imageRef.getDownloadUrl()).addOnCompleteListener(listener);
        }
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST_CODE);
    }
}

