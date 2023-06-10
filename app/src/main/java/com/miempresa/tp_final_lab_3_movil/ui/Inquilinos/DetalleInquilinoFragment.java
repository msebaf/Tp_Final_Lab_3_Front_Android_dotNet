package com.miempresa.tp_final_lab_3_movil.ui.Inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miempresa.tp_final_lab_3_movil.R;

import com.miempresa.tp_final_lab_3_movil.databinding.FragmentDetalleInquilinoBinding;
import com.miempresa.tp_final_lab_3_movil.databinding.FragmentPropiedadesAlquiladasBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Inquilino;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);


        Bundle bundle = getArguments();
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");



      //  vm.getInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
         //   @Override
          //  public void onChanged(Inquilino inquilino) {

                binding.etCodigo.setText(String.valueOf(contrato.getInquilino().getIdInquilino()));
                binding.etApellido.setText(String.valueOf(contrato.getInquilino().getApellido()));
                binding.etDni.setText(String.valueOf(contrato.getInquilino().getDNI()));
                binding.etNombre.setText(String.valueOf(contrato.getInquilino().getNombre()));
                binding.etEmail.setText(String.valueOf(contrato.getInquilino().getEmail()));
                binding.etTelefono.setText(String.valueOf(contrato.getInquilino().getTelefono()));
                binding.etGarante.setText(String.valueOf(contrato.getInquilino().getNombreGarante()));
                binding.etTelefonoGarante.setText(String.valueOf(contrato.getInquilino().getTelefonoGarante()));

           // }
       // });
      //  vm.consultarInquilino(inmueble);
       // Propietario prop= vm.recuperarPropietario();
       // inmueble.setPropietario(prop);



        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}