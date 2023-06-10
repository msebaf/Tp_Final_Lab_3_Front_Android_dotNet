package com.miempresa.tp_final_lab_3_movil.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentDetalleInquilinoBinding;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentDeyalleInmuebleBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Inquilino;

import java.util.ArrayList;

public class DeyalleInmuebleFragment extends Fragment {

    private DeyalleInmuebleViewModel vm;
    private FragmentDeyalleInmuebleBinding binding;

    public static DeyalleInmuebleFragment newInstance() {
        return new DeyalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeyalleInmuebleBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

    vm = new ViewModelProvider(this).get(DeyalleInmuebleViewModel.class);
        Bundle bundle = getArguments();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");

        binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
        binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
        binding.etPrecio.setText(String.valueOf("$"+inmueble.getPrecio()));
        binding.etDireccion.setText(String.valueOf(inmueble.getDireccion()));
        binding.etTipo.setText(String.valueOf(inmueble.getTipo()));
        binding.etUso.setText(String.valueOf(inmueble.getUso()));
        Glide.with(DeyalleInmuebleFragment.this)
                .load(inmueble.getImagen())
                .into(binding.ivFoto);
        vm.getEstadoInm().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                binding.btDisp.setText(strings.get(0));
                binding.btDisp.setBackgroundColor(Color.parseColor(strings.get(1)));
            }
        });



        vm.esatdoInmueble(inmueble.isEstado());

        vm.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                vm.esatdoInmueble(aBoolean);
            }
        });
        binding.btDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cambiarEstado(inmueble.getIdInmueble());

            }
        });





        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DeyalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}