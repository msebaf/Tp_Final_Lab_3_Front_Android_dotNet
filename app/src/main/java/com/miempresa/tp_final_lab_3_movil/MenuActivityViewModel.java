package com.miempresa.tp_final_lab_3_movil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivityViewModel extends AndroidViewModel {
    private ApiClient ac = ApiClient.getApi();
    private MutableLiveData<Propietario> propietarioLive;

    private MutableLiveData<String> avatarUrl;

    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getUsuarioActual() {
        if(propietarioLive==null){
            propietarioLive=new MutableLiveData<>();
        }
        return propietarioLive;
    }

    public void buscarPropietario(){
        propietarioLive.setValue( ac.obtenerUsuarioActual());
    }

    public String seleccionarTitulo(int id) {
        String title;
        switch (id) {
            case R.id.nav_home:
                title = "Mapa de la inmobiliaria";
                break;
            case R.id.nav_gallery:
                title = "Perfil";
                break;
            case R.id.nav_slideshow:
                title = "Tus Inmuebles";
                break;
            case R.id.nav_deyalleInmuebleFragment:
                title = "Datos del inmueble";
                break;
            case R.id.nav_propiedadesAlquiladas:
                title = "Tus alquileres";
                break;
            case R.id.nav_detalle_inqui:
                title = "Inquilino";
                break;
            case R.id.nav_contratos:
                title = "Contratos";
                break;
            case R.id.nav_detalleContratoFragment:
                title = "Detalle del contrato";
                break;
            case R.id.nav_pagosFragment:
                title = "Pagos Recibidos";
                break;
            default:
                title = "Salir";
                break;
        }
        return title;
    }


    public LiveData<String> getAvatarUrl() {
        if(avatarUrl==null){
            avatarUrl=new MutableLiveData<>();
        }
        return avatarUrl;
    }

    public void recuperarImagen(String nombreImagen) {
        SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        Call<ResponseBody> callImagen = EPI.recuperarImagen(token, nombreImagen);

        callImagen.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("prueba recuperacion imagen", "ok");
                    if (response.body() != null) {
                        Log.d("prueba recuperacion imagen body", "ok");
                        Log.d("prueba recuperacion imagen body2", response.body().toString());

                        String nombreAvatar = "avatar_" + ac.obtenerUsuarioActual().getId()+".jpg";
                        guardarImagen(getApplication(), nombreAvatar, response.body());

                    }
                } else {
                    Log.d("pruebar recuperar imagen", "no ok" + response.code() + ";" + response.message() + ";" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error en llamada", t.getMessage() + ";" + t.getCause());
                Toast.makeText(getApplication(), "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarImagen(Context context, String imageName, ResponseBody body) {
        Log.d("Prueba entra a guardar imagen", "ok");
        try {
            // Convierte el cuerpo de la respuesta en un arreglo de bytes
            byte[] imageBytes = body.bytes();

            // Obtiene el directorio de almacenamiento interno de la aplicación
            File directory = context.getFilesDir();

            // Crea el archivo de imagen en el directorio de almacenamiento interno
            File file = new File(directory, imageName);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(imageBytes);
            outputStream.close();

            if (file.exists()) {
                Log.d(" prueba ImageUtils", "Imagen guardada en: " + file.getAbsolutePath());
                avatarUrl.postValue(file.getAbsolutePath());
            } else {
                Log.d("prueba ImageUtils", "No se pudo guardar la imagen");
            }
        } catch (IOException e) {
            Log.e("prueba ImageUtils", "Error al guardar la imagen: " + e.getMessage());
        }


    }
}
