package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name="secciones")
public class Secciones extends Model {
    @Column(name="seccion")
    private String seccion;

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
