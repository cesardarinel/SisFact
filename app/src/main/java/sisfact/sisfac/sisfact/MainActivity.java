package sisfact.sisfac.sisfact;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import entidades.Interface.GenericListView;
import entidades.Marcas;
import sisfact.sisfac.sisfact.ver.Marca;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button boton;
    private EditText usuario;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(this);
        usuario = (EditText) findViewById(R.id.usuario);
        contrasena = (EditText) findViewById(R.id.contrasena);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.boton) {
            if (usuario.getText().equals("a") && contrasena.getText().equals("a")) {
                Intent menuprin = new Intent(this, MenuPrincipal.class);
                startActivity(menuprin);
                this.finish();
            }

            Intent intent =  new Intent(this,sisfact.sisfac.sisfact.guardar.Contacto.class);
//            ArrayList<Marcas> listaMarca =  new Select().from(Marcas.class).execute();
//            intent.putParcelableArrayListExtra("listaDeObjetos", listaMarca);
            startActivity(intent);

        }
    }
}
