package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name="facturas")
public class Facturas extends Model{

	@Column(name="fecha")
	protected
	Date fecha;

	@Column(name="firma")
	protected
	String firma;

	@Column(name="contacto")
	protected
	Contactos contacto;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Contactos getContacto() {
		return contacto;
	}

	public void setContacto(Contactos contacto) {
		this.contacto = contacto;
	}
}
