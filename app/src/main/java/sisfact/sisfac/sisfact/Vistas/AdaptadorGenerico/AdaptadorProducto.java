package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;
import sisfact.sisfac.sisfact.Vistas.Productos;

public class AdaptadorProducto extends FactoryAdaptadorGenerico {
    public AdaptadorProducto(){
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
                itemLista.setInfo(productos.getTipo());
                itemLista.setTexto1(productos.getNombre());
                itemLista.setTexto2(null);
                getObjetosListado().add(itemLista);
            }
        }
        ListaAdaptador listadoAdaptr = new ListaAdaptador(con, getObjetosListado());
        return listadoAdaptr;
    }
    @Override
    public Intent getIntentClase(Context con){
        return new Intent(con,Productos.class);
    }
}
