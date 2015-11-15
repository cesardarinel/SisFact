package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;

public class FactoryAdaptadorGenerico implements Serializable{
    protected List<String> camposBuscables;
    protected List<?> objectosAFiltrar;
    protected ArrayList<ItemLista> objetosListado;

    public List<?> getObjectosAFiltrar() {
        return objectosAFiltrar;
    }

    public void setObjectosAFiltrar(List<?> objectosAFiltrar) {
        this.objectosAFiltrar = objectosAFiltrar;
    }

    public ArrayList<ItemLista> getObjetosListado() {
        return objetosListado;
    }            boolean esValido = true;


    public void setObjetosListado(ArrayList<ItemLista> objetosListado) {
        this.objetosListado = objetosListado;
    }


    public enum Adaptador{
        Contacto
    }
    public static FactoryAdaptadorGenerico getAdaptador(Adaptador adapt){
        FactoryAdaptadorGenerico r=null;
        switch (adapt){
            case Contacto:
                r =  new AdaptadorContacto();
                break;
        }
        return r;
    }




    public  ArrayAdapter<String> getCamposBuscablesAdator(Context con){
        return new ArrayAdapter<>(con,android.R.layout.simple_spinner_dropdown_item,camposBuscables);
    }

    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor){return null;}

    public Intent getIntentClase(Context con){return null;}

}
