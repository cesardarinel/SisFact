package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import entidades.Contactos;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.Contacto;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;

public class AdaptadorContacto extends FactoryAdaptadorGenerico {
    public AdaptadorContacto(){
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Nombre");
        camposBuscables.add("Telefono");
        camposBuscables.add("Direccion");
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor){
        objetosListado = new ArrayList<>();

        for(Object obj : getObjectosAFiltrar()){
            Contactos contactos = (Contactos)obj;
            boolean esValido = true;
            switch (Campo){
                case "Nombre":
                    if (!contactos.getNombre().contains(Valor)) esValido = false;
                    break;
                case "Telefono":
                    if (!contactos.getTelefono().contains(Valor)) esValido = false;
                    break;
                case "Direccion":
                    if (!contactos.getDireccion().contains(Valor)) esValido = false;
                    break;
            }
            if (esValido){
                ItemLista itemLista =  new ItemLista();
                itemLista.setId(contactos.getInternalId().toString());
                itemLista.setInfo(contactos.getNombre());
                itemLista.setTexto1(contactos.getApellido());
                itemLista.setTexto2(contactos.getTelefono());
                getObjetosListado().add(itemLista);
            }
        }
        ListaAdaptador listadoAdaptr = new ListaAdaptador(con, getObjetosListado());
        return listadoAdaptr;
    }
    @Override
    public Intent getIntentClase(Context con){
        return new Intent(con,Contacto.class);
    }
}
