package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import entidades.Contactos;
import entidades.FacturaProductos;
import entidades.Facturas;
import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_factura extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

//    public Button agregar;
    public Spinner spin;
    public TableLayout tblayout;
    public Bundle extras;
    protected EditText textoFecha;
    protected Menu menuFactura;
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
    protected SimpleDateFormat dateFormatter;
    protected DatePickerDialog fechaDialogo;
    protected Facturas facturaCargada = null;
    protected Date fechaParaGuardar = new Date();
    protected AutoCompleteTextView completarContactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_factura);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es","ES"));
        completarContactos = (AutoCompleteTextView) findViewById(R.id.plantilla_factura_contacto);
        ArrayList<String> campoSugerencias = new ArrayList<>();
        List<Contactos> contactosList =  new Select().from(entidades.Contactos.class).execute();
        for(entidades.Contactos con : contactosList) {
            campoSugerencias.add(con.getTelefono());
            campoSugerencias.add(con.getCelular());
        }

        completarContactos.setAdapter(
            new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                campoSugerencias
            )
        );
        textoFecha = (EditText) findViewById(R.id.plantilla_factura_fecha);
        tblayout = (TableLayout) findViewById(R.id.data_table);

        productoMap = new HashMap<>();
        mapaMandar = new HashMap<>();
        productos = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        fechaDialogo = createDatePickerDialog(
                textoFecha,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        //Se crea la tabla para poder visualizar la lineas;
        creandoTabla();
        textoFecha.setOnClickListener(this);
        eltotal = (TextView) findViewById(R.id.vista_factura_txt_total);
        total = BigDecimal.ZERO;
        textoFecha.setText(dateFormatter.format(calendar.getTime()));
        try{
            Long id = Long.valueOf(getIntent().getExtras().getString("id"));
            facturaCargada = new Select().from(Facturas.class).where("id = ?",id).executeSingle();
        }
        catch (Exception e){}
        if (facturaCargada!=null){
            List<FacturaProductos> facturaProductoses = new Select().from(FacturaProductos.class).where("factura = ?",facturaCargada.getId()).execute();
            for (FacturaProductos item : facturaProductoses){
                agregarFilaTabla(item.getProducto(),item.getCantidad());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.plantilla_factura_fecha){
            fechaDialogo.show();
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
    private DatePickerDialog createDatePickerDialog(final EditText e, final int year, final int month, final int day){

        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e.setText(dateFormatter.format(newDate.getTime()));
                fechaParaGuardar = newDate.getTime();
            }

        },year, month, day);
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
                    HashMap<Long,Productos> tempmap = new HashMap<>();

                    for(Long l: mapa.keySet()){
                        if(!productoMap.containsKey(l)){
                            productoMap.put(l,mapa.get(l));
                        }
                    }

                    tempmap.putAll(productoMap);
                    mapaMandar.putAll(productoMap);
                    productoMap.clear();

                    for(Productos p: tempmap.values()){
                        agregarFilaTabla(p);
                    }
                }
            }
        }
    }
    protected void agregarFilaTabla(Productos p){
        agregarFilaTabla(p,0);
    }
    protected void agregarFilaTabla(Productos p,Integer Cantidad){
        TableRow row = new TableRow(this);
        String temp = p.getNombre();
        BigDecimal cant = p.getPrecio();
        TextView nombre = new TextView(this);
        nombre.setText(temp);
        row.addView(nombre);
        TextView pre = new TextView(this);
        pre.setText(String.format("%.2f", cant.floatValue()));
        row.addView(pre);
        TextView cantidad = new TextView(this);
        cantidad.setText(Cantidad.toString());
        ImageView edit = new ImageView(this);
        row.addView(cantidad);
        row.addView(edit);
        EditarCantidadProductos editarCantidadProductos = new EditarCantidadProductos();
        editarCantidadProductos.setTableRow(row);
        editarCantidadProductos.setIdProducto(p.getInternalId());
        edit.setOnClickListener(editarCantidadProductos);
        edit.setImageResource(R.drawable.ic_menu_edit);

        tblayout.addView(row);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Agregar:
                Intent nuevaActividad = new Intent(this, vista_factura_lineas.class);
                //nuevaActividad.putExtra("spinItem",spin.getSelectedItem().toString());

                //nuevaActividad.putExtra("productoslist", productos);
                if(!mapaMandar.isEmpty()){
                    nuevaActividad.putExtra("mapaMandar", mapaMandar);
                }
                startActivityForResult(nuevaActividad,1);
                return true;
            case R.id.Guardar:
                Intent rIntent = new Intent();
                Contactos contacto= null;
                if (!completarContactos.getText().toString().isEmpty()){
                    contacto = new Select()
                            .from(Contactos.class)
                            .where("telefono = ? OR celular = ? ", completarContactos.getText().toString(),
                                    completarContactos.getText().toString()).executeSingle();
                }
                if(esValidoFactura(contacto,fechaParaGuardar)) {
                    Facturas fact;

                    if (facturaCargada == null)fact = new Facturas();
                    else fact = facturaCargada;

                    fact.setContacto(contacto);
                    fact.setFecha(fechaParaGuardar);
                    fact.save();
                    Boolean esValido = true;
                    int i= 0;
                    Boolean alMenos1 = false;
                    for (Long prodid :mapaMandar.keySet()){
                        alMenos1 = true;
                        int cantidad;
                        try{
                        cantidad = Integer.valueOf(
                            (
                                (TextView) ((TableRow) tblayout.getChildAt(i + 2)).getChildAt(2)
                            ).getText().toString()
                        );
                        }catch (Exception e){cantidad=0;}
                        Productos productos = new Select().from(Productos.class).where("id = ? ",prodid).executeSingle();

                        if(cantidad <= 0){
                            fact.delete();
                            showError("Producto "+productos.getNombre() +" tiene que tener almenos 1");
                            return true;
                        }
                        productos.setCantidad(productos.getCantidad() - cantidad);
                        productos.save();
                        FacturaProductos facturaProductos = new FacturaProductos();
                        facturaProductos.setProducto(productos);
                        facturaProductos.setCantidad(cantidad);
                        facturaProductos.setFactura(fact);
                        facturaProductos.save();
                    }
                    if (!alMenos1){
                        showError("debe haber almenos una linea");
                        return true;
                    }
                    fact.save();
                    rIntent.putExtra("id", fact.getId());
                    setResult(RESULT_OK, rIntent);
                    finish();
                }
                return true;
            default:
                return false;
        }
    }
    protected Boolean esValidoFactura(Contactos contactos,Date date){
        if (contactos == null){
            completarContactos.setError("El Contacto no es valido");
            return false;
        }
        if (date == null){
            textoFecha.setError("La fecha no es validas");
            return false;
        }
        return true;
    }
    protected void showError(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        builder.create().show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuFactura = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_factura , menu);
        return true;
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
        TextView colm1 ;
        TextView colm2;
        for(int i = 2;i < tblayout.getChildCount(); i++){
            TableRow row = (TableRow) tblayout.getChildAt(i);
            colm1 = (TextView) row.getChildAt(1);
            colm2 = (TextView) row.getChildAt(2);
            if(colm1.getText().toString().equals("")){
                colm1.setText("0");
            }
            if(colm2.getText().toString().equals("")){
                colm2.setText("0");
            }

            BigDecimal numero1 = new BigDecimal(colm1.getText().toString());
            BigDecimal numero2 = new BigDecimal(colm2.getText().toString());

            t = t.add(numero1.multiply(numero2));
        }
        return t;
    }
    class EditarCantidadProductos implements View.OnClickListener{
        protected Long idProducto;
        protected TableRow tableRow;
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
                    Productos productos = new Select().from(Productos.class).where("id = ?",idProducto).executeSingle();
                    Integer cantidad = Integer.valueOf(edit.getText().toString());
                    if (productos.getCantidad() < cantidad) {
                        showError("No hay tantos productos solo tiene "+productos.getCantidad()+" de "+cantidad);
                    }
                    else {
                        (
                            (TextView) (tableRow).getChildAt(2)
                        ).setText(cantidad.toString());
                    }
                }

            });
            AlertDialog altd = alerta.create();
            altd.show();
        }

        public Long getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(Long idProducto) {
            this.idProducto = idProducto;
        }

        public TableRow getTableRow() {
            return tableRow;
        }

        public void setTableRow(TableRow tableRow) {
            this.tableRow = tableRow;
        }
    }
}
