package sisfact.sisfac.sisfact.guardar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import entidades.Secciones;
import sisfact.sisfac.sisfact.R;


public class Seccciones extends AppCompatActivity implements View.OnClickListener{

    protected EditText nombreSeccion;
    protected Button botonGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_secciones);
        nombreSeccion = (EditText) findViewById(R.id.guardar_seccion_seccion);
        botonGuardar = (Button) findViewById(R.id.guardar_seccion_btn_guardars);
        botonGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Secciones  secciones = new Secciones();
        secciones.setSeccion(nombreSeccion.getText().toString());
        try {
            secciones.save();
            Toast.makeText(this, "Guardado con Exito", Toast.LENGTH_SHORT).show();
            finish();

        }
        catch (Exception e){
            //// TODO: 11/4/2015
        }
        //posibement volver al menu
    }
}
