package com.miempresa.tp_final_lab_3_movil.ui.inmuebles;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeyalleInmuebleViewModel extends AndroidViewModel{
    private ApiClient ac;

    private Api.EndPointInmobiliaria EPI;
    private MutableLiveData<ArrayList<String>> estadoInm;

    private MutableLiveData<Boolean> estado ;
    public DeyalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<ArrayList<String>> getEstadoInm() {
        if (estadoInm == null) {
            this.estadoInm = new MutableLiveData<>();
        }
        return estadoInm;
    }

    public LiveData<Boolean> getEstado() {
        if (estado == null) {
            this.estado = new MutableLiveData<>();
        }
        return estado;
    }
    public void esatdoInmueble(Boolean estado) {
        ArrayList<String> bt = new ArrayList<>();
        if(estado){
            bt.add("Disponible");
            bt.add("#00FF00");
        }else{
            bt.add("No Disponible");
            bt.add("#FF0000");
        }
       estadoInm.setValue(bt);
    }

    public void cambiarEstado(Inmueble inmueble) {
        ac = ApiClient.getApi();
        inmueble.setEstado(!inmueble.isEstado());
        ac.actualizarInmueble(inmueble);

    }
    public void cambiarEstado(int id) {

            Log.d("prueba boton guardar", "guardar apretado");
            EPI = Api.getEndPointInmobiliaria();
            SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
            String token = sp.getString("token", "");

            Call<Inmueble> callProp = EPI.CambiarEstado(token, id);
            callProp.enqueue(new Callback<Inmueble>() {

                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    Log.d("prueba response put", "ok");
                    if (response.isSuccessful()) {
                        Log.d("prueba isSuccesfull put", "ok");
                        if (response.body() != null) {
                            Log.d("prueba body put", "entro :" + response.body().isEstado() );
                            estado.postValue(response.body().isEstado());

                        }
                    }
                    else {
                        Log.d("prueba error isSuccesfull", "No ok " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Log.d("prueba encolado put", "No encolo" + t.getMessage());

                }
            });



    }
}