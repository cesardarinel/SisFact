package sisfact.sisfac.sisfact.Vistas;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import entidades.*;
import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_factura_lineas extends ListActivity {

    //Lista que se llena con valores de la base de datos;
    List<String> listaProductos= new ArrayList<String>();

    Spinner spinItem;

    //Esta variable es para el tipo de producto
    String tipo;

    //obtener valor de la primera actividad
    Bundle extras;

    /*String[] city= {
            "Bangalore",
            "Chennai",
            "Mumbai",
            "Pune",
            "Delhi",
            "Jabalpur",
            "Indore",
            "Ranchi",
            "Hyderabad",
            "Ahmedabad",
            "Kolkata",
            "Bhopal"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura_lineas);
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);

        //Obteniendo valor de vista anterior
        extras = getIntent().getExtras();

        //Aqui se obtiene el valor del activity vista_factura spinner de tipo
        if(extras != null){
            tipo = extras.getString("spinItem");
        }

        List<Productos> templist = getProductos(tipo);
        if(tipo!= null) {
            for (Productos p : templist) {
                listaProductos.add(p.getNombre());
            }
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, listaProductos));
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
        //Toast.makeText(this, "OnItemSelectedListner: " + listaTipo.get(position).toString(), Toast.LENGTH_SHORT).show();
    }

    public List<Productos> getProductos(String tipo) {
        return new Select()
                .from(Productos.class)
                .execute();
    }

}
