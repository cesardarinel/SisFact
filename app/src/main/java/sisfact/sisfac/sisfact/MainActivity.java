package sisfact.sisfac.sisfact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.activeandroid.ActiveAndroid;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button boton;
    private EditText usuario;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent menuprin = new Intent(this, vista_factura_lineas.class);
        startActivity(menuprin);
        this.finish();
        setContentView(R.layout.activity_main);
        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(this);
        usuario = (EditText) findViewById(R.id.usuario);
        contrasena = (EditText) findViewById(R.id.contrasena);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.boton) {
            Intent menuprin = new Intent(this, vista_factura_lineas.class);
            startActivity(menuprin);
            this.finish();
            if (usuario.getText().equals("a") && contrasena.getText().equals("a")) {

            }

        }
    }
}
