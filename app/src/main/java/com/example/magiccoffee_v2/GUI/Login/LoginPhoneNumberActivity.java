package com.example.magiccoffee_v2.GUI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.magiccoffee_v2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginPhoneNumberActivity extends AppCompatActivity {
    private Button btnLogin;
    private CheckBox cbOk;
    private TextInputEditText edtPhoneNumber;
    private TextInputLayout edtPhoneNumberLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        btnLogin = findViewById(R.id.btn_next);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPhoneNumberLayout = findViewById(R.id.edtPhoneNumberLayout);
        cbOk = findViewById(R.id.cbOk);

        btnLogin.setOnClickListener(view -> {
            if(validatePhoneNumber() && validateCheckBox())
            {
                Intent intent = new Intent(LoginPhoneNumberActivity.this, VerificationActivity.class);
                intent.putExtra("PhoneNumber", edtPhoneNumber.getText());
                startActivity(intent);
                finish();
            }
        });
        cbOk.setOnClickListener(view -> {
            if(cbOk.isChecked())
                cbOk.setError(null);
        });
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtPhoneNumberLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean validateCheckBox() {
        if(cbOk.isChecked()) return true;
        else{
            cbOk.setError("Phải đồng ý điều khoản điều kiện");
            return false;
        }
    }

    private boolean validatePhoneNumber() {
        if(edtPhoneNumber.getText().toString().trim().equals("")){
            edtPhoneNumberLayout.setError("Số điện thoại không được để trống");
            return false;
        }
        if(edtPhoneNumber.getText().toString().trim().length() < 10){
            edtPhoneNumberLayout.setError("Số điện thoại không đúng định dạng");
            return false;
        }
        return true;
    }
}