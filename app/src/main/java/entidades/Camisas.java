package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.io.Serializable;

@Table(name="camisas")
public class Camisas extends Model implements Serializable{

    @Column(name="producto")
    protected Productos producto;

    @Column(name="tamano")
    protected String tamano;

    @Column(name = "tipo_camisa")
    protected TipoCamisas tipoCamisa;

    @Column(name="tipo_manga")
    protected TipoMangas tipoManga;


    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public TipoCamisas getTipoCamisa() {
        return tipoCamisa;
    }

    public void setTipoCamisa(TipoCamisas tipoCamisa) {
        this.tipoCamisa = tipoCamisa;
    }

    public TipoMangas getTipoManga() {
        return tipoManga;
    }

    public void setTipoManga(TipoMangas tipoManga) {
        this.tipoManga = tipoManga;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
}
