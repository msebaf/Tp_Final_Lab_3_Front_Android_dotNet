package com.miempresa.tp_final_lab_3_movil.ui.contratos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.request.ApiClient;

import java.util.ArrayList;

public class PagosActivityViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Pago>> pagos;

    private ApiClient ac;

    public PagosActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Pago>>getPagos() {
        if (pagos == null) {
            pagos = new MutableLiveData<>();
        }
        return pagos;
    }

    public void obtenerPagos(Contrato contrato){
        ac = ApiClient.getApi();
        pagos.setValue(ac.obtenerPagos(contrato));
    }
}
