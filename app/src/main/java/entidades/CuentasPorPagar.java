package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name ="cunetas_por_pagar")
public class CuentasPorPagar extends Model implements Serializable {



    protected Long internalId;

    @Column(name = "contacto")
    protected Contactos contacto;

    @Column(name = "monto")
    protected BigDecimal monto;

    @Column(name="descripcion")
    protected String descripcion;

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

    @Column(name = "fecha_Creada")
    Date fechaCreada;

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
        if (getId() != null) return getId();
        return internalId;
    }
    
    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    public Boolean EstaPagado(){
        List<CuentaPorPagarPagos> cuentasPorCobrarPagos = new Select()
                .from(CuentaPorPagarPagos.class)
                .where("cuenta_por_pagar = ? ",getInternalId())
                .execute();
        BigDecimal total = BigDecimal.ZERO;
        for (CuentaPorPagarPagos item : cuentasPorCobrarPagos){
            total = total.add(item.getMonto());
        }
        return (total.compareTo(monto) == 0);
    }
}
