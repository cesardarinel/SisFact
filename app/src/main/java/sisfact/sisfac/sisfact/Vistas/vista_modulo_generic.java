package sisfact.sisfac.sisfact.Vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class vista_modulo_generic extends AppCompatActivity implements AdapterView.OnItemClickListener ,AdapterView.OnKeyListener, View.OnClickListener {

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

    protected SimpleDateFormat dateFormatter;
    ListaAdaptador listaAdaptador;


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
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        titulo = tipoVista.getString("Actividad");
        setTitle(factoryAdaptadorGenerico.getTitulo());

        listado = (ListView) findViewById(R.id.listView);
        textoBuscar = (EditText) findViewById(R.id.vista_generica_txt);
        textoBuscar.setOnKeyListener(this);
        textoBuscar.requestFocus();

        desdeDate = (EditText) findViewById(R.id.vista_generica_desdeDate);
        desdeDate.setInputType(InputType.TYPE_NULL);
        desdeDate.setOnClickListener(this);

        hastaDate = (EditText) findViewById(R.id.vista_generica_desdeDate);
        hastaDate.setInputType(InputType.TYPE_NULL);
        hastaDate.setOnClickListener(this);

        hastaFechaDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                hastaDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> stringArrayAdapter = factoryAdaptadorGenerico.getCamposBuscablesAdator(this);
        spinner.setAdapter(stringArrayAdapter);

        listaAdaptador = factoryAdaptadorGenerico.getCamposaFiltrar(this,(String)spinner.getSelectedItem(),"");
        if (listaAdaptador !=  null) listado.setAdapter(listaAdaptador);
        listado.setOnItemClickListener(this);
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

    /**
     *
     * @param menu
     * @return
     */
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
        listaAdaptador = factoryAdaptadorGenerico.getCamposaFiltrar(this,(String)spinner.getSelectedItem(),textoBuscar.getText().toString());
        if (listaAdaptador !=  null)  listado.setAdapter(listaAdaptador);
        return false;
    }
}
