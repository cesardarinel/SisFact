package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import entidades.Contactos;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.Contacto;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;

public class AdaptadorContacto extends FactoryAdaptadorGenerico {
    public AdaptadorContacto(){
        titulo = "Contactos";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Todos: Nombre");
        camposBuscables.add("Cliente: Nombre");
        camposBuscables.add("Suplidor: Nombre");

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
                case "Todos: Nombre":
                    if (!contactos.getNombre().contains(Valor)) esValido = false;
                    break;
                case "Cliente: Nombre":
                    if (!contactos.getNombre().contains(Valor) && contactos.isEsCliente()) esValido = false;
                    break;
                case "Suplidor: Nombre":
                    if (!contactos.getNombre().contains(Valor) && contactos.isEsSuplidor()) esValido = false;
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
