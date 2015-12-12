package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;

public class FactoryAdaptadorGenerico implements Serializable{

    public enum Adaptador{
        Contacto,
        Producto,
        Factura,
        CuentasPorCobrar,
        CuentasPorPagar
    }

    public String getTitulo() {
        return titulo;
    }

    protected ItemLista nuevoItem;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    protected  String titulo;
    public static FactoryAdaptadorGenerico getAdaptador(Adaptador adapt){
        FactoryAdaptadorGenerico r=null;
        switch (adapt){
            case Contacto:
                r =  new AdaptadorContacto();
                break;
            case Producto:
                r = new AdaptadorProducto();
                break;
            case Factura:
                r = new AdaptadorFactura();
                break;
            case CuentasPorPagar:
                r =  new AdaptadorCuentasPorPagar();
                break;
            case CuentasPorCobrar:
                r = new AdaptadorCuentasPorCobrar();
                break;
        }
        return r;
    }

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
    }

    public  ArrayAdapter<String> getCamposBuscablesAdator(Context con){
        return new ArrayAdapter<>(con,android.R.layout.simple_spinner_dropdown_item,camposBuscables);
    }

    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor){return null;}

    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor,List<?> lista){
        List<?> tmp = objectosAFiltrar;
        objectosAFiltrar = lista;
        ListaAdaptador listaAdaptador = this.getCamposaFiltrar(con,Campo,Valor);
        objectosAFiltrar = tmp;
        return listaAdaptador;
    }

    public ListaAdaptador getCamposaFiltrar(Context con,Action act){return null;}

    public Intent getIntentClase(Context con){return null;}

    protected Object objAgregar(Long id){return null;}



    public  void agregar(Long itemLista){
        ArrayList<Object> genList = new ArrayList<>();
        for (Object obj : objectosAFiltrar){
            genList.add(obj);
        }
        genList.add(objAgregar(itemLista));
        objectosAFiltrar = genList;
    }
    public void update(){}
}


