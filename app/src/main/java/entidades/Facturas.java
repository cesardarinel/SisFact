package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name="facturas")
public class Facturas extends Model implements Serializable{

	protected Long internalId;

	@Column(name="fecha")
	protected Date fecha;

	@Column(name="contacto")
	protected Contactos contacto;


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Contactos getContacto() {
		return contacto;
	}

	public void setContacto(Contactos contacto) {
		this.contacto = contacto;
	}

	public Long getInternalId() {
		if (getId() != null) return getId();
		return internalId;
	}

	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}
}
