package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import sisfact.sisfac.sisfact.R;

public class vista_factura extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura);
    }

    @Override
    public void onClick(View v){
        if (v.getId()==R.id.button_agregar_lineas) {

              Intent  nuevaActividad = new Intent(this, vista_factura_lineas.class);
                startActivity(nuevaActividad);
        }

    }
}
