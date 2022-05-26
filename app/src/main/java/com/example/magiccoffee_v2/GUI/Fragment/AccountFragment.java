package com.example.magiccoffee_v2.GUI.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magiccoffee_v2.DTO.User;
import com.example.magiccoffee_v2.GUI.CartActivity;
import com.example.magiccoffee_v2.GUI.CustomDialogClass;
import com.example.magiccoffee_v2.GUI.Login.InfoActivity;
import com.example.magiccoffee_v2.GUI.Login.LoginActivity;
import com.example.magiccoffee_v2.GUI.MapsActivity;
import com.example.magiccoffee_v2.GUI.Static.RequestCode;
import com.example.magiccoffee_v2.GUI.Utils;
import com.example.magiccoffee_v2.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;
import java.io.IOException;

public class AccountFragment extends Fragment {
    private TextView txtName;
    private Button btnMap, btnCart;
    private User user;
    private Button btnLogout;
    private Button btnUpdateInfo;

    public AccountFragment(User user) {
        this.user = user;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getActivity().getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.primary));
        }
        initEvent();

        if(user.getUid() != null){
            txtName.setText(user.getName().toString());
        }
        return view;
    }
    private void initEvent() {
        btnMap.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MapsActivity.class));
        });
        btnUpdateInfo.setOnClickListener(view ->{

            Bundle bundle = new Bundle();
            bundle.putSerializable("User", user);

            Intent intent = new Intent(getContext(), InfoActivity.class);
            intent.setAction(RequestCode.UPDATE_INFO+"");
            intent.putExtra("Data", bundle);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(view->{
            FirebaseAuth.getInstance().signOut();
            writeData("");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        btnCart.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            startActivity(intent);
        });

    }
    private void initView(@NonNull View view) {
        btnMap = view.findViewById(R.id.btnMap);
        txtName = view.findViewById(R.id.txtName);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        btnCart = view.findViewById(R.id.btnCart);
    }
    private void writeData(String data){
        try
        {
            FileOutputStream fos = getActivity().openFileOutput(Utils.FILE_UID, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}