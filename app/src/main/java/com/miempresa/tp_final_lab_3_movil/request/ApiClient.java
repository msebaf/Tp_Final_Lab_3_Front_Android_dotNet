package com.miempresa.tp_final_lab_3_movil.request;





import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miempresa.tp_final_lab_3_movil.R;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Inquilino;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.modelo.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public class ApiClient {






    //---------------------VIEJA API--------------------
    private final ArrayList<Propietario> propietarios=new ArrayList<>();
    private final ArrayList<Inquilino> inquilinos=new ArrayList<>();
    private final ArrayList<Inmueble> inmuebles=new ArrayList<>();
    private final ArrayList<Contrato> contratos=new ArrayList<>();
    private final ArrayList<Pago> pagos=new ArrayList<>();
    private static Propietario usuarioActual=null;
    private static ApiClient api=null;

    private ApiClient(){
        //Nos conectamos a nuestra "Base de Datos"
        //cargaDatos();
    }
    //Método para crear una instancia de ApiClient
    public static ApiClient getApi(){
        if (api==null){
            api=new ApiClient();
        }
        return api;

        }




//Servicios
    //Para que pueda iniciar sesion
public void setGlobalUser(Propietario propietario){

        this.usuarioActual=propietario;

}


//Retorna el usuario que inició Sesión
    public Propietario obtenerUsuarioActual(){
        return usuarioActual;
    }

    //Retorna todas las propiedades del usuario propietario logueado
    public ArrayList<Inmueble> obtnerPropiedades(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Inmueble inmueble:inmuebles){
            if(inmueble.getPropietario().equals(usuarioActual)){
                temp.add(inmueble);
            }
        }
        return temp;
    }

//Lista de inmuebles alquilados actualmente del propietario logueado.
    public ArrayList<Inmueble> obtenerPropiedadesAlquiladas(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().getPropietario().equals(usuarioActual)){
                temp.add(contrato.getInmueble());
            }
        }
        return temp;
    }


//Dado un inmueble retorna el contrato activo de dicho inmueble

    public Contrato obtenerContratoVigente(Inmueble inmueble){

        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato;
            }
        }
        return null;
    }

    //Dado un inmueble, retorna el inquilino del ultimo contrato activo de ese inmueble.
    public Inquilino obtenerInquilino(Inmueble inmueble){
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato.getInquilino();
            }
        }
        return null;
    }
//Dado un Contrato, retorna los pagos de dicho contrato
    public ArrayList<Pago> obtenerPagos(Contrato contratoVer){
        ArrayList<Pago> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.equals(contratoVer)){
                for(Pago pago:pagos){
                    if(pago.getContrato().equals(contrato)){
                        temp.add(pago);
                    }
                }
            }
            break;
        }
        return temp;
    }
//Actualizar Perfil
    public void actualizarPerfil(Propietario propietario){
        int posición=propietarios.indexOf(propietario);
        if(posición!=-1){
            propietarios.set(posición,propietario);
        }
    }

    //ActualizarInmueble
    public void actualizarInmueble(Inmueble inmueble){
        int posicion=inmuebles.indexOf(inmueble);
        if(posicion!=-1){
            inmuebles.set(posicion,inmueble);
        }
    }



    public void obtenerImagenesInmuebles(ArrayList<Inmueble> inmuebles, Context context, MutableLiveData<ArrayList<Inmueble>> liveData){ {
        List<Call<ResponseBody>> calls = new ArrayList<>();
        List<Inmueble> inmueblesConImagenesLocal = new ArrayList<>();

        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");;

        Api.EndPointInmobiliaria EPI = Api.getEndPointInmobiliaria();
        for (Inmueble inmueble : inmuebles) {
            Call<ResponseBody> call = EPI.recuperarImagenInmueble(token, "inm_" + inmueble.getIdInmueble() + ".jpg");
            calls.add(call);
        }

        for (int i = 0; i < calls.size(); i++) {
            Call<ResponseBody> call = calls.get(i);
            Inmueble inmueble = inmuebles.get(i);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String imageName = "inm_" + inmueble.getIdInmueble() + ".jpg";
                        guardarImagen(context, imageName, response.body(), inmueble);
                        inmueblesConImagenesLocal.add(inmueble);

                        // Verifica si ya se obtuvieron todas las imágenes
                        if (inmueblesConImagenesLocal.size() == inmuebles.size()) {
                            liveData.postValue((ArrayList<Inmueble>) inmueblesConImagenesLocal);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Maneja el fallo de la solicitud
                }
            });
        }
    }

}

    private void guardarImagen(Context context, String imageName, ResponseBody body, Inmueble inmueble) {
        try {
            byte[] imageBytes = body.bytes();
            File file = new File(context.getCacheDir(), imageName); // Guardar en la memoria caché de la aplicación
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(imageBytes);
            outputStream.close();

            if (file.exists()) {
                inmueble.setImagen(file.getAbsolutePath());
            } else {
                Log.d("prueba ImageUtils", "No se pudo guardar la imagen");
            }
        } catch (IOException e) {
            Log.e("prueba ImageUtils", "Error al guardar la imagen: " + e.getMessage());
        }
    }



  /*  private void cargaDatos(){

        //Propietarios
        Propietario juan=new Propietario(1,23492012L,"Juan","Perez","juan@mail.com","123","2664553447", R.drawable.juan);
        Propietario sonia=new Propietario(2,17495869L,"Sonia","Lucero","sonia@mail.com","123","266485417", R.drawable.sonia);
        propietarios.add(juan);
        propietarios.add(sonia);

        //Inquilinos
        Inquilino mario=new Inquilino(100,25340691L,"Mario","Luna","Aiello sup.","luna@mail.com","2664253411","Lucero Roberto","2664851422");
        inquilinos.add(mario);

        //Inmuebles
        Inmueble salon=new Inmueble(501,"Colon 340","comercial","salon",2,20000,juan,true,"http://www.secsanluis.com.ar/servicios/salon1.jpg");
        Inmueble casa=new Inmueble(502,"Mitre 800","particular","casa",2,15000,juan,true,"http://www.secsanluis.com.ar/servicios/casa1.jpg");
        Inmueble otraCasa=new Inmueble(503,"Salta 325","particular","casa",3,17000,sonia,true,"http://www.secsanluis.com.ar/servicios/casa2.jpg");
        Inmueble dpto=new Inmueble(504,"Lavalle 450","particular","dpto",2,25000,sonia,true,"http://www.secsanluis.com.ar/servicios/departamento1.jpg");
        Inmueble casita=new Inmueble(505,"Belgrano 218","particular","casa",5,90000,sonia,true,"http://www.secsanluis.com.ar/servicios/casa3.jpg");

        inmuebles.add(salon);
        inmuebles.add(casa);
        inmuebles.add(otraCasa);
        inmuebles.add(dpto);
        inmuebles.add(casita);

        //Contratos
        Contrato uno=new Contrato(701, "05/08/2020","05/08/2023",17000,mario,otraCasa);
        contratos.add(uno);
        //Pagos
        pagos.add(new Pago(900,1,uno,17000,"10/08/2020"));
        pagos.add(new Pago(901,2,uno,17000,"10/09/2020"));
        pagos.add(new Pago(902,3,uno,17000,"10/10/2020"));




    }*/
}
