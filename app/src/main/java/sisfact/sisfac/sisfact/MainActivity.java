package sisfact.sisfac.sisfact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button boton;
    private EditText usuario;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        if (true) {

                Intent menuprin = new Intent(this, MenuPrincipal.class);
                startActivity(menuprin);
                this.finish();


            Intent intent =  new Intent(this,sisfact.sisfac.sisfact.guardar.Contacto.class);
            startActivity(intent);

        }
    }
}
