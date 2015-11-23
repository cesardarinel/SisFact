package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import sisfact.sisfac.sisfact.R;

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
        //cuentas por pagar
         if(R.id.cpp==v.getId()){
             nuevaActividad = new Intent(this, vista_modulo_generic.class);
             nuevaActividad.putExtra("Actividad", "cpp");
             startActivity(nuevaActividad);

         }
        //cuentas por cobrar
        if(R.id.cpc == v.getId()) {
            nuevaActividad = new Intent(this, vista_modulo_generic.class);
            nuevaActividad.putExtra("Actividad", "cpc");
            startActivity(nuevaActividad);
        }
    }
}
