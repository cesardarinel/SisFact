package entidades;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */

@Table(name="ropa_interioir")
public class RopaInterioires extends  Productos{
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
}
