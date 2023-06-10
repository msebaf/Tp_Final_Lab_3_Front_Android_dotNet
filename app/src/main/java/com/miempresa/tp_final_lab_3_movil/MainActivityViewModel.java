package com.miempresa.tp_final_lab_3_movil;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.modelo.Usuario;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private ApiClient ac = ApiClient.getApi();
    private Context context;

    private MutableLiveData <Propietario> usuarioActual = new MutableLiveData<>();

    private MutableLiveData<Boolean> keyObtenida = new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public LiveData<Propietario> getUsuarioActual() {
        if(usuarioActual==null){
            usuarioActual=new MutableLiveData<>();
        }
        return usuarioActual;
    }




    public LiveData<Boolean> getKeyObtenida() {
        if(keyObtenida==null){
            keyObtenida=new MutableLiveData<>();
        }
        return keyObtenida;
    }
    public void getToken(String email, String password) {


        Usuario usuario = new Usuario(email, password);
        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        Call<String> call = EPI.login(usuario);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("prueba llamdo token", "ok"); //de aca no pasa
                if (response.isSuccessful()) {
                    Log.d("prueba 2 respuesta token", "response succesful"); //de aca no pasa
                    if(response.body()!=null){
                        Log.d("pruba 3 respuasta token body", response.body());
                        // context.startActivity(new android.content.Intent(context, MainActivity.class));
                        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "Bearer " + response.body());

                        editor.commit();
                        keyObtenida.postValue(true);


                    }

                }else{

                    Log.d("sin respuesta porque", response.code() + ";" + response.message()+ ";" + response.headers());
                    Toast.makeText(context, "Credenciales incorrectas, revise su usuario y contraseña", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "ALgo salio mal", Toast.LENGTH_LONG).show();
                Log.d("lo malo", t.getMessage());

            }
        });


    }


    public void login(){
        Log.d("prueba login ", "intentando");
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        Call<Propietario> callProp = EPI.getPropietario(token);
        callProp.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> callProp, Response<Propietario> response) {
                Log.d("prueba login respuesta", "ok");
                if(response.isSuccessful()){
                    Log.d("Prueba login respuesta exitsa", "isSeccesful");
                    if(response.body()!=null){
                        Log.d("Prueba recuperacion de usuario en login", "usuario encontrado");
                        Log.d("Prueba Id", response.body().getId()+"");
                        Log.d("Prueba nombre", response.body().getNombre());
                        Log.d("Prueba apellido", response.body().getApellido());
                        Log.d("Prueba dni", response.body().getDni());
                        Log.d("Prueba email", response.body().getEmail());
                        Log.d("Prueba telefono", response.body().getTelefono());

                        usuarioActual.postValue(response.body()); // esto para cuando quiera settearlo a un mutble el postvalue es como el setvalue pero asincrono
                        ac.setGlobalUser(response.body());
                    }
                }else{
                    Log.d("Prueba recuperacion usario fallida porque...", response.code() + ";" + response.message()+ ";" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("no entrando porque...", t.getMessage()+";"+t.getCause());
                Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show();

            }
        });
    }



}
