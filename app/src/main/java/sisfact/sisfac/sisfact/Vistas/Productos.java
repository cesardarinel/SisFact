package sisfact.sisfac.sisfact.Vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import entidades.Camisas;
import entidades.Categorias;
import entidades.Contactos;
import entidades.Marcas;
import entidades.Pantalones;
import entidades.RopaInterioires;
import entidades.Secciones;
import entidades.TipoCamisas;
import entidades.TipoMangas;
import entidades.TipoRopaInteriores;
import entidades.Zapatos;
import sisfact.sisfac.sisfact.R;


public class Productos extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    protected String [] spinner = {"","Zapatos", "Camisa","Pantalon","Ropa Interior"};
    //cosas de las vista
    protected Spinner tipoProducto;
    protected EditText nombrePoducto;
    protected Spinner marcaProducto;
    protected EditText precioProducto;
    protected AutoCompleteTextView contactoProducto;
    protected AutoCompleteTextView seccionProducto;
    protected Spinner categoriaProducto;
    //Zapatos;
    protected TextView zapatosTextMedida;
    protected EditText zapatosMedida;

    //Camisa
    protected TextView tamanoTextCamisa;
    protected EditText tamanoCamisa;

    protected TextView tipoTextCamisa;
    protected Spinner tipoCamisa;

    protected TextView tipoMangaTextCamisa;
    protected Spinner tipoMangaCamisa;

    //Pantalones
    protected TextView largoTextPantalon;
    protected EditText largoPantalon;

    protected TextView anchoTextPantalon;
    protected EditText anchoPantalon;

    protected TextView sizeTextPantalon;
    protected EditText sizePantalon;

    //Ropa interioir
    protected TextView tipoTextRopaInterior;
    protected Spinner tipoRopaInterior;

    protected TextView medida1TextRopaInterior;
    protected EditText medida1RopaInterior;

    protected TextView medida2TextRopaInterior;
    protected EditText medida2RopaInterior;

    //Botones
    protected Button botonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_producto);

        tipoProducto = (Spinner) findViewById(R.id.plantilla_producto_spnr_tipo_producto);
        nombrePoducto = (EditText) findViewById(R.id.plantilla_producto_nombre);
        marcaProducto = (Spinner) findViewById(R.id.plantilla_producto_marca);
        ArrayList<String> listaMarca =  new ArrayList<>();
        List<Marcas> todasLasMarca = new Select().from(Marcas.class).execute();
        for (Marcas item : todasLasMarca)listaMarca.add(item.getNombre());
        ArrayAdapter<String> marcaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMarca);
        marcaProducto.setAdapter(marcaArrayAdapter);


        precioProducto = (EditText) findViewById(R.id.plantilla_producto_precio);
        contactoProducto = (AutoCompleteTextView) findViewById(R.id.plantilla_producto_contacto);
        seccionProducto = (AutoCompleteTextView) findViewById(R.id.plantilla_producto_seccion);
        categoriaProducto = (Spinner) findViewById(R.id.plantilla_producto_categoria);
        ArrayList<String> listaCategoria =  new ArrayList<>();
        List<Categorias> todasLasCategorias= new Select().from(Categorias.class).execute();
        for (Categorias item : todasLasCategorias)listaCategoria.add(item.getCategoria());
        ArrayAdapter<String> categoriaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategoria);
        categoriaProducto.setAdapter(categoriaArrayAdapter);

        //Zapatos
        zapatosTextMedida = (TextView) findViewById(R.id.plantilla_producto_zapatos_txt_medida);
        zapatosMedida = (EditText) findViewById(R.id.plantilla_producto_zapatos_medida);

        //Camisa
        tamanoTextCamisa = (TextView) findViewById(R.id.plantilla_producto_camisa_txt_tamano);
        tamanoCamisa = (EditText) findViewById(R.id.plantilla_producto_camisa_tamano);

        tipoTextCamisa = (TextView) findViewById(R.id.plantilla_producto_camisa_txt_tipo_camisa);

        tipoCamisa = (Spinner) findViewById(R.id.plantilla_producto_camisa_tipo_camisa);
        ArrayList<String> listaTipoCamisa =  new ArrayList<>();
        List<TipoCamisas> todasLosTipoCamisas = new Select().from(TipoCamisas.class).execute();
        for (TipoCamisas item : todasLosTipoCamisas)listaTipoCamisa.add(item.getTipoCamisa());
        ArrayAdapter<String> tipoCamisaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTipoCamisa);
        tipoCamisa.setAdapter(tipoCamisaArrayAdapter);

        tipoMangaTextCamisa = (TextView) findViewById(R.id.plantilla_producto_camisa_txt_tipo_manga);
        tipoMangaCamisa = (Spinner) findViewById(R.id.plantilla_producto_camisa_tipo_manga);
        ArrayList<String> listaMangaCamisa =  new ArrayList<>();
        List<TipoMangas> todasLasMangasCamisas = new Select().from(TipoMangas.class).execute();
        for (TipoMangas item : todasLasMangasCamisas)listaTipoCamisa.add(item.getTipoManga());
        ArrayAdapter<String> tipoMangaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMangaCamisa);
        tipoMangaCamisa.setAdapter(tipoMangaArrayAdapter);


        largoTextPantalon = (TextView) findViewById(R.id.plantilla_producto_pantalones_txt_largo);
        largoPantalon = (EditText) findViewById(R.id.plantilla_producto_pantalones_largo);

        anchoTextPantalon = (TextView) findViewById(R.id.plantilla_producto_pantalones_txt_ancho);
        anchoPantalon = (EditText) findViewById(R.id.plantilla_producto_pantalones_ancho);

        sizeTextPantalon = (TextView) findViewById(R.id.plantilla_producto_pantalones_txt_size);
        sizePantalon = (EditText) findViewById(R.id.plantilla_producto_pantalones_size);

        //RopaInterior;
        tipoTextRopaInterior = (TextView) findViewById(R.id.plantilla_producto_ropa_interior_txt_tipo);
        tipoRopaInterior = (Spinner) findViewById(R.id.plantilla_producto_ropa_interior_tipo);
        ArrayList<String> listaRopaInterior =  new ArrayList<>();
        List<TipoRopaInteriores> todasLosTipoRopaInteior = new Select().from(TipoRopaInteriores.class).execute();
        for (TipoRopaInteriores item : todasLosTipoRopaInteior)listaRopaInterior.add(item.getTipoRopaInterioir());
        ArrayAdapter<String> tipoRopaInteiorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaRopaInterior);
        tipoRopaInterior.setAdapter(tipoRopaInteiorArrayAdapter);

        medida1TextRopaInterior = (TextView) findViewById(R.id.plantilla_producto_ropa_interior_txt_medida1);
        medida1RopaInterior = (EditText) findViewById(R.id.plantilla_producto_ropa_interior_medida1);

        medida2TextRopaInterior = (TextView) findViewById(R.id.plantilla_producto_ropa_interior_txt_medida2);
        medida2RopaInterior = (EditText) findViewById(R.id.plantilla_producto_ropa_interior_medida2);

        botonGuardar = (Button) findViewById(R.id.plantilla_producto_btn_guardar);



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner);
        tipoProducto.setAdapter(spinnerArrayAdapter);
        tipoProducto.setOnItemSelectedListener(this);

        botonGuardar.setOnClickListener(this);


    }
    protected void Enable(String Habilitar){
        //Zapato
        zapatosTextMedida.setVisibility(View.GONE);
        zapatosMedida.setVisibility(View.GONE);

        //Camisa
        tamanoTextCamisa.setVisibility(View.GONE);
        tamanoCamisa.setVisibility(View.GONE);
        tipoTextCamisa.setVisibility(View.GONE);
        tipoCamisa.setVisibility(View.GONE);
        tipoMangaTextCamisa.setVisibility(View.GONE);
        tipoMangaCamisa.setVisibility(View.GONE);

        //Pantalones
        largoTextPantalon.setVisibility(View.GONE);
        largoPantalon.setVisibility(View.GONE);
        anchoTextPantalon.setVisibility(View.GONE);
        anchoPantalon.setVisibility(View.GONE);
        sizeTextPantalon.setVisibility(View.GONE);
        sizePantalon.setVisibility(View.GONE);

        //Ropa Interior
        tipoTextRopaInterior.setVisibility(View.GONE);
        tipoRopaInterior.setVisibility(View.GONE);
        medida1TextRopaInterior.setVisibility(View.GONE);
        medida1RopaInterior.setVisibility(View.GONE);
        medida2TextRopaInterior.setVisibility(View.GONE);
        medida2RopaInterior.setVisibility(View.GONE);

        switch (Habilitar){
            case "Zapatos":
                zapatosTextMedida.setVisibility(View.VISIBLE);
                zapatosMedida.setVisibility(View.VISIBLE);
                break;
            case "Camisa":
                tamanoTextCamisa.setVisibility(View.VISIBLE);
                tamanoCamisa.setVisibility(View.VISIBLE);
                tipoTextCamisa.setVisibility(View.VISIBLE);
                tipoCamisa.setVisibility(View.VISIBLE);
                tipoMangaTextCamisa.setVisibility(View.VISIBLE);
                tipoMangaCamisa.setVisibility(View.VISIBLE);
                break;
            case "Pantalon":
                largoTextPantalon.setVisibility(View.VISIBLE);
                largoPantalon.setVisibility(View.VISIBLE);
                anchoTextPantalon.setVisibility(View.VISIBLE);
                anchoPantalon.setVisibility(View.VISIBLE);
                sizeTextPantalon.setVisibility(View.VISIBLE);
                sizePantalon.setVisibility(View.VISIBLE);

                break;
            case "Ropa Interior":
                tipoTextRopaInterior.setVisibility(View.VISIBLE);
                tipoRopaInterior.setVisibility(View.VISIBLE);
                medida1TextRopaInterior.setVisibility(View.VISIBLE);
                medida1RopaInterior.setVisibility(View.VISIBLE);
                medida2TextRopaInterior.setVisibility(View.VISIBLE);
                medida2RopaInterior.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Enable(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {


        entidades.Productos productos =  new entidades.Productos();
        productos.setNombre(nombrePoducto.getText().toString());

        Marcas marcas =  new Select()
                .from(Marcas.class)
                .where("nombre = ?", (String) marcaProducto.getSelectedItem())
                .executeSingle();
        productos.setMarca(marcas);

        //TODO
        //productos.setCodigoBarra();

        Contactos contactos = new Select()
                .from(Contactos.class)
                .where("telefono = ?", contactoProducto.getText().toString())
                .executeSingle();
        productos.setContacto(contactos);

        Secciones secciones =  new Select()
                .from(Secciones.class)
                .where("seccion = ?",seccionProducto.getText().toString())
                .executeSingle();
        productos.setSeccion(secciones);

        productos.setTipo((String) tipoProducto.getSelectedItem());
        Categorias categorias = new Select()
                .from(Categorias.class)
                .where("categoria = ? ", (String) categoriaProducto.getSelectedItem())
                .executeSingle();
        productos.setCategoria(categorias);
        try {
            switch ((String)tipoProducto.getSelectedItem()){
                case "Zapatos":
                    Zapatos zapatos =  new Zapatos();
                    zapatos.setFrom(productos);
                    zapatos.setMedida(zapatosTextMedida.getText().toString());
                    zapatos.save();
                    break;
                case "Camisa":
                    Camisas camisas =  new Camisas();
                    camisas.setFrom(productos);
                    camisas.setTamano(tamanoCamisa.getText().toString());
                    TipoCamisas tipoCamisas =  new Select()
                            .from(TipoCamisas.class)
                            .where("tipo_camisa", (String) tipoCamisa.getSelectedItem())
                            .executeSingle();
                    camisas.setTipoCamisa(tipoCamisas);
                    TipoMangas tipoMangas =  new Select()
                            .from(TipoMangas.class)
                            .where("tipo_manga", (String) tipoMangaCamisa.getSelectedItem())
                            .executeSingle();
                    camisas.setTipoManga(tipoMangas);
                    camisas.save();
                    break;
                case "Pantalon":
                    Pantalones pantalones =  new Pantalones();
                    pantalones.setFrom(productos);
                    pantalones.setLargo(Float.valueOf(largoTextPantalon.getText().toString()));
                    pantalones.setAncho(Float.valueOf(anchoPantalon.getText().toString()));
                    pantalones.setSize(sizePantalon.getText().toString());
                    pantalones.save();
                    break;
                case "Ropa Interior":
                    RopaInterioires ropaInterioires =  new RopaInterioires();
                    ropaInterioires.setFrom(productos);
                    TipoRopaInteriores tipoRopaInteriores =  new Select()
                            .from(TipoRopaInteriores.class)
                            .where("tipo_ropa_interior", (String) tipoRopaInterior.getSelectedItem())
                            .executeSingle();
                    ropaInterioires.setTipoRopaInterior(tipoRopaInteriores);
                    ropaInterioires.setMedida1(medida1RopaInterior.getText().toString());
                    ropaInterioires.setMedida2(medida2RopaInterior.getText().toString());
                    ropaInterioires.save();
                    break;
            }
        }catch (Exception e){
            Toast.makeText(this,"error guardando " +tipoProducto,Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
