package com.miempresa.tp_final_lab_3_movil.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Inmueble>> inmuebles;
    private MutableLiveData<ArrayList<Inmueble>> inmueblesConImagenes;
    private ApiClient ac;

    public SlideshowViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() {
        if (inmuebles == null) {
            inmuebles = new MutableLiveData<>();
        }
        return inmuebles;
    }

    public LiveData<ArrayList<Inmueble>> getInmueblesConImagenes() {
        if (inmueblesConImagenes == null) {
            inmueblesConImagenes = new MutableLiveData<>();
        }
        return inmueblesConImagenes;
    }

    public void consultarInmuebles() {
        ac = ApiClient.getApi();
        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<List<Inmueble>> call = EPI.recuperarInmuebles(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Inmueble> inmuebles = (ArrayList<Inmueble>) response.body();
                    ac.obtenerImagenesInmuebles(inmuebles, getApplication().getApplicationContext(), inmueblesConImagenes);
                } else {
                    Log.d("prueba obtenerInmuebles sin respuesta porque", response.code() + ";" + response.message() + ";" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                Log.d("Lo malo", t.getMessage());
            }
        });
    }


}