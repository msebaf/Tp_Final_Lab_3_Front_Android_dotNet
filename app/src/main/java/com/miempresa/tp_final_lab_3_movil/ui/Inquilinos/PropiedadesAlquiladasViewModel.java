package com.miempresa.tp_final_lab_3_movil.ui.Inquilinos;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropiedadesAlquiladasViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Inmueble>> inmueblesConImagenes;
    private ApiClient ac;

    private MutableLiveData<ArrayList<Contrato>> contratosConImagenes;

    private ArrayList<Contrato> contratos;


    public PropiedadesAlquiladasViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() {
        if (inmueblesConImagenes == null) {
            this.inmueblesConImagenes = new MutableLiveData<>();
        }
        return inmueblesConImagenes;
    }
    public LiveData<ArrayList<Contrato>> getContratos() {
        if (contratosConImagenes == null) {
            this.contratosConImagenes = new MutableLiveData<>();
        }
        return contratosConImagenes;
    }



    public void consultarContratosVigentes() {
        ac = ApiClient.getApi();
        //inmuebles.setValue(ac.obtenerPropiedadesAlquiladas());
        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<List<Contrato>> call = EPI.getVigentes(token);

        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()) {
                    contratos = (ArrayList<Contrato>) response.body();
                    Log.d("prueba contrato", contratos.get(0).getInmueble().getDireccion());
                    ArrayList<Inmueble> inmuebles = new ArrayList<>();
                    for (Contrato c : contratos) {

                        inmuebles.add(c.getInmueble());
                    }
                    ac.obtenerImagenesInmuebles(inmuebles, getApplication().getApplicationContext(), inmueblesConImagenes);
                } else {
                    Log.d("prueba obtenerInmuebles sin respuesta porque", response.code() + ";" + response.message() + ";" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                Log.d("Lo malo", t.getMessage());
            }
        });
    }

    public void actualizarContrato(ArrayList<Inmueble> inmuebles){
        for (Inmueble inmueble : inmuebles) {
            for (Contrato contrato : contratos) {
                if (contrato.getInmueble().getIdInmueble() == inmueble.getIdInmueble()) {
                    contrato.setInmueble(inmueble);
                }
            }
        }
        contratosConImagenes.postValue(contratos);

    }


}