package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.math.BigDecimal;
import java.util.Date;

@Table(name="cunetas_por_cobrar_pago")
public class CuentaPorPagarPagos extends Model{

    @Column(name = "cuenta_por_pagar")
    protected
    CuentasPorCobrar cuentasPorCobrar;

    @Column(name = "fecha_pago")
    protected
    Date fechaPago;

    @Column(name = "monto")
    protected
    BigDecimal monto;

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

    public BigDecimal getMono() {
        return monto;
    }

    public void setMono(BigDecimal mono) {
        this.monto = mono;
    }
}
