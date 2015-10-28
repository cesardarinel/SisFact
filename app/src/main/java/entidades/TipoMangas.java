package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name="tipo_mangas")
public class TipoMangas extends Model {
    @Column(name="tipo_manga")
    private String tipoManga;

    public String getTipoManga() {
        return tipoManga;
    }

    public void setTipoManga(String tipoManga) {
        this.tipoManga = tipoManga;
    }
}
