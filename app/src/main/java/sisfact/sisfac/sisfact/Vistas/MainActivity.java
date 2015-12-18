package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import android.widget.ImageButton;

import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import entidades.*;
import entidades.Productos;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.AdaptadorContacto;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //botones del menu
    ImageButton  factura,producto,contacto,cuenta,reporte;
    Intent nuevaActividad;
    Gson serializeJson = new Gson();
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOverflowMenu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicialisamos variables
        factura= (ImageButton )findViewById(R.id.factura);
        producto= (ImageButton )findViewById(R.id.productos);
        contacto= (ImageButton )findViewById(R.id.contacto);

        cuenta= (ImageButton )findViewById(R.id.Cuenta);
        reporte= (ImageButton )findViewById(R.id.reporte);
        boton = (Button) findViewById(R.id.ver_todos_los_productos);

        //----------------setOnClickListener--------------------
        factura.setOnClickListener(this);
        producto.setOnClickListener(this);
        contacto.setOnClickListener(this);
        cuenta.setOnClickListener(this);
        reporte.setOnClickListener(this);
        boton.setOnClickListener(this);

    }
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        FactoryAdaptadorGenerico fact;
        switch (v.getId()) {
            case R.id.factura:
                nuevaActividad = new Intent(this, vista_modulo_generic.class);
                FactoryAdaptadorGenerico factoryAdaptadorGenerico = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.Factura);
                List<Facturas> facturasList = new Select().from(Facturas.class).execute();
                for (Facturas facturas : facturasList) facturas.setInternalId(facturas.getId());
                factoryAdaptadorGenerico.setObjectosAFiltrar(facturasList);
                nuevaActividad.putExtra("Datos",factoryAdaptadorGenerico);
                startActivity(nuevaActividad);
                break;

            case R.id.productos:
                nuevaActividad = new Intent(this,vista_modulo_generic.class);
                fact = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.Producto);
                List<entidades.Productos> productosList = new Select().from(entidades.Productos.class).execute();
                for (entidades.Productos con : productosList) con.setInternalId(con.getId());
                fact.setObjectosAFiltrar(productosList);
                nuevaActividad.putExtra("Datos",fact);
                startActivity(nuevaActividad);
                break;

            case R.id.contacto:
                nuevaActividad = new Intent(this, vista_modulo_generic.class);
                nuevaActividad.putExtra("Actividad", "Contactos");
                fact = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.Contacto);
                List<Contactos> contactosArrayList = new Select().from(Contactos.class).execute();
                for (Contactos con : contactosArrayList) con.setInternalId(con.getId());
                fact.setObjectosAFiltrar(contactosArrayList);
                nuevaActividad.putExtra("Datos",fact);
                startActivity(nuevaActividad);
                break;

            case R.id.Cuenta:
                nuevaActividad = new Intent(this, vista_cuenta.class);
                nuevaActividad.putExtra("Actividad", "Cuenta");
                startActivity(nuevaActividad);
                break;

            case R.id.reporte:
                nuevaActividad = new Intent(this, vista_reporte.class);
                nuevaActividad.putExtra("Actividad", "reporte");
                startActivity(nuevaActividad);
                break;

            case R.id.ver_todos_los_productos:
                nuevaActividad = new Intent(this,vista_ver_productos.class);
                startActivity(nuevaActividad);
                break;

            default:
                break;
        }
    }
}
