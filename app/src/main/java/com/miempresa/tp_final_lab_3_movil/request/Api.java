package com.miempresa.tp_final_lab_3_movil.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miempresa.tp_final_lab_3_movil.modelo.Contrato;
import com.miempresa.tp_final_lab_3_movil.modelo.Inmueble;
import com.miempresa.tp_final_lab_3_movil.modelo.Pago;
import com.miempresa.tp_final_lab_3_movil.modelo.Propietario;
import com.miempresa.tp_final_lab_3_movil.modelo.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class Api {


    public static final String PATH = "http://192.168.0.7:5000/";
    //public static final String PATH = "http://192.168.1.135:5000/";

    private static EndPointInmobiliaria endPointInmobiliaria;
    public static EndPointInmobiliaria getEndPointInmobiliaria() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endPointInmobiliaria = retrofit.create(EndPointInmobiliaria.class);
        return endPointInmobiliaria;
    }



    public interface EndPointInmobiliaria{

        @POST("Propietario/login")
        Call<String> login(@Body Usuario user);

        @GET("Propietario/")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT ("Propietario/actualizar")
        Call<Propietario> actualizar(@Header("Authorization") String token,  @Body Propietario propietario);

        @GET("Propietario/fotoPerfil/{nombreImagen}")
        Call<ResponseBody> recuperarImagen(@Header("Authorization") String token, @Path("nombreImagen") String nombreImagen);

        @GET("Inmueble")
        Call<List<Inmueble>> recuperarInmuebles(@Header("Authorization") String token);

        @GET("Inmueble/fotoInmueble/{nombreImagen}")
        Call<ResponseBody> recuperarImagenInmueble(@Header("Authorization") String token, @Path("nombreImagen") String nombreImagen);

        @PUT ("Inmueble/CambiarEstado/{id}")
        Call<Inmueble> CambiarEstado(@Header("Authorization") String token,  @Path("id") int id);


        @GET("Contrato/vigentes")
        Call<List<Contrato>> getVigentes(@Header("Authorization") String token);

        @GET("Pago/{Id}")
        Call<List<Pago>> getPagos(@Header("Authorization") String token, @Path("Id") int ContratoId);


    }









}
