package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="cunetas_por_cobrar")
public class CuentasPorCobrar extends Model implements Serializable{

	@Column(name="factura")
	protected Facturas factura;

	protected Long internalId;

	@Column(name = "fecha_Creada")
	protected Date fechaCreada;

	@Column(name = "descripcion")
	protected String descripcion;

	@Column(name = "monto")
	protected BigDecimal monto;

	public Facturas getFactura() {
		return factura;
	}

	public void setFactura(Facturas factura) {
		this.factura = factura;
	}

	public Long getInternalId() {
		if (getId() != null) return getId();
		return internalId;
	}

	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreada() {
		return fechaCreada;
	}

	public void setFechaCreada(Date fechaCreada) {
		this.fechaCreada = fechaCreada;
	}

	public Boolean EstaPagado(){
		List<CuentasPorCobrarPago> cuentasPorCobrarPagos = new Select()
				.from(CuentasPorCobrarPago.class)
				.where("cuentas_por_cobrar = ? ",getInternalId())
				.execute();
		BigDecimal total = BigDecimal.ZERO;
		for (CuentasPorCobrarPago item : cuentasPorCobrarPagos){
			total.add(item.getMonto());
		}
		return (total.compareTo(monto) == 0);
	}
}
