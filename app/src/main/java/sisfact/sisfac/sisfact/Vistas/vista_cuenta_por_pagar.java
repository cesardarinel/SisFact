package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
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
import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;

public class vista_cuenta_por_pagar extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener {

    private Bundle parametros;
    private CuentasPorPagar cuentaCargada;
    private List<CuentaPorPagarPagos> cuentaCargadaPagos;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obteniendo parametros pasados a la vista
        parametros = getIntent().getExtras();
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

        if(parametros != null && parametros.getString("id") != null) {
            try {

                cuentaCargada = new Select().from(CuentasPorPagar.class).where("id = ? ", parametros.getString("id")).executeSingle();
                cuentaCargadaPagos = new Select().from(CuentaPorPagarPagos.class).where("cuenta_por_pagar = ? ", cuentaCargada.getId()).execute();
                numeroContacto.setText(cuentaCargada.getContacto().getTelefono());
                numeroContacto.setEnabled(false);
                monto.setText(cuentaCargada.getMonto().toString());
                monto.setEnabled(false);
                descripcion.setText(cuentaCargada.getDescripcion());
                fechaCreacion.setText(dateFormatter.format(cuentaCargada.getFechaCreada()));
                fechaCreacion.setEnabled(false);
                fechaCreacionDialog =  createDatePickerDialog(fechaCreacion,cuentaCargada.getFechaCreada().getYear(),cuentaCargada.getFechaCreada().getMonth(),cuentaCargada.getFechaCreada().getDay());
                adeudadoVal = (cuentaCargada.getMonto());
                modo = "detalles";


            } catch (Exception e) {
//                e.printStackTrace();
//                finish();
//                return;
            }
        } else {
            fechaCreacion.setText(dateFormatter.format(new Date()));
            fechaCreacionDialog = createDatePickerDialog(fechaCreacion);
            modo = "edicion";
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

        parametroFila = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametroFila.weight = 1f;

        if (cuentaCargadaPagos != null){
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
        if(cuentaCargada != null) {
            for (CuentaPorPagarPagos p : cuentaCargadaPagos) {
                pagadoVal = pagadoVal.add(p.getMonto());
            }
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
        numeroContacto.setText(cuentaCargada.getContacto().getTelefono().toString());
        monto.setText(cuentaCargada.getMonto().toString());
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
                if (cuentaCargada == null) {
                    finish();
                    return;
                }
                setValoresComponentes(cuentaCargada);
                modo = "detalles";
                cambiarEstadoComponentes();
                break;
            case R.id.plantilla_cuenta_monto_btn_agregar:
                crearLinea();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuCuenta = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_generico_editar, menu);
        menuCuenta.findItem(R.id.nuevo).setVisible(false);

        if (cuentaCargada == null) {
            menuCuenta.findItem(R.id.editar).setVisible(false);
            menuCuenta.findItem(R.id.eliminar).setVisible(false);
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
                String query = "DELETE FROM cunetas_por_cobrar_pago WHERE cuenta_por_pagar = " + cuentaCargada.getId();
                ActiveAndroid.getDatabase().execSQL(query);
                cuentaCargada.delete();
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
            cuentaCargada = cuenta;
            setValoresComponentes(cuenta);
            cambiarEstadoComponentes();
        }

    }

    private void crearLinea() {

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
            final CuentaPorPagarPagos cuentaPorPagarPagos = new Select().from(CuentaPorPagarPagos.class).where("id = ?", getIdPago()).executeSingle();

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

            alert.setMessage("Esta Seguro que desea borrar");
            alert.setTitle("Esta accion es irreversible");


            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    cuentaPorPagarPagos.delete();
                    table.removeView(tableRow);
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
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            alert.setMessage("Monto a agregar");
            alert.setTitle("Monto");

            alert.setView(edittext);

            alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Float monto = Float.valueOf(edittext.getText().toString());

                    CuentaPorPagarPagos cuentasPorPagarPagos = new CuentaPorPagarPagos();
                    cuentasPorPagarPagos.setFechaPago(new Date());
                    cuentasPorPagarPagos.setCuentasPorPagar(cuentaCargada);
                    cuentasPorPagarPagos.setMonto(new BigDecimal(monto));
                    cuentasPorPagarPagos.save();
                    System.out.println("id del pago indivdual; " + cuentasPorPagarPagos.getId() + "pago " + cuentaCargada.getId());
                    agregarFilaATabla(
                            edittext.getText().toString(),
                            dateFormatter.format(cuentasPorPagarPagos.getFechaPago()),
                            cuentasPorPagarPagos.getId()
                    );
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
            edittext.setText(cuentaPorPagarPagos.getMonto().toString());
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            alert.setMessage("Monto a Cambiar");
            alert.setTitle("Monto");

            alert.setView(edittext);

            alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                Float monto = Float.valueOf(edittext.getText().toString());
                cuentaPorPagarPagos.setMonto(new BigDecimal(monto));
                cuentaPorPagarPagos.save();
                //precio
                ((TextView)tableRow.getChildAt(0)).setText(cuentaPorPagarPagos.getMonto().toString());
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
