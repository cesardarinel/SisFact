package entidades.ActiveAndroid;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.activeandroid.query.Select;

import entidades.Categorias;
import entidades.Marcas;
import entidades.RopaInterioires;
import entidades.Secciones;
import entidades.TipoCamisas;
import entidades.TipoMangas;
import entidades.TipoRopaInteriores;


public class init extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        String [] marcasPorDefecto  = new String []{"Marca 1","Marca 2"};
        String [] seccionPorDefecto  = new String []{"Hombre","Mujer","Niña","Niño"};
        String [] categoriaPorDefecto  = new String []{"Casual","Formal","Otro"};
        String [] tipoManga = new String[]{"Sin Mangas","Corta","Larga"};
        String [] tipoCamisa = new String[]{"Camisa","Polo Shirt"};
        String [] tiporopaInterior = new String[]{"Normal","Lingerie"};
        for (String val : marcasPorDefecto){
            Marcas marcas =  new Select().from(Marcas.class).where("nombre = ? " ,val).executeSingle();
            if (marcas == null){
                marcas = new Marcas();
                marcas.setNombre(val);
                marcas.save();
            }
        }

        for (String val : seccionPorDefecto){
            Secciones secciones =  new Select().from(Secciones.class).where("seccion = ? " ,val).executeSingle();
            if (secciones == null){
                secciones = new Secciones();
                secciones.setSeccion(val);
                secciones.save();
            }
        }

        for (String val : categoriaPorDefecto){
            Categorias categorias =  new Select().from(Categorias.class).where("categoria = ? " ,val).executeSingle();
            if (categorias == null){
                categorias = new Categorias();
                categorias.setCategoria(val);
                categorias.save();
            }
        }

        for (String val : tipoManga){
            TipoMangas tipoMangas =  new Select().from(TipoMangas.class).where("tipo_manga = ? " ,val).executeSingle();
            if (tipoMangas == null){
                tipoMangas = new TipoMangas();
                tipoMangas.setTipoManga(val);
                tipoMangas.save();
            }
        }

        for (String val : tipoCamisa){
            TipoCamisas tipoCamisas =  new Select().from(TipoCamisas.class).where("tipo_camisa = ? " ,val).executeSingle();
            if (tipoCamisas == null){
                tipoCamisas = new TipoCamisas();
                tipoCamisas.setTipoCamisa(val);
                tipoCamisas.save();
            }
        }

        for (String val : tiporopaInterior){
            TipoRopaInteriores tipoMangas =  new Select().from(TipoRopaInteriores.class).where("tipo_ropa_interior = ? " ,val).executeSingle();
            if (tipoMangas == null){
                tipoMangas = new TipoRopaInteriores();
                tipoMangas.setTipoRopaInterioir(val);
                tipoMangas.save();
            }
        }

    }
}