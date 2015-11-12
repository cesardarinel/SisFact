package sisfact.sisfac.sisfact.Vistas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.util.SQLiteUtils;

import java.util.ArrayList;
import java.util.List;

import entidades.Contactos;
import entidades.ItemLista;
import sisfact.sisfac.sisfact.R;

public class vista_modulo_generic extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    String actividad;
    ListView listView;
    ArrayList<ItemLista> dataSet;
    ListaAdaptador adaptador;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_modulo_generic);

        Bundle parametros = getIntent().getExtras();
        actividad = parametros.getString("Actividad");

        listView = (ListView) findViewById(R.id.listView);
        dataSet = new ArrayList<>();
        adaptador = new ListaAdaptador(this, dataSet);

        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(this);
        new GenericListHandler().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_generico_editar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nuevo:
                Intent intent = new Intent(this, Contacto.class);
                intent.putExtra("Actividad","");
                startActivity(intent);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Contacto.class);
        String mensaje = dataSet.get(position).get_TextoID();
        intent.putExtra("Actividad", mensaje);
        entidades.Productos p = new entidades.Productos();
        startActivity(intent);
        finish();

    }


    private class GenericListHandler extends AsyncTask<Void,Void,String> {
        private ArrayList data;

        @Override
        protected String doInBackground(Void... params) {

            String array = null;

            List<Contactos> queryResults = SQLiteUtils.rawQuery(Contactos.class,
                    "SELECT * from Contactos", null);
            for ( int i = 0 ; i < queryResults.size(); i++ ) {
                dataSet.add(new ItemLista(queryResults.get(i).getId().toString(),queryResults.get(i).getTelefono(),queryResults.get(i).getNombre(),queryResults.get(i).getApellido()));
            }
            adaptador.notifyDataSetChanged();

            return array;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(vista_modulo_generic.this);
            pDialog.setMessage(getString(R.string.Cargando));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (pDialog.isShowing())
            {
                pDialog.dismiss();
            }
        }
    }

}

