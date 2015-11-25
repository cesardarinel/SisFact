package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import entidades.Contactos;
import sisfact.sisfac.sisfact.R;


public class Contacto extends AppCompatActivity implements View.OnClickListener{

    private EditText nombreContacto;
    private EditText apellidoContacto;
    private EditText telefonoContacto;
    private EditText celularContacto;
    private EditText correoContacto;
    private EditText direccionContacto;
    private CheckBox esSuplidorContacto;
    private CheckBox esClienteContacto ;
    private Button botonGuardar;
    private Button botonCancelar;
    protected LinearLayout mainLayout;
    private entidades.Contactos contactoCargado;
    private Bundle parametros;
    private Menu menuContactos;
    private String modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_contactos);

        //Obteniendo parametros pasados a la vista
        parametros = getIntent().getExtras();

        //Asociando variables a componentes de vista
        mainLayout = (LinearLayout) findViewById(R.id.plantilla_contacto_layout);
        nombreContacto = (EditText) findViewById(R.id.guardar_contacto_nombre);
        apellidoContacto = (EditText) findViewById(R.id.guardar_contacto_apellido);
        telefonoContacto = (EditText) findViewById(R.id.guardar_contacto_telefono);
        celularContacto = (EditText) findViewById(R.id.guardar_contacto_celular);
        correoContacto = (EditText) findViewById(R.id.guardar_contacto_correo);
        direccionContacto = (EditText) findViewById(R.id.guardar_contacto_direccion);
        esSuplidorContacto = (CheckBox) findViewById(R.id.guardar_contacto_chkbx_es_suplidor);
        esClienteContacto = (CheckBox) findViewById(R.id.guardar_contacto_chkbx_es_cliente);
        botonGuardar = (Button) findViewById(R.id.guardar_contacto_btn_guardar);
        botonCancelar =  (Button) findViewById(R.id.guardar_contacto_btn_cancelar);

        //Seteando Listener a Componentes
        botonGuardar.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);
        telefonoContacto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        celularContacto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //Cargando Contacto si existe en la base de datos
        if(parametros.getString("id") != null) {
            try {
                contactoCargado = new Select().from(Contactos.class).where("id = ? ", parametros.getString("id")).executeSingle();
                setValoresComponentes(contactoCargado);
                modo = "detalles";
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        } else {
            modo = "edicion";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuContactos = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_generico_editar, menu);
        menuContactos.findItem(R.id.nuevo).setVisible(false);

        if (contactoCargado == null) {
            menuContactos.findItem(R.id.editar).setVisible(false);
            menuContactos.findItem(R.id.eliminar).setVisible(false);
        }
        cambiarEstadoComponentes();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                menuContactos.findItem(R.id.editar).setVisible(false);
                modo = "edicion";
                cambiarEstadoComponentes();
                return true;
            case R.id.eliminar:
                borrarContacto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setValoresComponentes(Contactos contacto) {
        nombreContacto.setText(contacto.getNombre());
        apellidoContacto.setText(contacto.getApellido());
        telefonoContacto.setText(contacto.getTelefono());
        celularContacto.setText(contacto.getCelular());
        correoContacto.setText(contacto.getCorreo());
        direccionContacto.setText(contacto.getDireccion());
        esSuplidorContacto.setChecked(contacto.isEsSuplidor());
        esClienteContacto.setChecked(contacto.isEsCliente());
    }

    private entidades.Contactos getValoresComponentes(){
        entidades.Contactos contacto = contactoCargado != null ? contactoCargado : new Contactos();
        contacto.setNombre(nombreContacto.getText().toString().trim());
        contacto.setApellido(apellidoContacto.getText().toString().trim());
        contacto.setTelefono(telefonoContacto.getText().toString().trim());
        contacto.setCelular(celularContacto.getText().toString().trim());
        contacto.setCorreo(correoContacto.getText().toString().trim());
        contacto.setDireccion(direccionContacto.getText().toString().trim());
        contacto.setEsSuplidor(esSuplidorContacto.isChecked());
        contacto.setEsCliente(esClienteContacto.isChecked());
        return contacto;
    }

    private boolean validarValoresComponentes() {
        entidades.Contactos contactoTemporal = getValoresComponentes();

        boolean camposRequeridosLlenos = true;
        //Validando que campos requeridos se encuentren llenos
        if(stringIsEmpty(contactoTemporal.getNombre())) {
            nombreContacto.setError("Campo no puede estar vacio");
            camposRequeridosLlenos = false;
        } else  {
            nombreContacto.setError(null);
        }

        if(stringIsEmpty(contactoTemporal.getApellido())) {
            apellidoContacto.setError("Campo no puede estar vacio");
            camposRequeridosLlenos = false;
        } else {
            apellidoContacto.setError(null);
        }

        if(stringIsEmpty(contactoTemporal.getTelefono()) && stringIsEmpty(contactoTemporal.getCelular())) {
            telefonoContacto.setError("Ambos Telefono y Celular no pueden estar vacio");
            celularContacto.setError("Ambos Telefono y Celular no pueden estar vacio");
            camposRequeridosLlenos = false;
        } else {
            telefonoContacto.setError(null);
            celularContacto.setError(null);
        }

        if (!esSuplidorContacto.isChecked() && !esClienteContacto.isChecked()) {
            esSuplidorContacto.setError("Campo no puede estar vacio");
            esClienteContacto.setError("Campo no puede estar vacio");
            camposRequeridosLlenos = false;
        } else {
            esSuplidorContacto.setError(null);
            esClienteContacto.setError(null);
        }

        //Validando formatos de Campos
        Boolean esFormatoCorreoValido = stringIsEmpty(contactoTemporal.getCorreo()) || Patterns.EMAIL_ADDRESS.matcher(contactoTemporal.getCorreo()).matches();
        if (!esFormatoCorreoValido){
            correoContacto.setError("El correo ingresado no es valido");
        } else {
            correoContacto.setError(null);
        }

        String telefonoRegex = "\\(\\d{3}\\)\\ \\d{3}\\-\\d{4}";
        Boolean esTelefonoValido = stringIsEmpty(contactoTemporal.getTelefono()) || Pattern.matches(telefonoRegex, contactoTemporal.getTelefono());

        if (!esTelefonoValido){
            telefonoContacto.setError("El telefono ingresado no es valido");
        } else if(!stringIsEmpty(contactoTemporal.getTelefono())) {
            telefonoContacto.setError(null);
        }

        Boolean esCelularValido =  stringIsEmpty(contactoTemporal.getCelular()) || Pattern.matches(telefonoRegex,contactoTemporal.getCelular());
        if (!esCelularValido){
            celularContacto.setError("El telefono ingresado no es valido");
        } else if(!stringIsEmpty(contactoTemporal.getCelular())) {
            celularContacto.setError(null);
        }

        return camposRequeridosLlenos && esFormatoCorreoValido && esTelefonoValido && esCelularValido;
    }

    private void setComponenteModoLectura(View v, boolean flag){
        v.setEnabled(!flag);
    }

    private void cambiarEstadoComponentes(){

        switch (modo) {
            case "detalles":
                menuContactos.findItem(R.id.editar).setVisible(true);
                setComponenteModoLectura(nombreContacto,true);
                setComponenteModoLectura(apellidoContacto,true);
                setComponenteModoLectura(telefonoContacto,true);
                setComponenteModoLectura(celularContacto,true);
                setComponenteModoLectura(correoContacto,true);
                setComponenteModoLectura(direccionContacto,true);
                setComponenteModoLectura(esClienteContacto,true);
                setComponenteModoLectura(esSuplidorContacto,true);
                botonGuardar.setVisibility(View.GONE);
                botonCancelar.setVisibility(View.GONE);
                break;
            case "edicion":
                menuContactos.findItem(R.id.editar).setVisible(false);
                setComponenteModoLectura(nombreContacto,false);
                setComponenteModoLectura(apellidoContacto,false);
                setComponenteModoLectura(telefonoContacto,false);
                setComponenteModoLectura(celularContacto,false);
                setComponenteModoLectura(correoContacto,false);
                setComponenteModoLectura(direccionContacto,false);
                setComponenteModoLectura(esClienteContacto,false);
                setComponenteModoLectura(esSuplidorContacto,false);
                botonGuardar.setVisibility(View.VISIBLE);
                botonCancelar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean stringIsEmpty(String s) {
        return  s == null || s.isEmpty();
    }

    private void guardarContacto() {
        boolean componentesValidos = validarValoresComponentes();
        if (componentesValidos) {
            entidades.Contactos contactoTemporal = getValoresComponentes();
            if (contactoCargado == null) {
                menuContactos.findItem(R.id.editar).setVisible(true);
                menuContactos.findItem(R.id.eliminar).setVisible(true);
            }
            contactoTemporal.save();
            contactoCargado = contactoTemporal;
            modo = "detalles";
            cambiarEstadoComponentes();
        }
    }

    private void borrarContacto() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.create();

        dialog.setTitle("Eliminar Contacto");

        dialog.setMessage("Esta seguro que desea eliminar este Contacto?");

        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactoCargado.delete();
                finish();
            }
        });

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.guardar_contacto_btn_guardar:
                guardarContacto();
                break;
            case R.id.guardar_contacto_btn_cancelar:
                if (contactoCargado == null) {
                    finish();
                } else {
                    setValoresComponentes(contactoCargado);
                    modo = "detalles";
                    cambiarEstadoComponentes();
                }
                break;
            default:
                break;
        }
    }

}