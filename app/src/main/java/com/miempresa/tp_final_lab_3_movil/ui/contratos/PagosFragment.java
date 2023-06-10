package com.miempresa.tp_final_lab_3_movil.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentPagosBinding;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentSlideshowBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.ui.inmuebles.SlideshowViewModel;

import java.util.ArrayList;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding binding;

    private PagosViewModel vm;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        Bundle bundle = getArguments();
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");

        RecyclerView rv = binding.rvPagos;
        vm.getPagos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pago>>() {
            @Override
            public void onChanged(ArrayList<Pago> pagos) {
                PagsActivityAdapter adapter = new PagsActivityAdapter(requireContext(), pagos, getLayoutInflater());
                rv.setAdapter(adapter);
            }
        });

        vm.obtenerPagos(contrato);

        GridLayoutManager grilla = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(grilla);




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}