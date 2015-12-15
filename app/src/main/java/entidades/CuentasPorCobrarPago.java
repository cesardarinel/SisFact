package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "cuentas_por_cobrar_pago")
public class CuentasPorCobrarPago extends Model implements Serializable {

    @Column(name = "cuentas_por_cobrar")
    protected CuentasPorCobrar cuentasPorCobrar;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    @Column(name = "monto")
    protected BigDecimal monto;

    @Column(name="fecha_pago")
    protected Date fechaPago;


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


}
