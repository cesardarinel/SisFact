package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.math.BigDecimal;

@Table(name ="cunetas_por_pagar")
public class CuentasPorPagar extends Model{



    protected Long internalId;

    @Column(name = "contacto")
    protected
    Contactos contacto;

    @Column
    protected
    BigDecimal monto;

    public Contactos getContacto() {
        return contacto;
    }

    public void setContacto(Contactos contacto) {
        this.contacto = contacto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }
}
