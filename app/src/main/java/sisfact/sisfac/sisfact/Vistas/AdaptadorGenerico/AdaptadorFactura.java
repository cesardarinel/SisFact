package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;


import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import entidades.Facturas;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;
import sisfact.sisfac.sisfact.Vistas.Productos;
import sisfact.sisfac.sisfact.Vistas.vista_factura;

public class AdaptadorFactura extends  FactoryAdaptadorGenerico{
    public AdaptadorFactura(){
        titulo = "Facturas";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Nombre");
        camposBuscables.add("Fecha");
        camposBuscables.add("Contacto");
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor){
        objetosListado = new ArrayList<>();

        for(Object obj : getObjectosAFiltrar()){
           Facturas facturas = (Facturas) obj;
            boolean esValido = true;
            switch (Campo){
                case "Nombre":
                    if (!facturas.getInternalId().toString().toLowerCase().contains(Valor.toLowerCase())) esValido = false;
                    break;
                case "Fecha":
                    if (!facturas.getFecha().toString().toLowerCase().contains(Valor.toLowerCase())) esValido = false;
                    break;
                case "Contacto":
                    if (!facturas.getContacto().getNombre().toLowerCase().contains(Valor.toLowerCase())) esValido = false;
                    break;
            }
            if (esValido){
                ItemLista itemLista =  new ItemLista();
                itemLista.setId(facturas.getInternalId().toString());
                itemLista.setInfo(facturas.getContacto().getNombre());
                itemLista.setTexto1(facturas.getFecha().toString());
                itemLista.setTexto2(null);
                getObjetosListado().add(itemLista);
            }
        }

        return new ListaAdaptador(con, getObjetosListado());
    }
    @Override
    public Intent getIntentClase(Context con){
        return new Intent(con,vista_factura.class);
    }
    @Override
    protected Object objAgregar(Long id){
        return new Select().
                from(Facturas.class)
                .where("id = ?",id)
                .executeSingle();
    }
    @Override
    public void update(){
        ArrayList<Facturas> facturases = new ArrayList<>();
        for (Object obj : objectosAFiltrar){
            Facturas viejo = (Facturas)obj;
            Facturas nuevo  = new Select().from(Facturas.class).where("id = ?",viejo.getInternalId()).executeSingle();
            facturases.add(nuevo);
        }
        objectosAFiltrar = facturases;
    }
}
