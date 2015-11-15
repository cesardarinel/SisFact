package entidades;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.io.Serializable;


@Table(name = "tipo_camisas")
public class TipoCamisas extends Model implements Serializable{
    @Column(name = "tipo_camisa")
    private String tipoCamisa;

    public String getTipoCamisa() {
        return tipoCamisa;
    }

    public void setTipoCamisa(String tipoCamisa) {
        this.tipoCamisa = tipoCamisa;
    }
}
