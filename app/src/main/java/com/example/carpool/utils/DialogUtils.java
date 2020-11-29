package com.example.carpool.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.carpool.R;

import java.util.logging.Level;
import java.util.logging.Logger;


public final class DialogUtils {

    private static DialogUtils dialogUtils = null;

    private DialogUtils() {
    }

    public static DialogUtils getDialogUtilsInstance() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }


    /**
     * to create and return the dialog
     *
     * @param context context as parameter
     * @return dialog object
     */
    public Dialog progressDialog(Context context) {
        Dialog dialog = null;
        try {
            dialog = new Dialog(context);
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_progress_dialog);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Logger.getLogger(DialogUtils.class.getName()).log(Level.SEVERE, null, e);
        }
        return dialog;
    }
}
