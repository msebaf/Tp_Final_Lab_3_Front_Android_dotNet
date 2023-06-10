package com.miempresa.tp_final_lab_3_movil.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    private int numero;
    private Contrato contrato;
    private double monto;
    private String fechaPago;

    public Pago() {}

    public Pago(int idPago, int numero, Contrato contrato, double importe, String fechaDePago) {
        this.id = idPago;
        this.numero = numero;
        this.contrato = contrato;
        this.monto = importe;
        this.fechaPago = fechaDePago;
    }

    public int getIdPago() {
        return id;
    }

    public void setIdPago(int idPago) {
        this.id = idPago;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getImporte() {
        return monto;
    }

    public void setImporte(double importe) {
        this.monto = importe;
    }

    public String getFechaDePago() {
        return fechaPago;
    }

    public void setFechaDePago(String fechaDePago) {
        this.fechaPago = fechaDePago;
    }
}
