package entidades.ActiveAndroid;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.activeandroid.query.Select;

import entidades.Categorias;
import entidades.Marcas;
import entidades.Secciones;


public class init extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        String [] marcasPorDefecto  = new String []{"Marca 1","Marca 2"};
        String [] seccionPorDefecto  = new String []{"Hombre","Mujer","Niña","Niño"};
        String [] categoriaPorDefecto  = new String []{"Casual","Formal","Otro"};

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

    }
}