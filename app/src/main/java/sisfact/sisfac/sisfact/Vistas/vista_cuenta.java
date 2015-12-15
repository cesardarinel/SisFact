package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import entidades.Contactos;
import entidades.CuentaPorPagarPagos;
import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.AdaptadorCuentasPorCobrar;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class vista_cuenta extends AppCompatActivity   implements View.OnClickListener{
    //Botones
    ImageButton cpp,cpc;
    Intent nuevaActividad;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cuenta);
        //inicialisamos variables
        cpp=(ImageButton)findViewById(R.id.cpp);
        cpc=(ImageButton)findViewById(R.id.cpc);
        //----------------setOnClickListener--------------------
        cpp.setOnClickListener(this);
        cpc.setOnClickListener(this);

    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //cuentas por cobrar
         if(R.id.cpc==v.getId()){
             nuevaActividad = new Intent(this,vista_modulo_generic.class);
             FactoryAdaptadorGenerico factoryAdaptadorGenerico = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.CuentasPorCobrar);
             List<CuentasPorCobrar> cuentasPorCobrars= new Select().from(CuentasPorCobrar.class).execute();
             factoryAdaptadorGenerico.setObjectosAFiltrar(cuentasPorCobrars);
             for(CuentasPorCobrar item : cuentasPorCobrars) {
                 item.setInternalId(item.getId());
                 item.getFactura().setInternalId(item.getFactura().getId());
             }
             nuevaActividad.putExtra("Actividad", "Cuentas Por Pagar");
             nuevaActividad.putExtra("Datos",factoryAdaptadorGenerico);
             nuevaActividad.putExtra("fecha",true);
             startActivity(nuevaActividad);

         }
        //cuentas por pagar
        if(R.id.cpp == v.getId()) {
            nuevaActividad = new Intent(this, vista_modulo_generic.class);
            FactoryAdaptadorGenerico factoryAdaptadorGenerico = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.CuentasPorPagar);
            List<CuentasPorPagar> cuentasPorPagarList= new Select().from(CuentasPorPagar.class).execute();
            for(CuentasPorPagar item : cuentasPorPagarList) {
                item.getContacto().setInternalId(item.getContacto().getId());
                item.setInternalId(item.getId());
            }
            factoryAdaptadorGenerico.setObjectosAFiltrar(cuentasPorPagarList);

            nuevaActividad.putExtra("Datos", factoryAdaptadorGenerico);
            nuevaActividad.putExtra("fecha",true);
            startActivity(nuevaActividad);
        }
    }
}
