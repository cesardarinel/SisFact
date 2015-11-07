package sisfact.sisfac.sisfact.guardar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import entidades.Contactos;
import sisfact.sisfac.sisfact.R;


public class Contacto extends AppCompatActivity implements View.OnClickListener{

    private EditText  nombreContacto;
    private EditText  apellidoContacto;
    private EditText  telefonoContacto;
    private EditText  celularContacto;
    private EditText  correoContacto;
    private EditText  direccionContacto;
    private CheckBox  esSuplidorContacto;
    private CheckBox  esClienteContacto ;
    protected Button botonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_contactos);

        nombreContacto = (EditText) findViewById(R.id.guardar_contacto_nombre);
        apellidoContacto = (EditText) findViewById(R.id.guardar_contacto_apellido);
        telefonoContacto = (EditText) findViewById(R.id.guardar_contacto_telefono);
        celularContacto = (EditText) findViewById(R.id.guardar_contacto_celular);
        correoContacto = (EditText) findViewById(R.id.guardar_contacto_correo);
        direccionContacto = (EditText) findViewById(R.id.guardar_contacto_direccion);

        esSuplidorContacto = (CheckBox) findViewById(R.id.guardar_contacto_chkbx_es_suplidor);
        esClienteContacto = (CheckBox) findViewById(R.id.guardar_contacto_chkbx_es_cliente);

        botonGuardar = (Button) findViewById(R.id.guardar_contacto_btn_guardar);
        botonGuardar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Contactos contacto = new Contactos();

        contacto.setNombre(nombreContacto.getText().toString());
        contacto.setApellido(apellidoContacto.getText().toString());
        contacto.setTelefono(telefonoContacto.getText().toString());
        contacto.setCelular(celularContacto.getText().toString());
        contacto.setCorreo(correoContacto.getText().toString());
        contacto.setDireccion(direccionContacto.getText().toString());

        contacto.setEsCliente(esClienteContacto.isChecked());
        contacto.setEsSuplidor(esSuplidorContacto.isChecked());

        try {
            contacto.save();
            Toast.makeText(this, "Guardado con Exito", Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (Exception e){
            //notificar el error
        }
        //posibement volver al menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contacto_suplidor, menu);
        return true;
    }
}
