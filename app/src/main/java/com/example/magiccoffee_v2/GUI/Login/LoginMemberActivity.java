package com.example.magiccoffee_v2.GUI.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Login;
import com.example.magiccoffee_v2.DTO.Member;
import com.example.magiccoffee_v2.GUI.Drawer.DrawerActivity;
import com.example.magiccoffee_v2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMemberActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextInputEditText txtUsername, txtPass;
    private CheckBox cbAdmin;
    private TextInputLayout txtUsernameLayout, txtPasswordLayout;
    //    Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_member);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        findView();
        event();
    }

    private void event() {
        btnLogin.setOnClickListener(view -> login());
        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtUsernameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cbAdmin.setOnClickListener(view -> {
            if(cbAdmin.isChecked()){
                cbAdmin.setError(null);
            }
        });
    }
    private void findView() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);
        cbAdmin = findViewById(R.id.cbAdmin);
        txtUsernameLayout = findViewById(R.id.txtUsernameLayout);
        txtPasswordLayout = findViewById(R.id.txtPassLayout);
    }
    private void login() {

        if(validateUsername() && validatePassword() && validateCheckbox()){
            String username = txtUsername.getText().toString();
            String password = txtPass.getText().toString();

            Login login = new Login(username, password);

            ApiService.apiService.login(login).enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    Member member = response.body();
                    if(member.getUid() != null){
                        updateUI();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Không thể đăng nhập! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void updateUI() {
        Intent intent = new Intent(LoginMemberActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish();
    }

    //     Validate

    private boolean validateUsername(){
        if(txtUsername.getText().toString().equals("")){
            txtUsernameLayout.setError(getString(R.string.description_username_err));
            return false;
        }
        return true;
    }
    private boolean validatePassword(){
        if(txtPass.getText().toString().equals("")){
            txtPasswordLayout.setError(getString(R.string.description_password_err));
            return false;
        }
        return true;
    }
    private boolean validateCheckbox(){
        if (!cbAdmin.isChecked()){
            cbAdmin.setError("Bạn không phải là nhân viên");
            return false;

        }
        return true;
    }
}