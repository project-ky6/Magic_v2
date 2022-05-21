package com.example.magiccoffee_v2.GUI;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.magiccoffee_v2.API.ApiService;
import com.example.magiccoffee_v2.DTO.Branch;
import com.example.magiccoffee_v2.R;
import com.example.magiccoffee_v2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private List<Branch> branches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = ActivityMapsBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.example.magiccoffee_v2.R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ApiService.apiService.getListBranch().enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                branches = response.body();
                for (Branch branch : branches) {
                    LatLng sydney = new LatLng(Double.parseDouble(branch.getX()), Double.parseDouble(branch.getY()));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(branch.getAddress()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }
            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Không thể tải map", Toast.LENGTH_SHORT).show();
            }
        });
    }
}