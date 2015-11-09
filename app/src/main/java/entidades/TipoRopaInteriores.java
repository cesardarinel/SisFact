package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name="tipo_ropainterior")
public class TipoRopaInteriores extends Model{
    @Column(name = "tipo_ropa_interior")
    private String tipoRopaInterioir;

    public String getTipoRopaInterioir() {
        return tipoRopaInterioir;
    }

    public void setTipoRopaInterioir(String tipoRopaInterioir) {
        this.tipoRopaInterioir = tipoRopaInterioir;
    }
}
