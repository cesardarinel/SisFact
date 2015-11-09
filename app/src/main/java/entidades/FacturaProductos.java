package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
public class FacturaProductos extends Model {

    @Column(name = "factura")
    protected Facturas factura;

    @Column(name = "vista_producto")
    protected Productos producto;

    @Column(name = "cantidad")
    protected int cantidad;

    @Column(name = "descuento")
    protected float descuento;

    @Column(name = "es_porciento")
    protected boolean esPorciento;

    @Column(name = "precio_historico")
    protected float precioHistorico;

    public Facturas getFactura() {
        return factura;
    }

    public void setFactura(Facturas factura) {
        this.factura = factura;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public boolean isEsPorciento() {
        return esPorciento;
    }

    public void setEsPorciento(boolean esPorciento) {
        this.esPorciento = esPorciento;
    }

    public float getPrecioHistorico() {
        return precioHistorico;
    }

    public void setPrecioHistorico(float precioHistorico) {
        this.precioHistorico = precioHistorico;
    }
}
