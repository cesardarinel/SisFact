package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import entidades.Contactos;
import entidades.CuentaPorPagarPagos;
import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.Contacto;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;

public class AdaptadorCuentasPorPagar extends  FactoryAdaptadorGenerico{
    public AdaptadorCuentasPorPagar(){
        titulo = "Cuenta Por Pagar";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Todos: Nombre");
        camposBuscables.add("Cliente: Nombre");
        camposBuscables.add("Suplidor: Nombre");

        camposBuscables.add("Telefono");
        camposBuscables.add("Direccion");
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,Action act){
        objetosListado = new ArrayList<>();

        for(Object obj : getObjectosAFiltrar()){
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar)obj;
            if (act.CompraraObjeto(cuentasPorPagar)){
                ItemLista itemLista =  new ItemLista();
                itemLista.setId(cuentasPorPagar.getContacto().getInternalId().toString());
                itemLista.setInfo(cuentasPorPagar.getContacto().getNombre());
                itemLista.setTexto1(cuentasPorPagar.getContacto().getApellido());
                itemLista.setTexto2(cuentasPorPagar.getContacto().getTelefono());
                getObjetosListado().add(itemLista);
            }
        }
        ListaAdaptador listadoAdaptr = new ListaAdaptador(con, getObjetosListado());

        return listadoAdaptr;
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor) {
        objetosListado = new ArrayList<>();

        for (Object obj : getObjectosAFiltrar()) {
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar) obj;
            boolean esValido = true;
            switch (Campo) {
                case "Todos: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().contains(Valor)) esValido = false;
                    break;
                case "Cliente: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().contains(Valor) && cuentasPorPagar.getContacto().isEsCliente())
                        esValido = false;
                    break;
                case "Suplidor: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().contains(Valor) && cuentasPorPagar.getContacto().isEsSuplidor())
                        esValido = false;
                    break;
                case "Telefono":
                    if (!cuentasPorPagar.getContacto().getTelefono().contains(Valor)) esValido = false;
                    break;
                case "Direccion":
                    if (!cuentasPorPagar.getContacto().getDireccion().contains(Valor)) esValido = false;
                    break;
            }
            if (esValido) {
                ItemLista itemLista = new ItemLista();
                itemLista.setId(cuentasPorPagar.getContacto().getInternalId().toString());
                itemLista.setInfo(cuentasPorPagar.getContacto().getNombre());
                itemLista.setTexto1(cuentasPorPagar.getContacto().getApellido());
                itemLista.setTexto2(cuentasPorPagar.getContacto().getTelefono());
                getObjetosListado().add(itemLista);
            }
        }
        return new ListaAdaptador(con, getObjetosListado());
    }
    @Override
    public Intent getIntentClase(Context con){
        //TODO
        return new Intent(con,Contacto.class);
    }
    //TODO
    @Override
    protected Object objAgregar(Long id){
        return new Select()
                .from(CuentasPorCobrar.class)
                .where("id = ?",id)
                .executeSingle();
    }
    //TODO
    @Override
    public void update(){
        ArrayList<CuentasPorPagar> contactoses = new ArrayList<>();
        for (Object obj : objectosAFiltrar){
            CuentasPorPagar viejo = (CuentasPorPagar)obj;
            CuentasPorPagar nuevo  = new Select().from(CuentasPorPagar.class).where("id = ?",viejo.getInternalId()).executeSingle();
            contactoses.add(nuevo);
        }
        objectosAFiltrar = contactoses;
    }
}
