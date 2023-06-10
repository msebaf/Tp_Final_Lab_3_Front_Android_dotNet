package com.miempresa.tp_final_lab_3_movil.ui.contratos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.util.ArrayList;

public class PagsActivityAdapter extends RecyclerView.Adapter<PagsActivityAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pago> pagos;
    private LayoutInflater inflater;
    private ApiClient apiClient;

    public PagsActivityAdapter(Context context, ArrayList<Pago> pagos, LayoutInflater layoutInflater) {
        this.context = context;
        this.pagos = pagos;
        this.inflater = layoutInflater;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pago_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = pagos.get(position);

       holder.codigoPago.setText("Codigo de pago: "+pago.getIdPago()+"");
       holder.numeroPago.setText("Numero de pago: "+pago.getNumero()+"");
       holder.codigoContarto.setText("Codigo de contrato: "+pago.getContrato().getIdContrato()+"");
       holder.importe.setText("Importe: "+pago.getImporte()+"");
       holder.fechaPAgo.setText("Fecha de pago: "+pago.getFechaDePago()+"");





    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView codigoPago, numeroPago, codigoContarto, importe, fechaPAgo;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            codigoPago = itemView.findViewById(R.id.tvCodigoPago);
            numeroPago = itemView.findViewById(R.id.tvNumeroPago);
            codigoContarto = itemView.findViewById(R.id.tvCodgioContrato);
            importe = itemView.findViewById(R.id.tvImporte);
            fechaPAgo = itemView.findViewById(R.id.tvFechaPago);

        }
    }
}

