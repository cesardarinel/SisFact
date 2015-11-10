package sisfact.sisfac.sisfact.Vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import sisfact.sisfac.sisfact.R;

public class vista_modulo_generic extends AppCompatActivity {
    String titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_modulo_generic);
        //capturo lo enviado en la vista anterior
        Bundle tipoVista = getIntent().getExtras();
        titulo = tipoVista.getString("Actividad");

    }

}
