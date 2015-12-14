package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.sql.Date;

@Table(name = "cuentas_por_cobrar_pago")
public class CuentasPorCobrarPago extends Model implements Serializable {

    @Column(name = "cuentas_por_cobrar")
    protected
    CuentasPorCobrar cuentasPorCobrar;

    @Column(name = "fecha_pago")
    protected
    Date fechaPago;

    @Column(name="fecha_pagada")
    protected Date fechaCreada;


    public CuentasPorCobrar getCuentasPorCobrar() {
        return cuentasPorCobrar;
    }

    public void setCuentasPorCobrar(CuentasPorCobrar cuentasPorCobrar) {
        this.cuentasPorCobrar = cuentasPorCobrar;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaCreada() {
        return fechaCreada;
    }

    public void setFechaCreada(Date fechaCreada) {
        this.fechaCreada = fechaCreada;
    }
}
