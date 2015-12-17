package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entidades.*;
import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_factura extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    public Button agregar;
    public Spinner spin;
    public TableLayout tblayout;
    public Bundle extras;


    //Este objeto nos ayuda a crear la vista de cantidad
    private AlertDialog.Builder alerta;
    private LayoutInflater li;
    private View vistadialog;
    final Context dialog = this;
    public ArrayList<String> productos;
    public HashMap<Long,Productos> productoMap;
    public HashMap<Long,Productos> mapaMandar;
    public TextView eltotal;
    public BigDecimal total;
    public Double valor;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura);

        agregar = (Button) findViewById(R.id.button_agregar_lineas);
        agregar.setOnClickListener(this);
        //txtedit = (EditText) findViewById(R.id.cantidadArticulo_txt_cantidad);
        //Tabla para mostrar lineas
        tblayout = (TableLayout) findViewById(R.id.data_table);
        tblayout.setStretchAllColumns(true);
        tblayout.setShrinkAllColumns(true);

        productoMap = new HashMap<>();
        mapaMandar = new HashMap<>();
        productos = new ArrayList<>();

        //Se crea la tabla para poder visualizar la lineas;
        creandoTabla();

        eltotal = (TextView) findViewById(R.id.vista_factura_txt_total);
        total = BigDecimal.valueOf(0);
        valor = 0.0;

    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_agregar_lineas) {
            Intent nuevaActividad = new Intent(this, vista_factura_lineas.class);
            //nuevaActividad.putExtra("spinItem",spin.getSelectedItem().toString());

            //nuevaActividad.putExtra("productoslist", productos);
            if(!mapaMandar.isEmpty()){
                nuevaActividad.putExtra("mapaMandar", mapaMandar);
            }
            startActivityForResult(nuevaActividad,1);


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            extras = data.getExtras();
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if(extras != null){
                    //Titulo de la tabla

                    String estado = extras.getString("estado");
                    ArrayList<String> items = extras.getStringArrayList("listalineas");
                    HashMap<Long,Productos> mapa = (HashMap<Long,Productos>) data.getSerializableExtra("productosMapa");
                    HashMap<Long,Productos> tempmap = new HashMap<Long,Productos>();

                    for(Long l: mapa.keySet()){
                        if(productoMap.containsKey(l)){
                            continue;
                        }else {
                            productoMap.put(l,mapa.get(l));
                        }
                    }

                    tempmap.putAll(productoMap);
                    mapaMandar.putAll(productoMap);
                    productoMap.clear();

                    //for (int i = 0; i < productos.size(); i++) {
                    for(Productos p: tempmap.values()){
                        TableRow row = new TableRow(this);

                        String temp = p.getNombre();
                        BigDecimal cant = p.getPrecio();

                        TextView nombre = new TextView(this);
                        nombre.setText(temp);
                        row.addView(nombre);

                        TextView pre = new TextView(this);
                        pre.setText(cant.toString());
                        pre.setGravity(Gravity.RIGHT);
                        row.addView(pre);

                        TextView cantidad = new TextView(this);
                        cantidad.setGravity(Gravity.RIGHT);

                        Button edit = new Button(this);
                        edit.setOnClickListener(ventanaCantidad(cantidad, cant));
                        edit.setText("Editar");
                        edit.setMaxWidth(1);

                        row.addView(cantidad);
                        row.addView(edit);

                        tblayout.addView(row);
                    }
                }
            }
        }
    }

    public View.OnClickListener ventanaCantidad(final TextView cantidad,final BigDecimal cant){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edit = new EditText(dialog);

                li = LayoutInflater.from(dialog);

                vistadialog = li.inflate(R.layout.activity_cantidad__articulo, null);
                alerta = new AlertDialog.Builder(dialog);
                alerta.setView(edit);
                alerta.setTitle("Cantidad de articulos");
                alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface vistaOpcion, int id) {
                        if (edit != null) {
                            cantidad.setText(edit.getText());
                            total = getTotal();
                            //total.add(cant.multiply(BigDecimal.valueOf(Double.valueOf(String.valueOf(edit.getText().toString())))));
                            eltotal.setText("Total: " + String.valueOf(total));
                        }
                    }

                });

                AlertDialog altd = alerta.create();
                altd.show();

            }

        };


    }


    public void creandoTabla(){
        final TableRow titulo = new TableRow(this);
        titulo.setGravity(Gravity.CENTER_HORIZONTAL);

        //titulo de la tabla
        TextView titulofila = new TextView(this);
        titulofila.setText("Lineas de Factura");
        titulofila.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titulofila.setGravity(Gravity.CENTER);
        titulofila.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        titulo.addView(titulofila, params);

        //Titulos de columnas
        TableRow titulocolumnas = new TableRow(this);

        TextView columnas = new TextView(this);
        columnas.setText("Producto");
        columnas.setGravity(Gravity.LEFT);

        TextView columnas1 = new TextView(this);
        columnas1.setText("Costo");
        columnas1.setGravity(Gravity.CENTER);

        TextView columnas2 = new TextView(this);
        columnas2.setText("Cantidad");
        columnas2.setGravity(Gravity.RIGHT);

        titulocolumnas.addView(columnas);
        titulocolumnas.addView(columnas1);
        titulocolumnas.addView(columnas2);

        tblayout.addView(titulo);
        tblayout.addView(titulocolumnas);

    }

    public BigDecimal getTotal(){
        BigDecimal t = BigDecimal.ZERO;
        Double d = 0.0;

        for(int i = 2;i < tblayout.getChildCount(); i++){
            TableRow row = (TableRow) tblayout.getChildAt(i);
            TextView colm1 = (TextView) row.getChildAt(1);
            TextView colm2 = (TextView) row.getChildAt(2);
            if(colm1.getText().toString().equals("")){
                colm1.setText("0");
            }
            if(colm2.getText().toString().equals("")){
                colm2.setText("0");
            }
            if(colm1 != null && colm2 != null) {
                d = (Double.valueOf(colm1.getText().toString()) * Double.valueOf(colm2.getText().toString()));
            }else {

            }
            t = t.add(BigDecimal.valueOf(d));
        }
        return t;
    }
}
