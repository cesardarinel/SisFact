package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Date;

@Table(name="cuentas_por_cobrar")
public class CuentasPorCobrar extends Model{

	@Column(name="factura")
	protected Facturas factura;

	@Column(name="pago")
	protected Float pago;

	@Column(name="fecha_pagada")
	protected Date fechaPagada;

	@Column(name="es_pagado")
	protected boolean esPagado;

	public Facturas getFactura() {
		return factura;
	}

	public void setFactura(Facturas factura) {
		this.factura = factura;
	}

	public Float getPago() {
		return pago;
	}

	public void setPago(Float pago) {
		this.pago = pago;
	}

	public Date getFechaPagada() {
		return fechaPagada;
	}

	public void setFechaPagada(Date fechaPagada) {
		this.fechaPagada = fechaPagada;
	}

	public boolean isEsPagado() {
		return esPagado;
	}

	public void setEsPagado(boolean esPagado) {
		this.esPagado = esPagado;
	}
}
