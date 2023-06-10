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
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentContratoBinding;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentPropiedadesAlquiladasBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.ui.Inquilinos.PropiedadesAlquiladasFragmentAdapter;
import com.miempresa.tp_final_lab_3_movil.ui.Inquilinos.PropiedadesAlquiladasViewModel;

import java.util.ArrayList;

public class ContratoFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;
   // private ArrayList<Inmueble> inmuebles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PropiedadesAlquiladasViewModel propiedadesAlquiladasViewModel =
                new ViewModelProvider(this).get(PropiedadesAlquiladasViewModel.class);

        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(ContratoViewModel.class);

        RecyclerView rv = binding.rvInmuebles;


        vm.getContratos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Contrato>>() {
            @Override
            public void onChanged(ArrayList<Contrato> contratoes) {
                ContratosFragmentAdapter adapter = new ContratosFragmentAdapter(requireContext(), contratoes, getLayoutInflater());
                rv.setAdapter(adapter);
            }
        });

        vm.getInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {

                vm.actualizarContrato(inmuebles);
                //ContratosFragmentAdapter adapter = new ContratosFragmentAdapter(requireContext(), inmuebles, getLayoutInflater());
               // rv.setAdapter(adapter);
            }
        });
        vm.consultarContratosVigentes();

        GridLayoutManager grilla = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(grilla);





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}