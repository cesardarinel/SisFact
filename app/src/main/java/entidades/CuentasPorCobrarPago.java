package entidades;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Date;

@Table(name = "cuentas_por_cobrar_pago")
public class CuentasPorCobrarPago {
    @Column(name = "fecha_pago")
    Date fechaPago;

    @Column(name="fecha_pagada")
    protected Date fechaCreada;


}
