package com.miempresa.tp_final_lab_3_movil.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private String fechaInicio;
    private String fechaFinal;
    private double montoMensual;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {}
    public Contrato(int idContrato, String fechaInicio, String fechaFin, double montoAlquiler, Inquilino inquilino, Inmueble inmueble) {
        this.id = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFin;
        this.montoMensual = montoAlquiler;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getIdContrato() {
        return id;
    }

    public void setIdContrato(int idContrato) {
        this.id= idContrato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFinal;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFinal = fechaFin;
    }

    public double getMontoAlquiler() {
        return montoMensual;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoMensual = montoAlquiler;
    }


    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
