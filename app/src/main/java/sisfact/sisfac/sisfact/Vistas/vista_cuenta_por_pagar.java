package sisfact.sisfac.sisfact.Vistas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;

/**
 * Created by Stanley on 12/13/15.
 */
public class vista_cuenta_por_pagar extends AppCompatActivity implements View.OnFocusChangeListener {

    private Bundle parametros;
    private CuentasPorPagar cuentaCargada;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView numeroContacto;
    private TableLayout table;
    private List<TableRow> tableRows;
    private EditText monto;
    private EditText descripcion;
    private EditText fechaCreacion;
    private ArrayAdapter<String> contactoArrayAdapter;
    private Map<String,String> telefonosContactos;
    protected TableRow.LayoutParams parametroFila;
    protected TableLayout.LayoutParams parametroTablaRow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obteniendo parametros pasados a la vista
        parametros = getIntent().getExtras();
        //Cargando Contacto si existe en la base de datos
        setContentView(R.layout.vista_cuenta_generic);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));
        findViewById(R.id.cuenta_texto_factura).setVisibility(View.GONE);
        findViewById(R.id.cuenta_valor_factura).setVisibility(View.GONE);

        numeroContacto = (AutoCompleteTextView) findViewById(R.id.cuenta_valor_contacto);
        monto = (EditText) findViewById(R.id.cuenta_valor_monto);
        descripcion = (EditText) findViewById(R.id.cuenta_valor_descripcion);
        fechaCreacion = (EditText) findViewById(R.id.plantilla_cuenta_valor_fecha_creacion);
        telefonosContactos = new HashMap<>();
        if(parametros != null && parametros.getString("id") != null) {
            try {
                cuentaCargada = new Select().from(CuentasPorPagar.class).where("id = ? ", parametros.getString("id")).executeSingle();
                numeroContacto.setText(cuentaCargada.getContacto().getTelefono());
                descripcion.setText(cuentaCargada.getDescripcion());
                fechaCreacion.setText(dateFormatter.format(cuentaCargada.getFechaCreada()));
            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
        } else {
            fechaCreacion.setText(dateFormatter.format(new Date()));
        }

        List<entidades.Contactos> contactosList =  new Select().from(entidades.Contactos.class).execute();
        ArrayList<String> listaContacto =  new ArrayList<>();
        for(entidades.Contactos con : contactosList) {
            listaContacto.add(con.getTelefono());
            listaContacto.add(con.getCelular());
            telefonosContactos.put(con.getTelefono(), con.toString());
            telefonosContactos.put(con.getCelular(),con.toString());
        }
        contactoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaContacto);
        numeroContacto.setAdapter(contactoArrayAdapter);
        numeroContacto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        monto.setOnFocusChangeListener(this);

        table = (TableLayout) findViewById(R.id.cuenta_tabla);

        parametroFila = new TableRow .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametroFila.weight = 1f;
        parametroFila.gravity = Gravity.START;

        //ponle un mejor nombre
        parametroTablaRow = new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //crear elemento
        TextView textView1 = new TextView(this);
        textView1.setText("hola");
        //setear el layout params para las filas
        textView1.setLayoutParams(parametroFila);

        TextView textView2 = new TextView(this);
        textView2.setText("estas");
        textView2.setLayoutParams(parametroFila);

        //crea el contendor de la fila
        TableRow tableRow = new TableRow(this);
        //asignarle los parametros
        tableRow.setLayoutParams(parametroTablaRow);

        //agregar los elemtnos que estan por fila
        tableRow.addView(textView1);
        tableRow.addView(textView2);

        //agregar la fila a la tabla
        table.addView(tableRow);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Float pval;
        if (!hasFocus) {
            try {
                pval = Float.valueOf(monto.getText().toString());
            }
            catch (Exception e){ return ;}
            if(monto.getText().toString().matches("\\d+")){
                monto.setText(String.format("%.2f",pval));
            }
            else if(monto.getText().toString().matches("\\d+\\.\\d*")){
                monto.setText(String.format("%.2f",pval));
            }
        }
    }
}