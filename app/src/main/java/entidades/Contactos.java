package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="contactos")
public class Contactos extends Model{


	@Column(name="nombre")
	private String nombre;


	@Column(name="apellido")
	private String apellido;

	@Column(name="telefono")
	private String telefono;

	@Column(name="celular")
	private String celular;

	@Column(name="correo")
	private String correo;

	@Column(name="direccion")
	private String direccion;

	@Column(name="es_cliente")
	private boolean esCliente;

	@Column(name="es_suplidor")
	private boolean esSuplidor;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isEsCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}

	public boolean isEsSuplidor() {
		return esSuplidor;
	}

	public void setEsSuplidor(boolean esSuplidor) {
		this.esSuplidor = esSuplidor;
	}
}
