package com.miempresa.tp_final_lab_3_movil.ui.mapa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private static final LatLng SANLUIS = new LatLng(-33.280576, -66.332482);
    private static final LatLng ULP = new LatLng(-33.150720, -66.306864);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("San Luis"));
        googleMap.addMarker(new MarkerOptions().position(ULP).title("ULP"));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(ULP)
                .zoom(19)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(camPos);
        googleMap.animateCamera(update);
    }
}