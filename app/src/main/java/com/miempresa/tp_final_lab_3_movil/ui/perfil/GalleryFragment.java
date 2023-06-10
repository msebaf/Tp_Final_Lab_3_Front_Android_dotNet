package com.miempresa.tp_final_lab_3_movil.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.miempresa.tp_final_lab_3_movil.databinding.FragmentGalleryBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;

public class GalleryFragment extends Fragment {


    private FragmentGalleryBinding binding;
    private GalleryViewModel vm;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(GalleryViewModel.class);
        vm.getPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etCodigo.setText(propietario.getId()+"");
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etContrasenia.setText(propietario.getContraseña());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
               // binding.etAvatar.setText(propietario.getAvatar()+"");
            }
        });
        vm.traerProp();

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.actualizarPerfil( binding.btEditar.getText().toString()  , binding.etCodigo.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etContrasenia.getText().toString(),
                        binding.etEmail.getText().toString(),
                        binding.etTelefono.getText().toString()
                        /*,binding.etAvatar.getText().toString()*/);
                binding.etNombre.setEnabled(!binding.etNombre.isEnabled());
                binding.etApellido.setEnabled(!binding.etApellido.isEnabled());
                binding.etDni.setEnabled(!binding.etDni.isEnabled());
                binding.etContrasenia.setEnabled(!binding.etContrasenia.isEnabled());
                binding.etEmail.setEnabled(!binding.etEmail.isEnabled());
                binding.etTelefono.setEnabled(!binding.etTelefono.isEnabled());
                binding.btEditar.setText(vm.editarTexto(binding.btEditar.getText().toString()));
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}