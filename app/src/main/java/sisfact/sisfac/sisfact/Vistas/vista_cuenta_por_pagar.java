package sisfact.sisfac.sisfact.Vistas;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entidades.CuentaPorPagarPagos;
import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;

/**
 * Created by Stanley on 12/13/15.
 */
public class vista_cuenta_por_pagar extends AppCompatActivity implements View.OnFocusChangeListener {

    private Bundle parametros;
    private CuentasPorPagar cuentaCargada;
    private List<CuentaPorPagarPagos> cuentaCargadaPagos;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView numeroContacto;
    private TableLayout table;
    private EditText monto;
    private EditText descripcion;
    private EditText fechaCreacion;
    private ArrayAdapter<String> contactoArrayAdapter;
    private TableRow.LayoutParams parametroFila;
    private TableLayout.LayoutParams parametroTablaRow;
    Calendar newCalendar;

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
        table = (TableLayout) findViewById(R.id.cuenta_tabla);

        if(parametros != null && parametros.getString("id") != null) {
            try {
                cuentaCargada = new Select().from(CuentasPorPagar.class).where("id = ? ", parametros.getString("id")).executeSingle();
                cuentaCargadaPagos = new Select().from(CuentaPorPagarPagos.class).where("cuentasPorPagar.id = ? ", parametros.getString("id")).execute();
                numeroContacto.setText(cuentaCargada.getContacto().getTelefono());
                numeroContacto.setEnabled(false);
                monto.setText(cuentaCargada.getMonto().toString());
                monto.setEnabled(false);
                descripcion.setText(cuentaCargada.getDescripcion());
                fechaCreacion.setText(dateFormatter.format(cuentaCargada.getFechaCreada()));
                fechaCreacion.setEnabled(false);
                table.setVisibility(View.VISIBLE);
                findViewById(R.id.cuenta_tabla_fila_header).setVisibility(View.VISIBLE);
                findViewById(R.id.plantilla_cuenta_monto_btn_agregar).setVisibility(View.VISIBLE);
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
        }
        contactoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaContacto);
        numeroContacto.setAdapter(contactoArrayAdapter);
        numeroContacto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        monto.setOnFocusChangeListener(this);

        parametroFila = new TableRow .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametroFila.weight = 1f;
        parametroFila.gravity = Gravity.START;
        parametroTablaRow = new TableLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );


        if(cuentaCargada != null) {
            for (CuentaPorPagarPagos p : cuentaCargadaPagos) {
                agregarFilaATabla(p.getMono().toString(),
                        dateFormatter.format(p.getFechaPago()),
                        Integer.parseInt(p.getId().toString()));
            }
        }
        table.setVisibility(View.VISIBLE);
        agregarFilaATabla("600.00",dateFormatter.format(new Date()), 1);


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

    public void agregarFilaATabla(String monto,String sFecha,Integer Id){
        TextView pago = new TextView(this);
        final TableRow tableRow = new TableRow(this);

        pago.setText(monto);
        pago.setTextColor(Color.BLACK);
        //setear el layout params para las filas
        pago.setLayoutParams(parametroFila);

        TextView fecha = new TextView(this);
        fecha.setText(sFecha);
        fecha.setTextColor(Color.BLACK);
        fecha.setLayoutParams(parametroFila);

        ImageView editar = new ImageView(this);
        editar.setImageResource(R.drawable.ic_menu_edit);
        editar.setLayoutParams(parametroFila);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Agregar Dialog de Editar Linea
            }
        });

        ImageView borrar = new ImageView(this);
        editar.setImageResource(R.drawable.ic_menu_delete);
        borrar.setLayoutParams(parametroFila);

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Agregar Dialog de Borrar Linea
                //table.removeView(tableRow);
            }
        });

        tableRow.addView(pago);
        tableRow.addView(fecha);
        tableRow.addView(editar);
        tableRow.addView(borrar);
        tableRow.setId(Id);

        table.addView(tableRow);
    }

    private DatePickerDialog createDatePickerDialog(final EditText e, final int year, final int month, final int day){

        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e.setText(dateFormatter.format(newDate.getTime()));
            }

        },year, month, day);
    }

    private DatePickerDialog createDatePickerDialog(final EditText e){

        return createDatePickerDialog(e,newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}