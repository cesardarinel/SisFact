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
    ArrayList<Long> Idproductos = new ArrayList<Long>();
    List<String> listaTipo = new ArrayList<String>();
    HashMap<Long,Productos> productosMapa = new HashMap<>();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guardar_lineas:
                Intent actividadactual = new Intent(this, vista_factura.class);
                productosMapa = getlistaseleccionada();
                actividadactual.putExtra("productosMapa", productosMapa);
                actividadactual.putExtra("estado", "lleno");
                extras = getIntent().getExtras();
                setResult(RESULT_OK,actividadactual);
                finish();
        }
    }

    public HashMap<Long,Productos> getlistaseleccionada(){
        ListView listView = getListView();
        SparseBooleanArray listchecked = listView.getCheckedItemPositions();
        List<Productos> templist =  getProductos(tipo);
        HashMap<Long,Productos> temp = new HashMap<Long,Productos>();
        HashMap<Long,Productos> mapa =new HashMap<>();
        for(Productos p: templist){
            temp.put(p.getId(),p);
        }
        for(int i=0; i < listView.getCount();i++){
            if(listchecked.get(i) == true){
                //lineas.add(listaProductos.get(i));
                Productos prod = temp.get(Idproductos.get(i));
                prod.setInternalId(prod.getId());
                mapa.put(Idproductos.get(i),prod);
            }
        }

        return mapa;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item1 = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();

        ListView listView = getListView();

        ArrayAdapter<String> adap = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked, listaProductos);

        listView.setAdapter(adap);
        adap.clear();
        tipo = spin.getSelectedItem().toString();
        llenarlistproductos();
        adap.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public List<Productos> getProductos(String tipo) {
        return new Select()
                .from(Productos.class)
                .where("tipo = ? and cantidad > 0",tipo)
                .execute();
    }

    public Productos getProducto(Long Id){
        return new Select()
                .from(Productos.class)
                .where("Id = ?",Id)
                .executeSingle();
    }

    public void llenarlistproductos(){
        Intent actualextras = getIntent();
        extras = actual.getExtras();
        HashMap<Long,Productos> mapa = (HashMap<Long,Productos>) actual.getSerializableExtra("mapaMandar");
        List<Productos> templist = getProductos(tipo);
        for (Productos p : templist) {
            if(extras != null){
                if(!mapa.isEmpty()) {
                    if(mapa.containsKey(p.getId())){
                        continue;
                    }else {
                        listaProductos.add(p.getNombre() + "\nCantidad: " + p.getCantidad() );
                        Idproductos.add(p.getId());
                    }
                }

            }
            else {
                listaProductos.add(p.getNombre() + "\nCantidad: " + p.getCantidad());
                Idproductos.add(p.getId());
            }
        }
    }

}
