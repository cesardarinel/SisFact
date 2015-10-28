package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name="categorias")
public class Categorias extends Model {
    @Column(name = "categoria")
    private String categoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
