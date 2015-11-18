package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entidades.Contactos;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.AdaptadorContacto;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //botones del menu
    ImageButton  factura,producto,contacto,cpp,reporte;
    Intent nuevaActividad;
    Gson serializeJson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicialisamos variables
        factura= (ImageButton )findViewById(R.id.factura);
        producto= (ImageButton )findViewById(R.id.productos);
        contacto= (ImageButton )findViewById(R.id.contacto);

        cpp= (ImageButton )findViewById(R.id.cpp);
        reporte= (ImageButton )findViewById(R.id.reporte);
        //----------------setOnClickListener--------------------
        factura.setOnClickListener(this);
        producto.setOnClickListener(this);
        contacto.setOnClickListener(this);

        cpp.setOnClickListener(this);
        reporte.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.factura:
                nuevaActividad = new Intent(this, vista_factura.class);
                startActivity(nuevaActividad);
                break;

            case R.id.productos:
                nuevaActividad = new Intent(this, Productos.class);
                startActivity(nuevaActividad);
                break;

            case R.id.contacto:
                nuevaActividad = new Intent(this, vista_modulo_generic.class);
                nuevaActividad.putExtra("Actividad", "Contactos");
                FactoryAdaptadorGenerico fact = FactoryAdaptadorGenerico.getAdaptador(FactoryAdaptadorGenerico.Adaptador.Contacto);
                List<Contactos> contactosArrayList = new Select().from(Contactos.class).execute();
                for (Contactos con : contactosArrayList) con.setInternalId(con.getId());
                fact.setObjectosAFiltrar(contactosArrayList);
                nuevaActividad.putExtra("Datos",fact);
                startActivity(nuevaActividad);
                break;

            case R.id.cpp:
                nuevaActividad = new Intent(this, vista_modulo_generic.class);
                nuevaActividad.putExtra("Actividad", "cpp");
                startActivity(nuevaActividad);
                break;

            case R.id.reporte:

                nuevaActividad = new Intent(this, vista_modulo_generic.class);
                nuevaActividad.putExtra("Actividad", "reporte");
                startActivity(nuevaActividad);

            default:
                break;
        }
    }
}
