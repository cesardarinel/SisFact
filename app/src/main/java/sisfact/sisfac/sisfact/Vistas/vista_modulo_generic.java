package sisfact.sisfac.sisfact.Vistas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entidades.Contactos;
import entidades.Listado;
import sisfact.sisfac.sisfact.R;

import static com.activeandroid.util.SQLiteUtils.rawQuery;

public class vista_modulo_generic extends ActionBarActivity implements AdapterView.OnItemClickListener  {

    String titulo;
    ProgressDialog pDialog;
    ListView listado;
    ArrayList<Listado> adaptador_list;
    ListaAdaptador adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_modulo_generic);
        //capturo lo enviado en la vista anterior
        Bundle tipoVista = getIntent().getExtras();
        titulo = tipoVista.getString("Actividad");

        //Esto se leera de la base de datos
        String[] country={"Nombre", "Apellido", "Telefono"};// temporal

        //iniciamos el espiner
        /*/  -----------------------------------------------------/*/
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, country);
        Spinner spinner =(Spinner)  findViewById(R.id.spinner);
        spinner.setAdapter(stringArrayAdapter);
        /*/  -----------------------------------------------------/*/


        /*  inicio el list view*/
        listado= (ListView) findViewById(R.id.listView);
        adaptador_list = new ArrayList<Listado>();
        adaptador = new ListaAdaptador(this, adaptador_list);
        // Asignamos el Adapter al ListView.

        listado.setAdapter((android.widget.ListAdapter) adaptador);
        listado.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        new LLenamos().execute();



    }

    /*******************************/
    private class LLenamos extends AsyncTask<Void, Void, String> {
        private ArrayList data;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(vista_modulo_generic.this);
            pDialog.setMessage(getString(R.string.Cargando));
            pDialog.setCancelable(false);
            pDialog.show();

        }
////////////plo plo hilo que corre atras
        @Override
        protected String doInBackground(Void... arg0) {

            String array = null;

            List<Contactos> queryResults =
                    SQLiteUtils.rawQuery(Contactos.class,
                            "SELECT * from Contactos",null);
            int i;
            for ( i = 0 ; i < queryResults.size(); i++ ) {

                adaptador_list.add(new Listado(queryResults.get(i).getNombre(),queryResults.get(i).getTelefono(),queryResults.get(i).getApellido(),null));
            }
            adaptador.notifyDataSetChanged();
            return array;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
            {
                pDialog.dismiss();

            }

        }




    }
    //agrege el menu aqui solo para ver como quedo y me puedan evaluar la tarea
/*                          inicio                                           */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_generico_editar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nuevo:
                Intent nuevaActividad = new Intent(this, Contacto.class);
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
    /*              Fin                                                              */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
        Intent intent = new Intent(this, Contacto.class);
        String mensaje = adaptador_list.get(position).get_Info();
        intent.putExtra("Actividad", mensaje);

        startActivity(intent);
        this.finish();

    }

}
