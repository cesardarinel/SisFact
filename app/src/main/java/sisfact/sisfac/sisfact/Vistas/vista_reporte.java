package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.activeandroid.ActiveAndroid;


import java.util.HashMap;

import sisfact.sisfac.sisfact.R;

public class vista_reporte extends AppCompatActivity implements View.OnClickListener {

    final Context dialog = this;
    private Button boton_dialog_facturas;
    private Button boton_dialog_contactos;
    private Button boton_dialog_cuentaCobrar;
    private Button boton_dialog_cuentaPagar;
    private RadioButton boton_radio_pdf;
    private RadioButton boton_radio_excel;
    private LayoutInflater lo;
    private View vistaDialog;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_reporte);

        //Creando ventana
        boton_dialog_facturas  = (Button) findViewById(R.id.vista_reporte_btn_factura);
        boton_dialog_contactos = (Button) findViewById(R.id.vista_reporte_btn_contacto);
        boton_dialog_cuentaPagar = (Button) findViewById(R.id.vista_reporte_btn_cuentasPagar);
        boton_dialog_cuentaCobrar = (Button) findViewById(R.id.vista_reporte_btn_cuentasCobrar);

        boton_dialog_facturas.setOnClickListener(this);
        boton_dialog_contactos.setOnClickListener(this);
        boton_dialog_cuentaPagar.setOnClickListener(this);
        boton_dialog_cuentaCobrar.setOnClickListener(this);


    }

    /**
     *
     * @param tipo
     */
    public void crearVentana(final String tipo){

        lo = LayoutInflater.from(dialog);
        vistaDialog = lo.inflate(R.layout.vista_reportes_opciones_dialog, null);
        alerta = new AlertDialog.Builder(dialog);

        alerta.setView(vistaDialog);

        alerta.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface vistaOpcion, int id) {
                vistaOpcion.cancel();
            }
        });

        alerta.setPositiveButton("Generar Reporte", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface vistaOpcion,int id){
               ActiveAndroid.getDatabase();

           }

        });

        AlertDialog alertDialog = alerta.create();

        // show it
        alertDialog.show();

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_reporte, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.vista_reporte_btn_factura:
                crearVentana("factura");
                break;
            case R.id.vista_reporte_btn_contacto:
                crearVentana("contacto");
                break;
            case R.id.vista_reporte_btn_cuentasCobrar:
                crearVentana("cuentasCobrar");
                break;
            case R.id.vista_reporte_btn_cuentasPagar:
                crearVentana("cuentasPagar");
                break;

            default:
                break;
        }
    }
}
