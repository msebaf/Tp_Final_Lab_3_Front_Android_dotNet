package com.miempresa.tp_final_lab_3_movil.ui.contratos;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Pago>> pagosLive;

    private ApiClient ac;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Pago>>getPagos() {
        if (pagosLive == null) {
            pagosLive = new MutableLiveData<>();
        }
        return pagosLive;
    }

    public void obtenerPagos(Contrato contrato){
        ac = ApiClient.getApi();
        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<List<Pago>> call = EPI.getPagos(token, contrato.getIdContrato());

        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Pago> pagos = (ArrayList<Pago>) response.body();
                    for (Pago p : pagos) {
                        p.setContrato(contrato);
                    }
                    pagosLive.postValue(pagos);
                } else {
                    Log.d("prueba obtenerInmuebles sin respuesta porque", response.code() + ";" + response.message() + ";" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                Log.d("Lo malo", t.getMessage());
            }
        });
    }

}
