package sisfact.sisfac.sisfact.Vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entidades.Contactos;
import entidades.CuentaPorPagarPagos;
import entidades.CuentasPorCobrar;
import entidades.CuentasPorCobrarPago;
import entidades.CuentasPorPagar;
import entidades.Facturas;
import sisfact.sisfac.sisfact.R;

public class vista_cuenta_por_pagar extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener {

    private Bundle parametros;
    private CuentasPorPagar cuentaCargadaPago;
    protected CuentasPorCobrar cuentaCargadaCobrar;
    private List<CuentaPorPagarPagos> cuentaCargadaPagos;
    protected List<CuentasPorCobrarPago> cuentasPorCobrarPagos;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView numeroContacto;
    private TableLayout table;
    private EditText monto;
    private EditText descripcion;
    private EditText fechaCreacion;
    private TextView pagadotxt;
    private TextView pagado;
    private TextView adeudadotxt;
    private TextView adeudado;
    private Button agregarPagoBtn;
    private Button guardarCuentaBtn;
    private Button cancelarBtn;
    private BigDecimal pagadoVal;
    private BigDecimal adeudadoVal;
    private ArrayAdapter<String> contactoArrayAdapter;
    private TableRow.LayoutParams parametroFila;
    private Menu menuCuenta;
    protected DatePickerDialog fechaCreacionDialog;

    private String modo;
    Calendar newCalendar;
    protected Boolean esCuentaPorCobrar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obteniendo parametros pasados a la vista
        parametros = getIntent().getExtras();
        if (parametros!= null)esCuentaPorCobrar = parametros.getBoolean("esCobros");
        //Cargando Contacto si existe en la base de datos
        setContentView(R.layout.vista_cuenta_generic);
        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));
        findViewById(R.id.cuenta_texto_factura).setVisibility(View.GONE);
        findViewById(R.id.cuenta_valor_factura).setVisibility(View.GONE);
        pagadoVal = BigDecimal.valueOf(0.0);
        adeudadoVal = BigDecimal.valueOf(0.0);

        numeroContacto = (AutoCompleteTextView) findViewById(R.id.cuenta_valor_contacto);
        monto = (EditText) findViewById(R.id.cuenta_valor_monto);
        descripcion = (EditText) findViewById(R.id.cuenta_valor_descripcion);
        fechaCreacion = (EditText) findViewById(R.id.plantilla_cuenta_valor_fecha_creacion);
        fechaCreacion.setInputType(InputType.TYPE_NULL);
        fechaCreacion.setOnClickListener(this);
        table = (TableLayout) findViewById(R.id.cuenta_tabla);
        agregarPagoBtn = (Button) findViewById(R.id.plantilla_cuenta_monto_btn_agregar);

        AgregarEvento agregarEvento = new AgregarEvento();
        agregarEvento.setContext(this);
        agregarPagoBtn.setOnClickListener(agregarEvento);
        pagadotxt = (TextView) findViewById(R.id.cuenta_texto_pagado);
        pagado = (TextView) findViewById(R.id.cuenta_texto_pagado_valor);
        adeudadotxt = (TextView) findViewById(R.id.cuenta_texto_adeudado);
        adeudado = (TextView) findViewById(R.id.cuenta_texto_adeudado_valor);
        guardarCuentaBtn = (Button) findViewById(R.id.cuenta_guardar_btn);
        cancelarBtn = (Button) findViewById(R.id.cuenta_cancelar_btn);
        cancelarBtn.setOnClickListener(this);
        guardarCuentaBtn.setOnClickListener(this);

        if(esCuentaPorCobrar)((TextView)findViewById(R.id.cuenta_texto_contacto)).setText("# de Factura");

        if(parametros != null && parametros.getString("id") != null) {
            try {

                if(esCuentaPorCobrar){
                    cuentaCargadaCobrar = new Select().from(CuentasPorCobrar.class).where("id = ? ", parametros.getString("id")).executeSingle();
                    cuentasPorCobrarPagos = new Select().from(CuentasPorCobrarPago.class).where("cuentas_por_cobrar = ? ", cuentaCargadaCobrar.getId()).execute();
                    monto.setText(cuentaCargadaCobrar.getMonto().toString());
                    descripcion.setText(cuentaCargadaCobrar.getDescripcion());
                    fechaCreacion.setText(dateFormatter.format(cuentaCargadaCobrar.getFechaCreada()));

                }
                else{
                    cuentaCargadaPago = new Select().from(CuentasPorPagar.class).where("id = ? ", parametros.getString("id")).executeSingle();
                    cuentaCargadaPagos = new Select().from(CuentaPorPagarPagos.class).where("cuenta_por_pagar = ? ", cuentaCargadaPago.getId()).execute();
                    monto.setText(cuentaCargadaPago.getMonto().toString());
                    numeroContacto.setText(cuentaCargadaPago.getContacto().getTelefono());
                    numeroContacto.setEnabled(false);
                    descripcion.setText(cuentaCargadaPago.getDescripcion());
                    fechaCreacion.setText(dateFormatter.format(cuentaCargadaPago.getFechaCreada()));
                }

                monto.setEnabled(false);
                fechaCreacion.setEnabled(false);
                fechaCreacionDialog =  createDatePickerDialog(fechaCreacion, cuentaCargadaPago.getFechaCreada().getYear(), cuentaCargadaPago.getFechaCreada().getMonth(), cuentaCargadaPago.getFechaCreada().getDay());
                adeudadoVal = (cuentaCargadaPago.getMonto());
                modo = "detalles";


            } catch (Exception e) {
//                e.printStackTrace();
//                finish();
//                return;
            }
        }
        else {
            fechaCreacion.setText(dateFormatter.format(new Date()));
            fechaCreacionDialog = createDatePickerDialog(fechaCreacion);
            modo = "edicion";
        }
        ArrayList<String> campoSugerencias =  new ArrayList<>();
        if(esCuentaPorCobrar) {
            List<Facturas> facturases =  new Select().from(Facturas.class).execute();
            for(Facturas item : facturases) campoSugerencias.add(item.getId().toString());

        }
        else{
            List<entidades.Contactos> contactosList =  new Select().from(entidades.Contactos.class).execute();
            for(entidades.Contactos con : contactosList) {
                campoSugerencias.add(con.getTelefono());
                campoSugerencias.add(con.getCelular());
            }
        }

        contactoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, campoSugerencias);
        numeroContacto.setAdapter(contactoArrayAdapter);
        numeroContacto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        monto.setOnFocusChangeListener(this);

        parametroFila = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametroFila.weight = 1f;
        if (esCuentaPorCobrar){
            if (cuentaCargadaCobrar != null){
                for(CuentasPorCobrarPago item :cuentasPorCobrarPagos){
                    agregarFilaATabla(
                            item.getMonto().toString(),
                            dateFormatter.format(item.getFechaPago()),
                            item.getId()
                    );
                }
            }
        }
        else if (cuentaCargadaPagos != null ){
            for (CuentaPorPagarPagos item :cuentaCargadaPagos){
                agregarFilaATabla(
                        item.getMonto().toString(),
                        dateFormatter.format(item.getFechaPago()),
                        item.getId()
                );
            }
        }
        ActualizarMonto();
        table.setVisibility(View.VISIBLE);

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
    protected void ActualizarMonto(){
        pagadoVal= BigDecimal.ZERO;
        if (esCuentaPorCobrar){
            if(cuentaCargadaCobrar != null) {
                cuentasPorCobrarPagos =  new Select().from(CuentasPorCobrarPago.class).where("cuentas_por_cobrar = ? ", cuentaCargadaCobrar.getId()).execute();
                for (CuentasPorCobrarPago p : cuentasPorCobrarPagos) {
                    pagadoVal = pagadoVal.add(p.getMonto());
                }
                adeudadoVal = cuentaCargadaCobrar.getMonto();
            }
        }
        else if(cuentaCargadaPago != null) {
            cuentaCargadaPagos = new Select().from(CuentaPorPagarPagos.class).where("cuenta_por_pagar = ? ", cuentaCargadaPago.getId()).execute();
            for (CuentaPorPagarPagos p : cuentaCargadaPagos) {
                pagadoVal = pagadoVal.add(p.getMonto());
            }
            adeudadoVal = cuentaCargadaPago.getMonto();
        }
        adeudadoVal = adeudadoVal.subtract(pagadoVal);
        adeudado.setText(adeudadoVal.toString());
        pagado.setText(pagadoVal.toString());
    }
    public void agregarFilaATabla(String monto,String sFecha,final Long id){
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

        EditarEvento editarEvento = new EditarEvento();
        editarEvento.setContext(this);
        editarEvento.setIdPago(id);
        editarEvento.setTableRow(tableRow);

        editar.setOnClickListener(editarEvento);

        ImageView borrar = new ImageView(this);
        borrar.setImageResource(R.drawable.ic_menu_delete);
        borrar.setLayoutParams(parametroFila);

        BorraEvento borraEvento =  new BorraEvento();
        borraEvento.setContext(this);
        borraEvento.setIdPago(id);
        borraEvento.setTableRow(tableRow);
        borrar.setOnClickListener(borraEvento);

        tableRow.addView(pago);
        tableRow.addView(fecha);
        tableRow.addView(editar);
        tableRow.addView(borrar);
        tableRow.setTag(id);
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

    private void setValoresComponentes(CuentasPorPagar cuentaPorPagar) {
        numeroContacto.setText(cuentaCargadaPago.getContacto().getTelefono().toString());
        monto.setText(cuentaCargadaPago.getMonto().toString());
        descripcion.setText(cuentaPorPagar.getDescripcion());
        fechaCreacion.setText(dateFormatter.format(cuentaPorPagar.getFechaCreada()));
    }
    private void setValoresComponentes(CuentasPorCobrar cuentaPorPagar) {
        numeroContacto.setText(cuentaCargadaPago.getContacto().getTelefono().toString());
        monto.setText(cuentaCargadaPago.getMonto().toString());
        descripcion.setText(cuentaPorPagar.getDescripcion());
        fechaCreacion.setText(dateFormatter.format(cuentaPorPagar.getFechaCreada()));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plantilla_cuenta_valor_fecha_creacion:
                fechaCreacionDialog.show();
                break;
            case R.id.cuenta_guardar_btn:
                guardarCuenta();
                finish();
            case R.id.cuenta_cancelar_btn:
                if (cuentaCargadaPago == null) {
                    finish();
                    return;
                }
                setValoresComponentes(cuentaCargadaPago);
                modo = "detalles";
                cambiarEstadoComponentes();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuCuenta = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cuentas, menu);
        menuCuenta.findItem(R.id.detalle_cliente).setVisible(false);
        menuCuenta.findItem(R.id.detalle_factura).setVisible(false);
        if (esCuentaPorCobrar){
            if (cuentaCargadaCobrar == null) {
                menuCuenta.findItem(R.id.editar).setVisible(false);
                menuCuenta.findItem(R.id.eliminar).setVisible(false);

            }
            else{
                menuCuenta.findItem(R.id.detalle_factura).setVisible(true);
            }

        }
        else {
            if (cuentaCargadaPago == null ) {
                menuCuenta.findItem(R.id.editar).setVisible(false);
                menuCuenta.findItem(R.id.eliminar).setVisible(false);
            }
            else{
                menuCuenta.findItem(R.id.detalle_cliente).setVisible(true);
            }
        }
        cambiarEstadoComponentes();
        return true;
    }

    private void borrarCuenta() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.create();

        dialog.setTitle("Eliminar Cuenta Por Pagar");

        dialog.setMessage("Esta seguro que desea eliminar esta Cuenta Por Pagar?");

        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM cunetas_por_cobrar_pago WHERE cuenta_por_pagar = " + cuentaCargadaPago.getId();
                ActiveAndroid.getDatabase().execSQL(query);
                cuentaCargadaPago.delete();
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

    private void guardarCuenta(){
        Long idIntent = null;
        Intent returnInent =  new Intent();
        if (esCuentaPorCobrar){
            boolean esValido = false;
            Facturas facturas = new Select()
                    .from(Facturas.class)
                    .where("id = ? ",numeroContacto.getText().toString()).executeSingle();

            esValido = (numeroContacto.getText().toString().trim() != "")
                    && (monto.getText().toString().trim() != "")
                    && (fechaCreacion.getText().toString().trim() != "")
                    && (facturas != null);

            if (esValido) {
                CuentasPorCobrar cuenta = new CuentasPorCobrar();
                cuenta.setFactura(facturas);
                cuenta.setMonto(BigDecimal.valueOf(Float.valueOf(monto.getText().toString())));
                cuenta.setDescripcion(descripcion.getText().toString());
                String[] dateParts = fechaCreacion.getText().toString().split("/");
                cuenta.setFechaCreada(java.sql.Date.valueOf(dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]));
                cuenta.save();
                modo = "detalles";
                idIntent = cuenta.getId();
                cuentaCargadaCobrar = cuenta;
                setValoresComponentes(cuenta);
                cambiarEstadoComponentes();
            }
        }
        else{
            boolean esValido = false;
            Contactos contacto = new Select()
                    .from(Contactos.class)
                    .where("telefono = ? OR celular = ?", numeroContacto.getText().toString(),
                            numeroContacto.getText().toString()).executeSingle();

            esValido = (numeroContacto.getText().toString().trim() != "")
                    && (monto.getText().toString().trim() != "")
                    && (fechaCreacion.getText().toString().trim() != "")
                    && (contacto != null);

            if (esValido) {
                CuentasPorPagar cuenta = new CuentasPorPagar();
                cuenta.setContacto(contacto);
                cuenta.setMonto(BigDecimal.valueOf(Float.valueOf(monto.getText().toString())));
                cuenta.setDescripcion(descripcion.getText().toString());
                String[] dateParts = fechaCreacion.getText().toString().split("/");
                cuenta.setFechaCreada(java.sql.Date.valueOf(dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]));
                cuenta.save();
                modo = "detalles";
                cuentaCargadaPago = cuenta;
                idIntent = cuenta.getId();
                setValoresComponentes(cuenta);
                cambiarEstadoComponentes();
            }
        }
        if(idIntent != null){
            returnInent.putExtra("id",idIntent);
            setResult(Activity.RESULT_OK,returnInent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                menuCuenta.findItem(R.id.editar).setVisible(false);
                modo = "edicion";
                cambiarEstadoComponentes();
                return true;
            case R.id.eliminar:
                borrarCuenta();
                return true;
            case R.id.detalle_cliente:
                Intent intent = new Intent(this,Contacto.class);
                intent.putExtra("id",cuentaCargadaPago.getContacto().getId().toString());
                startActivity(intent);
                return true;
            case R.id.detalle_factura:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cambiarEstadoComponentes(){

        switch (modo) {
            case "detalles":
                table.setVisibility(View.VISIBLE);
                pagado.setVisibility(View.VISIBLE);
                pagadotxt.setVisibility(View.VISIBLE);
                adeudado.setVisibility(View.VISIBLE);
                adeudadotxt.setVisibility(View.VISIBLE);
                agregarPagoBtn.setVisibility(View.VISIBLE);
                guardarCuentaBtn.setVisibility(View.GONE);
                cancelarBtn.setVisibility(View.GONE);
                numeroContacto.setEnabled(false);
                monto.setEnabled(false);
                descripcion.setEnabled(false);
                fechaCreacion.setEnabled(false);
                break;
            case "edicion":
                table.setVisibility(View.GONE);
                pagado.setVisibility(View.GONE);
                pagadotxt.setVisibility(View.GONE);
                adeudado.setVisibility(View.GONE);
                adeudadotxt.setVisibility(View.GONE);
                agregarPagoBtn.setVisibility(View.GONE);
                guardarCuentaBtn.setVisibility(View.VISIBLE);
                cancelarBtn.setVisibility(View.VISIBLE);
                numeroContacto.setEnabled(true);
                monto.setEnabled(true);
                descripcion.setEnabled(true);
                fechaCreacion.setEnabled(true);
                break;
        }
    }

    class BorraEvento implements View.OnClickListener{
        protected Long idPago;
        protected Context context;

        public TableRow getTableRow() {
            return tableRow;
        }

        public void setTableRow(TableRow tableRow) {
            this.tableRow = tableRow;
        }

        protected  TableRow tableRow;
        @Override
        public void onClick(View v) {


            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            alert.setMessage("Esta Seguro que desea borrar");
            alert.setTitle("Esta accion es irreversible");


            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(esCuentaPorCobrar) {
                        CuentasPorCobrarPago cuentasPorCobrarPago = new Select().from(CuentasPorCobrarPago.class).where("id = ?", getIdPago()).executeSingle();
                        cuentasPorCobrarPago.delete();
                    }
                    else {
                        CuentaPorPagarPagos cuentaPorPagarPagos = new Select().from(CuentaPorPagarPagos.class).where("id = ?", getIdPago()).executeSingle();
                        cuentaPorPagarPagos.delete();
                    }

                    table.removeView(tableRow);
                    ActualizarMonto();
                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alert.show();
        }

        public Long getIdPago() {
            return idPago;
        }

        public void setIdPago(Long idPago) {
            this.idPago = idPago;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
    class AgregarEvento implements View.OnClickListener{
        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        protected Context context;
        @Override
        public void onClick(View v) {
            final EditText edittext = new EditText(context);
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            edittext.setOnKeyListener(new LimtarDeudaEvento());
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            alert.setMessage("Monto a agregar");
            alert.setTitle("Monto");

            alert.setView(edittext);

            alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Float monto = Float.valueOf(edittext.getText().toString());
                    if (esCuentaPorCobrar) {
                        CuentasPorCobrarPago cuentasPorCobrarPago = new CuentasPorCobrarPago();
                        cuentasPorCobrarPago.setFechaPago(new Date());
                        cuentasPorCobrarPago.setMonto(new BigDecimal(monto));
                        cuentasPorCobrarPago.save();

                        cuentasPorCobrarPagos.add(cuentasPorCobrarPago);
                        agregarFilaATabla(
                                edittext.getText().toString(),
                                dateFormatter.format(cuentasPorCobrarPago.getFechaPago()),
                                cuentasPorCobrarPago.getId()
                        );
                    }
                    else {
                        CuentaPorPagarPagos cuentasPorPagarPagos = new CuentaPorPagarPagos();
                        cuentasPorPagarPagos.setFechaPago(new Date());
                        cuentasPorPagarPagos.setCuentasPorPagar(cuentaCargadaPago);
                        cuentasPorPagarPagos.setMonto(new BigDecimal(monto));
                        cuentasPorPagarPagos.save();

                        cuentaCargadaPagos.add(cuentasPorPagarPagos);
                        agregarFilaATabla(
                                edittext.getText().toString(),
                                dateFormatter.format(cuentasPorPagarPagos.getFechaPago()),
                                cuentasPorPagarPagos.getId()
                        );
                    }
                    ActualizarMonto();
                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alert.show();
        }
    }
    class LimtarDeudaEvento implements View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText editText = (EditText)v;
            try{
                BigDecimal bigDecimal = new BigDecimal(editText.getText().toString());
                if (bigDecimal.compareTo(adeudadoVal)  ==1 ){
                    editText.setText(adeudado.getText());
                }
            }
            catch(Exception e){}
            return false;
        }
    }
    class EditarEvento implements View.OnClickListener{

        public TableRow getTableRow() {
            return tableRow;
        }

        public void setTableRow(TableRow tableRow) {
            this.tableRow = tableRow;
        }

        TableRow tableRow;
        public Long getIdPago() {
            return idPago;
        }

        public void setIdPago(Long idPago) {
            this.idPago = idPago;
        }

        protected Long idPago;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        protected Context context;
        @Override
        public void onClick(View v) {
            final EditText edittext = new EditText(context);

            final CuentaPorPagarPagos cuentaPorPagarPagos = new Select().from(CuentaPorPagarPagos.class).where("id = ?",idPago).executeSingle();
            final CuentasPorCobrarPago cuentasPorCobrarPago =new Select().from(CuentasPorCobrarPago.class).where("id = ?",idPago).executeSingle();;

            edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            edittext.setOnKeyListener(new LimtarDeudaEvento());
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            if(esCuentaPorCobrar) edittext.setText(cuentasPorCobrarPago.getMonto().toString());
            else edittext.setText(cuentaPorPagarPagos.getMonto().toString());

            alert.setMessage("Monto a Cambiar");
            alert.setTitle("Monto");

            alert.setView(edittext);

            alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (adeudadoVal.compareTo(BigDecimal.ZERO) == 0) return;
                    Float monto = Float.valueOf(edittext.getText().toString());
                    if (esCuentaPorCobrar) {
                        cuentasPorCobrarPago.setMonto(new BigDecimal(monto));
                        cuentasPorCobrarPago.save();
                        ((TextView) tableRow.getChildAt(0)).setText(cuentasPorCobrarPago.getMonto().toString());
                    } else {
                        cuentaPorPagarPagos.setMonto(new BigDecimal(monto));
                        cuentaPorPagarPagos.save();
                        ((TextView) tableRow.getChildAt(0)).setText(cuentaPorPagarPagos.getMonto().toString());
                    }
                    //precio
                    ActualizarMonto();

                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alert.show();
        }
    }
}
