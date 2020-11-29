package com.example.carpool.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.carpool.R;
import com.example.carpool.utils.DialogUtils;

public class BaseActivity extends AppCompatActivity {

    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingStatusBarColor();
    }

    private void settingStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showProgressBar(Context context) {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(context);
            } else {
                closeProgressbar();
                progressDialog = null;
                progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(context);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void closeProgressbar() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
