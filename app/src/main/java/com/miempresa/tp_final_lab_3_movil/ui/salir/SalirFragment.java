package com.miempresa.tp_final_lab_3_movil.ui.salir;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miempresa.tp_final_lab_3_movil.databinding.FragmentSalirBinding;

public class SalirFragment extends Fragment {

    private FragmentSalirBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SalirViewModel svm =
                new ViewModelProvider(this).get(SalirViewModel.class);

        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        Alert.mostrarDialogoSalida(getContext());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}