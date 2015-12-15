package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entidades.*;
import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_factura_lineas extends ListActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    //Lista que se llena con valores de la base de datos;
    List<String> listaProductos= new ArrayList<String>();
    List<String> listaTipo = new ArrayList<String>();
    ArrayList<String> lineas = new ArrayList<String>();
    HashMap<String,BigDecimal> mapaProductos = new HashMap<>();
    TableLayout table;
    Spinner spin;

    Button boton;

    //Esta variable es para el tipo de producto
    String tipo;

    //obtener valor de actividades
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura_lineas);
        /*ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);*/

        boton = (Button) findViewById(R.id.button_guardar_lineas);
        boton.setOnClickListener(this);
        spin = (Spinner) findViewById(R.id.spinner_tipo_producto);
        spin.setOnItemSelectedListener(this);

        table = (TableLayout) findViewById(R.id.data_table);

        //agregando valores a la lista de Productos
        listaTipo.add("--Ninguno--");
        listaTipo.add("Camisa");
        listaTipo.add("Pantalon");
        listaTipo.add("Ropa Interior");
        listaTipo.add("Zapatos");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTipo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, listaProductos));

    }
    /**
     *
     * @param parent
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView parent, View v,int position,long id){
        CheckedTextView item = (CheckedTextView) v;
        Toast.makeText(this, listaProductos.get(position) + " checked : " +
                item.isChecked(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            extras = data.getExtras();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guardar_lineas:
                Intent actividadactual = new Intent(this, vista_factura.class);
                ArrayList<String> l = getlistaseleccionada();
                actividadactual.putExtra("listalineas", l);
                actividadactual.putExtra("mapaProductos", mapaProductos);
                if (!l.isEmpty() && !mapaProductos.isEmpty()) {
                    if(extras != null){
                        if (extras.getStringArrayList("productoslist") == l) {
                            actividadactual.putExtra("estado", "igual");
                        } else if (extras.getStringArrayList("productoslist").size() < l.size()){
                            actividadactual.putExtra("estado", "nuevo");
                        }

                    }else {
                        actividadactual.putExtra("estado", "lleno");
                    }

                } else if (l.isEmpty() && mapaProductos.isEmpty()) {
                    actividadactual.putExtra("estado", "vacio");
                }
                setResult(RESULT_OK,actividadactual);
                finish();
        }
    }

    public ArrayList<String> getlistaseleccionada(){
        ListView listView = getListView();
        SparseBooleanArray listchecked = listView.getCheckedItemPositions();
        for(int i=0; i < listView.getCount();i++){
            if(listchecked.get(i) == true){
                lineas.add(listaProductos.get(i));
            }
        }
        return lineas;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item1 = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();

        ListView listView = getListView();
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, listaProductos);
        listView.setAdapter(adap);
        adap.clear();
        tipo = spin.getSelectedItem().toString();
        llenarlistproductos();
        //llenarMapaProductos();
        //  adap.addAll(listaProductos);
        adap.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public List<Productos> getProductos(String tipo) {
        return new Select()
                .from(Productos.class)
                .where("tipo = ?",tipo)
                .execute();
    }

    public void llenarlistproductos(){
        List<Productos> templist = getProductos(tipo);
        if(!templist.isEmpty()) {
            for (Productos p : templist) {
                listaProductos.add(p.getNombre());
                mapaProductos.put(p.getNombre(),p.getPrecio());
            }
        }

    }

}
