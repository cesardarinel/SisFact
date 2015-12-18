package sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import entidades.Contactos;
import entidades.CuentaPorPagarPagos;
import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.Vistas.Contacto;
import sisfact.sisfac.sisfact.Vistas.ListaAdaptador;
import sisfact.sisfac.sisfact.Vistas.vista_cuenta_por_pagar;

public class AdaptadorCuentasPorPagar extends  FactoryAdaptadorGenerico{
    public AdaptadorCuentasPorPagar(){
        titulo = "Cuenta Por Pagar";
        camposBuscables = new ArrayList<>();
        camposBuscables.add("Todos: Nombre");
        camposBuscables.add("Cliente: Nombre");
        camposBuscables.add("Suplidor: Nombre");

        camposBuscables.add("Telefono");
        camposBuscables.add("Celular");

        //camposBuscables.add("Direccion");
    }

    @Override
    public ArrayList<?> getObjetosFiltradoPor(Action act){
        objetosListado = new ArrayList<>();
        ArrayList<CuentasPorPagar> cuentasPorPagars =  new ArrayList<>();
        for(Object obj : getObjectosAFiltrar()){
            CuentasPorPagar cuentasPorPagar;
            cuentasPorPagar = (CuentasPorPagar)obj;
            if (act.CompraraObjeto(cuentasPorPagar)){
                cuentasPorPagars.add(cuentasPorPagar);
            }
        }
        return cuentasPorPagars;
    }

    @Override
    public ListaAdaptador getCamposaFiltrar(Context con,String Campo,String Valor) {
        objetosListado = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));

        for (Object obj : getObjectosAFiltrar()) {
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar) obj;
            if (cuentasPorPagar == null)
                continue;
            boolean esValido = true;
            switch (Campo) {
                case "Todos: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().toLowerCase().contains(Valor.toLowerCase())) esValido = false;
                    break;
                case "Cliente: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().toLowerCase().contains(Valor.toLowerCase()) && cuentasPorPagar.getContacto().isEsCliente())
                        esValido = false;
                    break;
                case "Suplidor: Nombre":
                    if (!cuentasPorPagar.getContacto().getNombre().toLowerCase().contains(Valor.toLowerCase()) && cuentasPorPagar.getContacto().isEsSuplidor())
                        esValido = false;
                    break;
                case "Telefono":
                    if (!cuentasPorPagar.getContacto().getTelefono().contains(Valor)) esValido = false;
                    break;
                case "Celular":
                    if (!cuentasPorPagar.getContacto().getCelular().contains(Valor)) esValido = false;
                    break;
                case "Direccion":
                    if (!cuentasPorPagar.getContacto().getDireccion().contains(Valor)) esValido = false;
                    break;
            }
            if (esValido) {
                ItemLista itemLista = new ItemLista();
                itemLista.setId(cuentasPorPagar.getInternalId().toString());
                itemLista.setTexto1(cuentasPorPagar.getContacto().getNombre() + " " + cuentasPorPagar.getContacto().getApellido());
                itemLista.setTexto2("Monto: " + cuentasPorPagar.getMonto());
                itemLista.setInfo("Fecha Creación: " + dateFormatter.format(cuentasPorPagar.getFechaCreada()));
                getObjetosListado().add(itemLista);
            }
        }
        return new ListaAdaptador(con, getObjetosListado());
    }
    @Override
    public Intent getIntentClase(Context con){
        //TODO
        return new Intent(con,vista_cuenta_por_pagar.class);
    }
    //TODO
    @Override
    protected Object objAgregar(Long id){
        return new Select()
                .from(CuentasPorPagar.class)
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
