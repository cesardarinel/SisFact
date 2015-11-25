package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.R;
import sisfact.sisfac.sisfact.Vistas.AdaptadorGenerico.FactoryAdaptadorGenerico;

public class vista_modulo_generic extends AppCompatActivity implements AdapterView.OnItemClickListener ,AdapterView.OnKeyListener {

    String titulo;
    ListView listado;
    FactoryAdaptadorGenerico data;
    Spinner spinner;
    EditText textoBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_modulo_generic);
        listado = (ListView) findViewById(R.id.listView);

        textoBuscar = (EditText) findViewById(R.id.vista_generica_txt);
        textoBuscar.setOnKeyListener(this);
        Bundle tipoVista = getIntent().getExtras();
        titulo = tipoVista.getString("Actividad");
        data = (FactoryAdaptadorGenerico) tipoVista.getSerializable("Datos");
        if (data == null){
            Toast.makeText(this,"Los Datos Suministrados son invalidos",Toast.LENGTH_LONG).show();
            finish();
        }
        ArrayAdapter<String> stringArrayAdapter= data.getCamposBuscablesAdator(this);
        spinner =(Spinner)  findViewById(R.id.spinner);
        spinner.setAdapter(stringArrayAdapter);


        ListaAdaptador  listaAdaptador = data.getCamposaFiltrar(this,(String)spinner.getSelectedItem(),"");
        if (listaAdaptador !=  null)  listado.setAdapter(listaAdaptador);
        listado.setOnItemClickListener(this);
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

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nuevo:
                Intent nuevaActividad = data.getIntentClase(this);
                nuevaActividad.putExtra("Actividad","");
                startActivity(nuevaActividad);
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

    /**
     *
     * @param adapter
     * @param view
     * @param position
     * @param arg
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
        Intent intent = data.getIntentClase(this);
        String id = data.getObjetosListado().get(position).getId();
        intent.putExtra("id", id);
        startActivity(intent);
        this.finish();
    }

    /**
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        ListaAdaptador  listaAdaptador = data.getCamposaFiltrar(this,(String)spinner.getSelectedItem(),textoBuscar.getText().toString());
        if (listaAdaptador !=  null)  listado.setAdapter(listaAdaptador);
        return false;
    }
}
