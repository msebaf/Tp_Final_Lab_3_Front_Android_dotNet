package com.miempresa.tp_final_lab_3_movil.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentDetalleContratoBinding;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentDeyalleInmuebleBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.ui.inmuebles.DeyalleInmuebleFragment;
import com.miempresa.tp_final_lab_3_movil.ui.inmuebles.SlideshowViewModel;

import java.time.format.DateTimeFormatter;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;


    private FragmentDetalleContratoBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        Bundle bundle = getArguments();
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");



                binding.etCodigo.setText(String.valueOf(contrato.getIdContrato()));
                binding.etFechaFinalizacion.setText(String.valueOf(contrato.getFechaFin()));
                binding.etFechaInicio.setText(String.valueOf(contrato.getFechaInicio()));
                binding.etInmueble.setText(String.valueOf(contrato.getInmueble().getDireccion()));
                binding.etMonto.setText(String.valueOf(contrato.getMontoAlquiler()));
                binding.etInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());

                binding.btPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato", contrato);

                        NavOptions op= new NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(R.id.nav_detalleContratoFragment,true)
                                .build();

                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.nav_pagosFragment, bundle);



                    }
                });




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}