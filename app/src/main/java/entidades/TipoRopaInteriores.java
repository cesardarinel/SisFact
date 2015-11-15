package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.io.Serializable;

@Table(name="tipo_ropainterior")
public class TipoRopaInteriores extends Model implements Serializable{
    @Column(name = "tipo_ropa_interior")
    private String tipoRopaInterioir;

    public String getTipoRopaInterioir() {
        return tipoRopaInterioir;
    }

    public void setTipoRopaInterioir(String tipoRopaInterioir) {
        this.tipoRopaInterioir = tipoRopaInterioir;
    }
}
