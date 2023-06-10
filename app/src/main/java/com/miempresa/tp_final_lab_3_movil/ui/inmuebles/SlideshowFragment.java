package com.miempresa.tp_final_lab_3_movil.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miempresa.tp_final_lab_3_movil.databinding.FragmentSlideshowBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private SlideshowViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(SlideshowViewModel.class);

        RecyclerView rv = binding.rvInmuebles;


        vm.getInmueblesConImagenes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                SlideshowFragmentAdapter adapter = new SlideshowFragmentAdapter(requireContext(), inmuebles, getLayoutInflater());
                rv.setAdapter(adapter);
            }
        });

        vm.consultarInmuebles();

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