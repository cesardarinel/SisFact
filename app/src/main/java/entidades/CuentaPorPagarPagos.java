package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name="cunetas_por_cobrar_pago")
public class CuentaPorPagarPagos  extends Model implements Serializable {

    public CuentasPorPagar getCuentasPorPagar() {
        return cuentasPorPagar;
    }

    public void setCuentasPorPagar(CuentasPorPagar cuentasPorPagar) {
        this.cuentasPorPagar = cuentasPorPagar;
    }

    @Column(name = "cuenta_por_pagar")
    protected
    CuentasPorPagar cuentasPorPagar;

    @Column(name = "fecha_pago")
    protected
    Date fechaPago;

    @Column(name = "monto")
    protected
    BigDecimal monto;



    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal mono) {
        this.monto = mono;
    }
}
