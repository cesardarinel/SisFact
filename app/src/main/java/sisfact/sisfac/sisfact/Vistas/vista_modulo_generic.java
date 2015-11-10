package sisfact.sisfac.sisfact.Vistas;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.Console;

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
    //agrege el menu aqui solo para ver como quedo y me puedan evaluar la tarea
/*                          inicio                                           */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_generico_editar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nuevo:
                System.out.println("Se presionó Nuevo");
                return true;
            case R.id.editar:
                System.out.println("Se presionó Editar");
                return true;
            case R.id.eliminar:
                System.out.println("Se presionó Eliminar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
/*              Fin                                                              */
}
