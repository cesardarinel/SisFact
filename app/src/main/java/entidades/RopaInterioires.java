package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.io.Serializable;


@Table(name="ropa_interioir")
public class RopaInterioires extends Model implements Serializable{


    @Column(name="producto")
    protected Productos producto;

    @Column(name="tipo_roba_interior")
    protected TipoRopaInteriores tipoRopaInterior;

    @Column(name = "medida_1")
    protected String medida1;

    @Column(name = "medida_2")
    protected String medida2;


    public TipoRopaInteriores getTipoRopaInterior() {
        return tipoRopaInterior;
    }

    public void setTipoRopaInterior(TipoRopaInteriores tipoRopaInterior) {
        this.tipoRopaInterior = tipoRopaInterior;
    }

    public String getMedida1() {
        return medida1;
    }

    public void setMedida1(String medida1) {
        this.medida1 = medida1;
    }

    public String getMedida2() {
        return medida2;
    }

    public void setMedida2(String medida2) {
        this.medida2 = medida2;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
}
