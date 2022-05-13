package com.example.magiccoffee_v2.GUI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.magiccoffee_v2.R;

public class CustomDialogClass extends Dialog{

    public Button ok, cancel;

    public CustomDialogClass(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ok = (Button) findViewById(R.id.btn_ok);
        cancel = (Button) findViewById(R.id.btn_cancel);
    }
}