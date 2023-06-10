package com.miempresa.tp_final_lab_3_movil.modelo;

public class Usuario {

    private String email;
    private String clave;

    public Usuario(String usuario, String clave) {
        this.email = usuario;
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
