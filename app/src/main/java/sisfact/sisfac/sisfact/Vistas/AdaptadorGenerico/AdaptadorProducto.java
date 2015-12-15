package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Objects;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;
import sisfact.sisfac.sisfact.Vistas.Productos;

public class AdaptadorProducto extends FactoryAdaptadorGenerico {
    public AdaptadorProducto(){
        titulo = "Productos";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Nombre");
        camposBuscables.add("Marca");
        camposBuscables.add("Tipo");

        camposBuscables.add("Session");
        camposBuscables.add("Suplidor");
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor){
        objetosListado = new ArrayList<>();

        for(Object obj : getObjectosAFiltrar()){
            entidades.Productos productos = (entidades.Productos) obj;
            boolean esValido = true;
            if (productos == null) continue;
            switch (Campo){
                case "Nombre":
                    if (!productos.getNombre().contains(Valor)) esValido = false;
                    break;
                case "Marca":
                    if (!productos.getNombre().contains(Valor)) esValido = false;
                    break;
                case "Tipo":
                    if (!productos.getTipo().contains(Valor)) esValido = false;
                    break;
                case "Session":
                    if (!productos.getSeccion().getSeccion().contains(Valor)) esValido = false;
                    break;
                case "Suplidor":
                    if (!productos.getContacto().getNombre().contains(Valor)) esValido = false;
                    break;
            }
            if (esValido){
                ItemLista itemLista =  new ItemLista();
                itemLista.setId(productos.getInternalId().toString());
                itemLista.setTexto1(productos.getNombre());
                itemLista.setTexto2(productos.getMarca().getNombre());
                itemLista.setInfo(productos.getTipo());
                getObjetosListado().add(itemLista);
            }
        }
        return new ListaAdaptador(con, getObjetosListado());
    }

    @Override
    public Intent getIntentClase(Context con){
        return new Intent(con,Productos.class);
    }

    @Override
    protected Object objAgregar(Long id){
        return new Select()
                .from(entidades.Productos.class)
                .where("id = ?",id)
                .executeSingle();
    }

    @Override
    public void update(){
        ArrayList<entidades.Productos> productoses = new ArrayList<>();
        for (Object obj : objectosAFiltrar){
            entidades.Productos viejo = (entidades.Productos) obj;
            entidades.Productos nuevo  = new Select().from(entidades.Productos.class).where("id = ?", viejo.getInternalId()).executeSingle();
            productoses.add(nuevo);
        }
        objectosAFiltrar = productoses;
    }

}
