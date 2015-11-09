package sisfact.sisfac.sisfact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import sisfact.sisfac.sisfact.Vistas.Productos;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //botones del menu
    ImageButton  factura,producto,contacto,cpp,reporte;
    Intent nuevaActividad;

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
                nuevaActividad = new Intent(this,Productos.class);
                startActivity(nuevaActividad);
                break;

            case R.id.contacto:
                nuevaActividad = new Intent(this, MenuPrincipal.class);
                startActivity(nuevaActividad);
                break;

            case R.id.cpp:
                nuevaActividad = new Intent(this, MenuPrincipal.class);
                startActivity(nuevaActividad);
                break;

            case R.id.reporte:
                nuevaActividad = new Intent(this, MenuPrincipal.class);
                startActivity(nuevaActividad);

            default:
                break;
        }
    }
}
