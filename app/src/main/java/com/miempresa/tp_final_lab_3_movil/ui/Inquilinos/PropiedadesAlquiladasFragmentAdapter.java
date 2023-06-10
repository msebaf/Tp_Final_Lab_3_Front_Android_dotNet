package com.miempresa.tp_final_lab_3_movil.ui.Inquilinos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;

import com.miempresa.tp_final_lab_3_movil.modelo.Inquilino;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;
import com.miempresa.tp_final_lab_3_movil.ui.Inquilinos.PropiedadesAlquiladasFragmentAdapter;

import java.util.List;

public class PropiedadesAlquiladasFragmentAdapter extends RecyclerView.Adapter<PropiedadesAlquiladasFragmentAdapter.ViewHolder> {

    private Context context;
    private List<Contrato> contratos;
    private LayoutInflater inf;
    private ApiClient ac;

    public PropiedadesAlquiladasFragmentAdapter(Context context, List<Contrato> contratos, LayoutInflater inf) {
        this.context = context;
        this.contratos = contratos;
        this.inf = inf;
        ac = ApiClient.getApi();
    }

    @NonNull
    @Override
    public PropiedadesAlquiladasFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = inf.inflate(R.layout.inmueble_card ,parent,false);
        return new PropiedadesAlquiladasFragmentAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PropiedadesAlquiladasFragmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //funcionaba igual sin el @SupressLint pero int position estaba e rojo...me sugirio ponerlo...ni idea...


        holder.direccion.setText(contratos.get(position).getInmueble().getDireccion()+"");
        holder.precio.setText( "Ver inquilino");
         Glide.with(context).
         load(contratos.get(position).getInmueble().getImagen()).
         diskCacheStrategy(DiskCacheStrategy.ALL).
          into(holder.foto);
        CardView cv = holder.itemView.findViewById(R.id.cvInm);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               Contrato contrato = contratos.get(position);


                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contrato);


                NavOptions op= new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(R.id.nav_propiedadesAlquiladas,true)
                        .build();

                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_menu).navigate(R.id.nav_detalle_inqui, bundle);


            }
        });

    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         ImageView foto;
        TextView precio, direccion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              foto = itemView.findViewById(R.id.ivFoto);
            precio = itemView.findViewById(R.id.tvPrecio);
            direccion = itemView.findViewById(R.id.tvDireccion);
        }
    }
}
//descomentar cuando tenga fotos