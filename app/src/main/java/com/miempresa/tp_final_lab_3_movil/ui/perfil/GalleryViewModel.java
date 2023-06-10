package com.miempresa.tp_final_lab_3_movil.ui.perfil;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.request.Api;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietario;

    private ApiClient ac;
    private Api.EndPointInmobiliaria EPI;
    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Propietario> getPropietario(){;
        if(propietario==null){
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public void traerProp() {
        ac= ApiClient.getApi();

        propietario.setValue(ac.obtenerUsuarioActual());
    }

    public String editarTexto(String nombreBoton) {
        if(nombreBoton.equals("Editar")){
            return "Guardar";
        }else{
            return "Editar";
        }
    }

    public void actualizarPerfil(String boton, String id,String nombre, String apellido, String dni, String contrasenia, String email, String telefono /*, String avatarF*/) {
        if (boton.equals("Guardar")) {
            Log.d("prueba boton guardar", "guardar apretado");
            EPI = Api.getEndPointInmobiliaria();
            SharedPreferences sp = getApplication().getApplicationContext().getSharedPreferences("token.xml", 0);
            String token = sp.getString("token", "");
            int idI = Integer.parseInt(id);
           // Long dniI = Long.parseLong(dni);
           // int avatar = Integer.parseInt(avatarF);
            Propietario propietarioB = new Propietario(idI, dni, nombre, apellido, email, contrasenia, telefono , "");
            Call<Propietario> callProp = EPI.actualizar(token, propietarioB);
            callProp.enqueue(new Callback<Propietario>() {

                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    Log.d("prueba response put", "entro");
                    if (response.isSuccessful()) {
                        Log.d("prueba isSuccesfull put", "entro");
                       if (response.body() != null) {
                           Log.d("prueba body put", "entro :" + response.body() );
                           propietario.postValue(response.body());
                           ac.setGlobalUser(response.body());
                       }
                    }
                    else {
                        Log.d("prueba error isSuccesfull", "No entro " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("prueba encolado put", "No encolo" + t.getMessage());

                }
            });


        }
    }
}