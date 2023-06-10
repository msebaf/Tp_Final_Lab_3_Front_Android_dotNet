package com.miempresa.tp_final_lab_3_movil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.miempresa.tp_final_lab_3_movil.databinding.ActivityMenuBinding;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    private MenuActivityViewModel mv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMenu.toolbar);
        mv= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MenuActivityViewModel.class);

        mv.getAvatarUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                View navHeader = binding.navView.getHeaderView(0);
                ImageView imageView = navHeader.findViewById(R.id.imageView);

                Glide.with(MenuActivity.this)
                        .load(s)
                        .into(imageView);

            }
        });

        mv.getUsuarioActual().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {


                View navHeader = binding.navView.getHeaderView(0);
                ImageView imageView = navHeader.findViewById(R.id.imageView);
                TextView textViewNombre = navHeader.findViewById(R.id.nombre);
                TextView textViewMail = navHeader.findViewById(R.id.mail);

                // Establecer la imagen del ImageView
                //imageView.setImageResource(propietario.getAvatar());
                mv.recuperarImagen("avatar_"+ propietario.getId()+".jpg");


                if (propietario.getAvatar() != null) {
                    // Carga la imagen en ivFoto utilizando Glide

                } else {
                    // Si la URL de la imagen no está disponible, muestra una imagen predeterminada
                    imageView.setImageResource(R.drawable.avatar);
                }


                // Establecer el texto del TextView
                textViewNombre.setText(propietario.getApellido() + " " + propietario.getNombre());
                textViewMail.setText(propietario.getEmail() );

            }
        });
        mv.buscarPropietario();






        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_propiedadesAlquiladas,R.id.nav_contratos,R.id.nav_salir)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        ActionBar actionBar = getSupportActionBar();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = mv.seleccionarTitulo(destination.getId());

            actionBar.setTitle(title);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}