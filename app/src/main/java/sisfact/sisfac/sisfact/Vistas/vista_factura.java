package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sisfact.sisfac.sisfact.R;

public class vista_factura extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    public Button agregar;
    public Spinner spin;
    public List<String> listaTipo = new ArrayList<String>();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura);

        agregar = (Button) findViewById(R.id.button_agregar_lineas);
        spin = (Spinner) findViewById(R.id.spinner_tipo_producto);

        agregar.setOnClickListener(this);
        spin.setOnItemSelectedListener(this);

        //agregando valores a la lista de Productos
        listaTipo.add("--Ninguno--");
        listaTipo.add("Camisa");
        listaTipo.add("Pantalon");
        listaTipo.add("Ropa Interior");
        listaTipo.add("Zapatos");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaTipo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v){
        if (v.getId()==R.id.button_agregar_lineas){
            Intent  nuevaActividad = new Intent(this, vista_factura_lineas.class);
            nuevaActividad.putExtra("spinItem",spin.getSelectedItem().toString());
            startActivity(nuevaActividad);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
