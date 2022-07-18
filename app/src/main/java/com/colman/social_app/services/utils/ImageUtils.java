package com.colman.social_app.services.utils;

import static com.colman.social_app.constants.Constants.PICK_MEDIA_REQUEST_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ImageUtils {
    public static void selectMedia(String mediaType, Context context) {
        Intent intent = new Intent();
        intent.setType(mediaType);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Media from here..."),
                PICK_MEDIA_REQUEST_CODE);
    }

    public static void selectImage(Context context) {
        selectMedia("image/*", context);
    }

    public static void selectImageOrVideo(Context context) {
        selectMedia("*/*" , context);
    }
}
