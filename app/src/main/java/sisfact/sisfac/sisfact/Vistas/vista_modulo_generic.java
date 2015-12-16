package sisfact.sisfac.sisfact.Vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.Action;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class vista_modulo_generic extends AppCompatActivity implements AdapterView.OnItemClickListener ,AdapterView.OnKeyListener, View.OnClickListener, Action, CompoundButton.OnCheckedChangeListener {

    protected String titulo;
    protected ListView listado;
    protected FactoryAdaptadorGenerico factoryAdaptadorGenerico;
    protected Spinner spinner;
    protected EditText textoBuscar;
    protected EditText desdeDate;
    protected EditText hastaDate;
    protected final int resultadoDeAgregar =1;
    protected DatePickerDialog desdeFechaDialog;
    protected DatePickerDialog hastaFechaDialog;
    protected ListaAdaptador listaAdaptador;
    protected Calendar newCalendar;
    protected SimpleDateFormat dateFormatter;
    protected Date fechaInicio;
    protected Date fechaFin;
    protected Boolean habilitarFecha = false;
    protected CheckBox cuentasPagadas;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_modulo_generic);

        Bundle tipoVista = getIntent().getExtras();
        factoryAdaptadorGenerico = (FactoryAdaptadorGenerico) tipoVista.getSerializable("Datos");
        if (factoryAdaptadorGenerico == null){
            Toast.makeText(this,"Los Datos Suministrados son invalidos",Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));
        titulo = tipoVista.getString("Actividad");
        setTitle(factoryAdaptadorGenerico.getTitulo());

        listado = (ListView) findViewById(R.id.listView);
        textoBuscar = (EditText) findViewById(R.id.vista_generica_txt);
        textoBuscar.setOnKeyListener(this);
        textoBuscar.requestFocus();

        desdeDate = (EditText) findViewById(R.id.vista_generica_desdeDate);
        desdeDate.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        fechaInicio = calendar.getTime();
        desdeDate.setText(dateFormatter.format(fechaInicio));
        desdeDate.setOnClickListener(this);

        fechaFin = new Date();
        hastaDate = (EditText) findViewById(R.id.vista_generica_hastaDate);
        hastaDate.setInputType(InputType.TYPE_NULL);
        hastaDate.setText(dateFormatter.format(new Date()));
        hastaDate.setOnClickListener(this);
        desdeFechaDialog = createDatePickerDialog(desdeDate,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        hastaFechaDialog = createDatePickerDialog(hastaDate);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> stringArrayAdapter = factoryAdaptadorGenerico.getCamposBuscablesAdator(this);
        spinner.setAdapter(stringArrayAdapter);
        listado.setOnItemClickListener(this);

        cuentasPagadas = (CheckBox)findViewById(R.id.mostrar_cuentas_sinpagar);
        cuentasPagadas.setChecked(false);
        cuentasPagadas.setOnCheckedChangeListener(this);
        habilitarFecha = tipoVista.getBoolean("fecha");

        if (habilitarFecha){
            desdeDate.setVisibility(View.VISIBLE);
            hastaDate.setVisibility(View.VISIBLE);
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            findViewById(R.id.textView4).setVisibility(View.VISIBLE);
            cuentasPagadas.setVisibility(View.VISIBLE);
        }

        actualizarCampos();


    }

    private DatePickerDialog createDatePickerDialog(final EditText e, final int year, final int month, final int day){

        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e.setText(dateFormatter.format(newDate.getTime()));
                if (e.getId() == R.id.vista_generica_desdeDate){
                    fechaInicio = newDate.getTime();
                }
                else if (e.getId() == R.id.vista_generica_hastaDate){
                    fechaFin = newDate.getTime();
                }
                actualizarCampos();
            }
        },year, month, day);
    }

    protected  void actualizarCampos(){
        if (habilitarFecha){
            listaAdaptador = factoryAdaptadorGenerico.getCamposaFiltrar(
                this,
                (String) spinner.getSelectedItem(),
                textoBuscar.getText().toString(),
                factoryAdaptadorGenerico.getObjetosFiltradoPor(this)
            );
        }
        else {
            listaAdaptador = factoryAdaptadorGenerico.getCamposaFiltrar(this, (String) spinner.getSelectedItem(), textoBuscar.getText().toString());
        }
        if (listaAdaptador !=  null)  listado.setAdapter(listaAdaptador);
    }
    private DatePickerDialog createDatePickerDialog(final EditText e){

        return createDatePickerDialog(e,newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.vista_generica_desdeDate:
                desdeFechaDialog.show();
                break;
            case R.id.vista_generica_hastaDate:
                hastaFechaDialog.show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_generico_editar, menu);
        menu.findItem(R.id.editar).setVisible(false);
        menu.findItem(R.id.eliminar).setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nuevo:
                Intent intent = factoryAdaptadorGenerico.getIntentClase(this);
                startActivityForResult(intent, resultadoDeAgregar);
                return true;
            case R.id.editar:
                System.out.println("Se presionó Editar");
                return true;
            case R.id.eliminar:
                System.out.println("Se presionó Eliminar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
        Intent intent = factoryAdaptadorGenerico.getIntentClase(this);
        String id = factoryAdaptadorGenerico.getObjetosListado().get(position).getId();
        intent.putExtra("id", id);
        startActivityForResult(intent,resultadoDeAgregar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == resultadoDeAgregar) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Long itemLista = (Long)data.getSerializableExtra("id");
                if (itemLista != null){
                    factoryAdaptadorGenerico.agregar(itemLista);
                }
                factoryAdaptadorGenerico.update();
                listaAdaptador = factoryAdaptadorGenerico.getCamposaFiltrar(this,(String)spinner.getSelectedItem(),"");
                listado.setAdapter(listaAdaptador);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        actualizarCampos();
        return false;
    }

    @Override
    public Boolean CompraraObjeto(Object obj) {
        Date refDate =  new Date();
        Boolean esPagado = false;
        if (getTitle().equals("Cuenta Por Cobrar")){
            CuentasPorCobrar cuentasPorCobrar= (CuentasPorCobrar) obj;
            refDate = cuentasPorCobrar.getFechaCreada();
            if (cuentasPagadas.isChecked()){
                esPagado = cuentasPorCobrar.EstaPagado();
            }
        }
        else if (getTitle().equals("Cuenta Por Pagar")){
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar)obj;
            refDate = cuentasPorPagar.getFechaCreada();
            if (cuentasPagadas.isChecked()){
                esPagado = cuentasPorPagar.EstaPagado();
            }
        }
        System.out.println("el check box es " +cuentasPagadas.isChecked() + "  es pagada " + esPagado);
        return (
                (refDate.after(fechaInicio) && refDate.before(fechaFin) ||
                        refDate.equals(fechaFin) || refDate.equals(fechaFin)
                )
                ) && !esPagado;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        actualizarCampos();
    }
}
