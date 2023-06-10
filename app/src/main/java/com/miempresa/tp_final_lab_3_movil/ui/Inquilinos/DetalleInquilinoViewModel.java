package com.miempresa.tp_final_lab_3_movil.ui.Inquilinos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Inquilino;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilinoLive;
    private ApiClient ac;
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilino() {
        if (inquilinoLive == null) {
            this.inquilinoLive = new MutableLiveData<>();
        }
        return inquilinoLive;
    }

    public void consultarInquilino(Inmueble inmueble) {

        ac = ApiClient.getApi();
        inquilinoLive.setValue(ac.obtenerInquilino(inmueble));
    }

    public Propietario recuperarPropietario() {
        return ac.obtenerUsuarioActual();
    }
}