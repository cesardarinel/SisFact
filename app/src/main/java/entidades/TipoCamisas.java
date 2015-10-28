package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by RynaMartinez on 10/28/2015.
 */
@Table(name = "tipo_camisas")
public class TipoCamisas extends Model {
    @Column(name = "tipo_camisa")
    private String tipoCamisa;

    public String getTipoCamisa() {
        return tipoCamisa;
    }

    public void setTipoCamisa(String tipoCamisa) {
        this.tipoCamisa = tipoCamisa;
    }
}
