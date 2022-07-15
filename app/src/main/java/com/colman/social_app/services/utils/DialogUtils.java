package com.colman.social_app.services.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.colman.social_app.R;

public class DialogUtils {
    public static AlertDialog dialog(Context context, View dialogView) {
        Button cancel = dialogView.findViewById(R.id.cancel_button);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();
        cancel.setOnClickListener(v -> alertDialog.dismiss());
        return alertDialog;
    }
}
