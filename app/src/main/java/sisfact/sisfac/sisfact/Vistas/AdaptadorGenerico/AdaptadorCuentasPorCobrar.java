package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.Contacto;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;
import sisfact.sisfac.sisfact.Vistas.vista_cuenta_por_pagar;

public class AdaptadorCuentasPorCobrar extends FactoryAdaptadorGenerico {
    public AdaptadorCuentasPorCobrar(){
        titulo = "Cuenta Por Cobrar";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Todos: Nombre");
        camposBuscables.add("Cliente: Nombre");
        camposBuscables.add("Suplidor: Nombre");

        camposBuscables.add("Telefono");
        camposBuscables.add("Direccion");
    }

    @Override
    public ArrayList<?> getObjetosFiltradoPor(Action act){
        objetosListado = new ArrayList<>();
        ArrayList<CuentasPorCobrar> cuentasPorCobrars =  new ArrayList<>();
        for(Object obj : getObjectosAFiltrar()){
            CuentasPorCobrar cuentasPorCobrar;
            cuentasPorCobrar = (CuentasPorCobrar)obj;
            if (act.CompraraObjeto(cuentasPorCobrar)){
                cuentasPorCobrars.add(cuentasPorCobrar);
            }
        }
        return cuentasPorCobrars;
    }


    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor) {
        objetosListado = new ArrayList<>();

        for (Object obj : getObjectosAFiltrar()) {
            CuentasPorCobrar cuentasPorPagar = (CuentasPorCobrar) obj;
            boolean esValido = true;
            switch (Campo) {
                case "Todos: Nombre":
                    if (!cuentasPorPagar.getFactura().getContacto().getNombre().contains(Valor)) esValido = false;
                    break;
                case "Cliente: Nombre":
                    if (!cuentasPorPagar.getFactura().getContacto().getNombre().contains(Valor) && cuentasPorPagar.getFactura().getContacto().isEsCliente())
                        esValido = false;
                    break;
                case "Suplidor: Nombre":
                    if (!cuentasPorPagar.getFactura().getContacto().getNombre().contains(Valor) && cuentasPorPagar.getFactura().getContacto().isEsSuplidor())
                        esValido = false;
                    break;
                case "Telefono":
                    if (!cuentasPorPagar.getFactura().getContacto().getTelefono().contains(Valor)) esValido = false;
                    break;
                case "Direccion":
                    if (!cuentasPorPagar.getFactura().getContacto().getDireccion().contains(Valor)) esValido = false;
                    break;
            }
            if (esValido) {
                ItemLista itemLista = new ItemLista();
                itemLista.setId(cuentasPorPagar.getFactura().getContacto().getInternalId().toString());
                itemLista.setInfo(cuentasPorPagar.getFactura().getContacto().getNombre());
                itemLista.setTexto1(cuentasPorPagar.getFactura().getContacto().getApellido());
                itemLista.setTexto2(cuentasPorPagar.getFactura().getContacto().getTelefono());
                getObjetosListado().add(itemLista);
            }
        }
        return new ListaAdaptador(con, getObjetosListado());
    }
    @Override
    public Intent getIntentClase(Context con){
        Intent rIntent = new Intent(con,vista_cuenta_por_pagar.class);
        rIntent.putExtra("esCobros",true);
        return rIntent;
    }
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
        ArrayList<CuentasPorCobrar> contactoses = new ArrayList<>();
        for (Object obj : objectosAFiltrar){
            CuentasPorCobrar viejo = (CuentasPorCobrar)obj;
            CuentasPorCobrar nuevo  = new Select().from(CuentasPorCobrar.class).where("id = ?",viejo.getInternalId()).executeSingle();
            contactoses.add(nuevo);
        }
        objectosAFiltrar = contactoses;
    }
}
