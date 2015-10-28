package entidades;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name = "pantalones")
public class Pantalones extends Productos {

    @Column(name = "largo")
    protected float largo;

    @Column(name = "ancho")
    protected float ancho;

    @Column(name = "size")
    protected String size;

    public float getLargo() {
        return largo;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
