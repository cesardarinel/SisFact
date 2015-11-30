package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name="Productos")
public class Productos  extends  Model implements Serializable{
    protected Long internalId;

    @Column(name="nombre")
    protected String nombre;

    @Column(name = "marca")
    protected Marcas marca;

    @Column(name = "codigo_barra")
    protected String codigoBarra;

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @Column(name="ruta_imagen")
    protected String rutaImagen;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "cantidad")
    protected Integer cantidad;

    @Column(name="precio")
    protected BigDecimal precio;

    @Column(name="contacto")
    protected Contactos contacto;

    @Column(name="seccion")
    protected Secciones seccion;

    @Column(name="tipo")
    protected String tipo;

    @Column(name="Categoria")
    protected Categorias Categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Contactos getContacto() {
        return contacto;
    }

    public void setContacto(Contactos contacto) {
        this.contacto = contacto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSeccion(Secciones seccion) {
        this.seccion = seccion;
    }

    public void setCategoria(Categorias categoria) {
        Categoria = categoria;
    }

    public Secciones getSeccion() {
        return seccion;
    }

    public Categorias getCategoria() {
        return Categoria;
    }

    public Long getInternalId() {
        if (getId() != null) return  getId();
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
