package sisfact.sisfac.sisfact.Vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.math.BigDecimal;
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


public class Productos extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, View.OnFocusChangeListener {
    protected String [] spinner = {"Zapatos", "Camisa","Pantalon","Ropa Interior"};
    protected LinearLayout layout;
    Long idProducto = null;
    File photo = null;
    File saveDir = null;
    //cosas de las vista
    protected Spinner tipoProducto;
    protected EditText nombrePoducto;
    protected Spinner marcaProducto;
    protected EditText precioProducto;
    protected AutoCompleteTextView contactoProducto;
    protected Spinner seccionProducto;
    protected Spinner categoriaProducto;
    protected EditText cantidadProducto;
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
    protected ImageView botonSacarFoto;
    protected Button botonCancelar;
    //array adaptor
    protected ArrayAdapter<String> marcaArrayAdapter;
    protected ArrayAdapter<String> categoriaArrayAdapter;
    protected ArrayAdapter<String> tipoCamisaArrayAdapter;
    protected ArrayAdapter<String> tipoMangaArrayAdapter;
    protected ArrayAdapter<String> tipoProductoArrayAdapter;
    protected ArrayAdapter<String> tipoRopaInteiorArrayAdapter;
    protected ArrayAdapter<String> seccionArrayAdapter;
    protected ArrayAdapter<String> contactoArrayAdapter;

    Uri imageUri;
    Menu  menuProductos;
    final String Editar = "edicion";
    final String Detalle = "detalles";

    String modo = Detalle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_producto);

        setTitle("Productos");
        IniciarComponente();
        File tf = new File(Environment.getExternalStorageDirectory() +"/Foto-Productos/tmp.jpg");
        if (tf.exists())tf.delete();
        HabilitarProducto((String)tipoProducto.getSelectedItem());
        String id;
        idProducto = null;
        try{
            id = getIntent().getExtras().getString("id");
            idProducto = Long.valueOf(id);
        }catch(Exception e){}

        if (idProducto != null ){
            LlenarProducto();
            modo = Detalle;
        }
        else modo = Editar;
        cambiarEstadoComponentes();
    }

    protected void HabilitarEdicion(boolean habilitar){
        int total =  layout.getChildCount();
        for (int i=0;i<total;i++){
            View v = layout.getChildAt(i);
            if (v.getVisibility() == View.VISIBLE){
                if (v instanceof EditText || v instanceof Spinner) {
                    v.setEnabled(habilitar);
                }
            }
        }
        if(habilitar) {
            botonGuardar.setVisibility(View.VISIBLE);
            botonCancelar.setVisibility(View.VISIBLE);
        }
        else {
            botonGuardar.setVisibility(View.GONE);
            botonCancelar.setVisibility(View.GONE);
        }
    }

    protected void IniciarComponente(){
        layout = (LinearLayout) findViewById(R.id.plantill_producto_layout);
        tipoProducto = (Spinner) findViewById(R.id.plantilla_producto_spnr_tipo_producto);
        nombrePoducto = (EditText) findViewById(R.id.plantilla_producto_nombre);
        marcaProducto = (Spinner) findViewById(R.id.plantilla_producto_marca);
        cantidadProducto = (EditText) findViewById(R.id.plantill_producto_cantidad);

        ArrayList<String> listaMarca =  new ArrayList<>();
        List<Marcas> todasLasMarca = new Select().from(Marcas.class).execute();

        for (Marcas item : todasLasMarca)listaMarca.add(item.getNombre());
        marcaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMarca);
        marcaProducto.setAdapter(marcaArrayAdapter);

        precioProducto = (EditText) findViewById(R.id.plantilla_producto_precio);
        precioProducto.setOnFocusChangeListener(this);


        contactoProducto = (AutoCompleteTextView) findViewById(R.id.plantilla_producto_contacto);
        List<entidades.Contactos> contactoses =  new Select().from(entidades.Contactos.class).execute();
        ArrayList<String> listaContacto =  new ArrayList<>();
        for(entidades.Contactos con : contactoses) listaContacto.add(con.getTelefono());
        contactoArrayAdapter =  new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,listaContacto);
        contactoProducto.setAdapter(contactoArrayAdapter);
        contactoProducto.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        seccionProducto = (Spinner) findViewById(R.id.plantilla_producto_seccion);
        List<Secciones> secciones =  new Select().from(Secciones.class).execute();
        ArrayList<String> listaSeccion =  new ArrayList<>();
        for (Secciones item : secciones) listaSeccion.add(item.getSeccion());
        seccionArrayAdapter =  new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaSeccion);
        seccionProducto.setAdapter(seccionArrayAdapter);

        categoriaProducto = (Spinner) findViewById(R.id.plantilla_producto_categoria);
        ArrayList<String> listaCategoria =  new ArrayList<>();
        List<Categorias> todasLasCategorias= new Select().from(Categorias.class).execute();
        for (Categorias item : todasLasCategorias)listaCategoria.add(item.getCategoria());
        categoriaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategoria);
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
        tipoCamisaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTipoCamisa);
        tipoCamisa.setAdapter(tipoCamisaArrayAdapter);

        tipoMangaTextCamisa = (TextView) findViewById(R.id.plantilla_producto_camisa_txt_tipo_manga);
        tipoMangaCamisa = (Spinner) findViewById(R.id.plantilla_producto_camisa_tipo_manga);
        ArrayList<String> listaMangaCamisa =  new ArrayList<>();
        List<TipoMangas> todasLasMangasCamisas = new Select().from(TipoMangas.class).execute();
        for (TipoMangas item : todasLasMangasCamisas)listaMangaCamisa.add(item.getTipoManga());
        tipoMangaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMangaCamisa);
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




        tipoProductoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner);
        tipoProducto.setAdapter(tipoProductoArrayAdapter);
        tipoProducto.setOnItemSelectedListener(this);

        botonGuardar = (Button) findViewById(R.id.plantilla_producto_btn_guardar);
        botonSacarFoto= (ImageView) findViewById(R.id.plantill_producto_imagen_poducto);
        botonCancelar= (Button) findViewById(R.id.plantilla_producto_btn_cancelar);

        botonGuardar.setOnClickListener(this);
        botonSacarFoto.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);
    }


    protected void HabilitarProducto(String Habilitar){
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
        HabilitarProducto(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void LlenarProducto(){
        entidades.Productos prod = new Select()
                .from(entidades.Productos.class)
                .where("id = ?",idProducto)
                .executeSingle();

        botonGuardar.setVisibility(View.GONE);
        if (prod.getRutaImagen() != null){
            ImageLoader img = ImageLoader.getInstance();
            img.displayImage("file:///"+prod.getRutaImagen(),botonSacarFoto);
        }
        if (prod.getTipo() != null){
            int tipoProductoPos = tipoProductoArrayAdapter.getPosition(prod.getTipo());
            tipoProducto.setSelection(tipoProductoPos);
        }

        if(prod.getNombre() != null) nombrePoducto.setText(prod.getNombre());


        if (prod.getMarca() != null){
            int marcaPos = marcaArrayAdapter.getPosition(prod.getMarca().getNombre());
            marcaProducto.setSelection(marcaPos);
        }

        if(prod.getPrecio() != null) precioProducto.setText(
            String.format("%.02f",Float.valueOf(prod.getPrecio().toString()))
        );

        if (prod.getCantidad() != null) cantidadProducto.setText(prod.getCantidad().toString());


        if (prod.getContacto() != null) contactoProducto.setText(prod.getContacto().getTelefono());



        if(prod.getSeccion() != null){
            int seccionPos = seccionArrayAdapter.getPosition(prod.getSeccion().getSeccion());
            seccionProducto.setSelection(seccionPos);
        }


        if (prod.getCategoria() != null){
            int categoriaPos = categoriaArrayAdapter.getPosition(prod.getCategoria().getCategoria());
            categoriaProducto.setSelection(categoriaPos);
        }

        if(prod.getTipo() != null) HabilitarProducto(prod.getTipo());
        HabilitarEdicion(true);
        BuscarCambiosTipoProducto(prod.getTipo(), idProducto);


    }

    protected void BuscarCambiosTipoProducto(String tipo, Long idProd){
        switch (tipo){
            case "Zapatos":

                Zapatos zapatos = new Select()
                        .from(Zapatos.class)
                        .where("producto = ?", idProd)
                        .executeSingle();
                if(zapatos.getMedida()!=null) zapatosMedida.setText(zapatos.getMedida());

                break;
            case "Camisa":
                Camisas camisas = new Select()
                        .from(Camisas.class)
                        .where("producto = ?",idProd)
                        .executeSingle();


                if(camisas.getTamano() != null) tamanoCamisa.setText(camisas.getTamano());


                if(camisas.getTipoCamisa().getTipoCamisa() != null) {
                    int tipoCamisaPos = tipoCamisaArrayAdapter.getPosition(camisas.getTipoCamisa().getTipoCamisa());
                    tipoCamisa.setSelection(tipoCamisaPos);
                }

                tipoMangaCamisa.setEnabled(false);

                if (camisas.getTipoManga() != null) {
                    int tipoMangaPos = tipoMangaArrayAdapter.getPosition(camisas.getTipoManga().getTipoManga());
                    tipoMangaCamisa.setSelection(tipoMangaPos);
                }

                break;
            case "Pantalon":
                Pantalones pantalones = new Select()
                        .from(Pantalones.class)
                        .where("producto = ?",idProd)
                        .executeSingle();


                if( pantalones.getLargo() != null) largoPantalon.setText(String.valueOf(pantalones.getLargo()));

                if(pantalones.getAncho() != null) anchoPantalon.setText(String.valueOf(pantalones.getAncho()));

                if(pantalones.getSize() != null) sizePantalon.setText(String.valueOf(pantalones.getSize()));

                break;
            case "Ropa Interior":
                RopaInterioires ropaInterioires = new Select()
                        .from(RopaInterioires.class)
                        .where("producto = ?",idProd)
                        .executeSingle();


                if (ropaInterioires.getTipoRopaInterior() != null) {
                    int tipoRopaInteriorPos = tipoRopaInteiorArrayAdapter.getPosition(ropaInterioires.getTipoRopaInterior().getTipoRopaInterioir());
                    tipoRopaInterior.setSelection(tipoRopaInteriorPos);
                }

                if(ropaInterioires.getMedida1() != null) medida1RopaInterior.setText(ropaInterioires.getMedida1());

                if(ropaInterioires.getMedida2() != null) medida2RopaInterior.setText(ropaInterioires.getMedida2());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plantilla_producto_btn_guardar:
                GuardarProucto();
                break;
            case R.id.plantill_producto_imagen_poducto:
                TomarFotoProducto();
                break;
            case R.id.plantilla_producto_btn_cancelar:
                if (idProducto == null) {
                    finish();
                    return;
                }
                modo= "detalles";
                cambiarEstadoComponentes();
                break;
        }
    }

    protected void GuardarProucto(){
        boolean esValidoProducto = true;
        boolean esValidoTipoProducto = true;
        entidades.Productos productos;

        if (idProducto == null){
            productos = new entidades.Productos();
        }
        else {
            productos = new Select().from(entidades.Productos.class).where("id = ?",idProducto.toString()).executeSingle();
        }
        productos.setNombre(nombrePoducto.getText().toString());

        productos.setTipo((String)tipoProducto.getSelectedItem());

        if (productos.getNombre().isEmpty()){
            esValidoProducto = false;
            nombrePoducto.setError("El nombre del producto no puedes estar vacio");
        }

        if (marcaProducto.getSelectedItem() != null){
            Marcas marcas =  new Select()
                    .from(Marcas.class)
                    .where("nombre = ?", (String) marcaProducto.getSelectedItem())
                    .executeSingle();
            productos.setMarca(marcas);
        }
        else{
            //no puede estar vacia
            esValidoProducto = false;
        }


        try{
            productos.setPrecio(BigDecimal.valueOf(
                            Float.valueOf(precioProducto.getText().toString()))
            );
        }
        catch (Exception e) {
            if (precioProducto.getText().toString().isEmpty()){
                precioProducto.setError("No puede estar vacio");
                esValidoProducto = false;
            }
            else {
                precioProducto.setError("No es un Numero");
                esValidoProducto = false;
            }

        }
        if (productos.getPrecio() != null && productos.getPrecio().floatValue() < 0){
            precioProducto.setError("No puede ser negativo");
            esValidoProducto = false;
        }

        if(cantidadProducto.getText().toString().isEmpty()){
            cantidadProducto.setError("no puede estar vacio");
            esValidoProducto = false;

        }
        else if(Integer.valueOf(cantidadProducto.getText().toString()) < 0){
            cantidadProducto.setError("no puede ser negativo");
            esValidoProducto = false;
        }
        else{
            productos.setCantidad(Integer.valueOf(cantidadProducto.getText().toString()));
        }

        Contactos contactos = new Select()
                .from(Contactos.class)
                .where("telefono = ?", contactoProducto.getText().toString())
                .executeSingle();

        productos.setContacto(contactos);

        if (seccionProducto.getSelectedItem() != null){
            Secciones secciones =  new Select()
                    .from(Secciones.class)
                    .where("seccion = ?", (String) seccionProducto.getSelectedItem())
                    .executeSingle();
            productos.setSeccion(secciones);
        }

        if (categoriaProducto.getSelectedItem() != null){
            Categorias categorias = new Select()
                    .from(Categorias.class)
                    .where("categoria = ? ", (String) categoriaProducto.getSelectedItem())
                    .executeSingle();
            productos.setCategoria(categorias);
        }



        if (esValidoProducto) productos.save();

        if (photo != null ){
            File newFile = new File(saveDir+"/"+productos.getId()+".jpg");

            if (newFile.exists())newFile.delete();

            if(photo.renameTo(newFile)){
                productos.setRutaImagen(newFile.getAbsolutePath());
                productos.save();
            }
        }
        switch ((String)tipoProducto.getSelectedItem()){
            case "Zapatos":

                if (idProducto != null) {
                    if (!productos.getTipo().equals("Zapatos")){
                        elemintarTipoProducto(productos.getTipo(),idProducto);
                    }
                }

                Zapatos zapatos = new Zapatos();
                zapatos.setProducto(productos);
                zapatos.setMedida(zapatosMedida.getText().toString());

                if (zapatos.getMedida().isEmpty()){
                    zapatosMedida.setError("No puede estar vacio");
                    esValidoTipoProducto = false;
                }

                if(esValidoTipoProducto && esValidoProducto) zapatos.save();


                break;
            case "Camisa":

                if (idProducto != null) {
                    if (!productos.getTipo().equals("Camisa")){
                        elemintarTipoProducto(productos.getTipo(),idProducto);
                    }
                }

                Camisas camisas =  new Camisas();
                camisas.setProducto(productos);
                camisas.setTamano(tamanoCamisa.getText().toString());


                if (camisas.getTamano().isEmpty()){
                    tamanoCamisa.setError("No puede estar vacio");
                    esValidoTipoProducto = false;
                }

                if (tipoCamisa.getSelectedItem() != null){
                    TipoCamisas tipoCamisas =  new Select()
                            .from(TipoCamisas.class)
                            .where("tipo_camisa = ?", (String) tipoCamisa.getSelectedItem())
                            .executeSingle();
                    camisas.setTipoCamisa(tipoCamisas);
                }
                else{
                    // TODO: 11/18/15
                    esValidoTipoProducto = false;
                }
                if(tipoMangaCamisa.getSelectedItem() != null){
                    TipoMangas tipoMangas =  new Select()
                            .from(TipoMangas.class)
                            .where("tipo_manga = ?", (String) tipoMangaCamisa.getSelectedItem())
                            .executeSingle();
                    camisas.setTipoManga(tipoMangas);
                }
                else {
                    //// TODO: 11/18/15
                    esValidoTipoProducto = false;
                }

                if(esValidoTipoProducto && esValidoProducto) camisas.save();

                break;
            case "Pantalon":

                if (idProducto != null) {
                    if (!productos.getTipo().equals("Pantalon")){
                        elemintarTipoProducto(productos.getTipo(),idProducto);
                    }
                }

                Pantalones pantalones =  new Pantalones();
                pantalones.setProducto(productos);

                try{
                    pantalones.setLargo(Float.valueOf(largoPantalon.getText().toString()));
                }
                catch (Exception e){
                    if(largoPantalon.getText().toString().isEmpty()){
                        largoPantalon.setError("no puedes estar vacio");
                        esValidoTipoProducto = false;
                    }
                    else{
                        largoPantalon.setError("No es un numero");
                        esValidoTipoProducto = false;
                    }
                }

                if (pantalones.getLargo() != null && pantalones.getLargo() < 0){
                    largoPantalon.setError("No puede ser negativo");
                    esValidoTipoProducto = false;
                }

                try{
                    pantalones.setAncho(Float.valueOf(anchoPantalon.getText().toString()));
                }catch (Exception e){
                    if(anchoPantalon.getText().toString().isEmpty()){
                        anchoPantalon.setError("no puedes estar vacio");
                        esValidoTipoProducto = false;
                    }
                    else{
                        anchoPantalon.setError("No es un numero");
                        esValidoTipoProducto = false;
                    }
                }
                if (pantalones.getAncho() != null && pantalones.getAncho() < 0){
                    anchoPantalon.setError("No puede ser negativo");
                    esValidoTipoProducto = false;
                }

                pantalones.setSize(sizePantalon.getText().toString());


                if (pantalones.getSize().isEmpty()){
                    sizePantalon.setError("No puede estar vacio");
                    esValidoTipoProducto = false;
                }

                if(esValidoTipoProducto && esValidoProducto) pantalones.save();

                break;
            case "Ropa Interior":

                if (idProducto != null) {
                    if (!productos.getTipo().equals("Pantalon")){
                        elemintarTipoProducto(productos.getTipo(),idProducto);
                    }
                }

                RopaInterioires ropaInterioires =  new RopaInterioires();
                ropaInterioires.setProducto(productos);

                if (tipoRopaInterior.getSelectedItem() != null){
                    TipoRopaInteriores tipoRopaInteriores =  new Select()
                            .from(TipoRopaInteriores.class)
                            .where("tipo_ropa_interior", (String) tipoRopaInterior.getSelectedItem())
                            .executeSingle();
                    ropaInterioires.setTipoRopaInterior(tipoRopaInteriores);
                }
                else {
                    // TODO: 11/18/15
                    esValidoTipoProducto = false;
                }

                ropaInterioires.setMedida1(medida1RopaInterior.getText().toString());

                if (ropaInterioires.getMedida1().isEmpty()){
                    medida1RopaInterior.setError("No puede estar vacia");
                    esValidoTipoProducto = false;
                }

                ropaInterioires.setMedida2(medida2RopaInterior.getText().toString());

                if (ropaInterioires.getMedida2().isEmpty()){
                    medida2RopaInterior.setError("No puede estar vacia");
                    esValidoTipoProducto = false;
                }
                if(esValidoTipoProducto && esValidoProducto) ropaInterioires.save();
                break;
        }

        if(esValidoTipoProducto && esValidoProducto)  {
            Toast.makeText(this, "Guardado con Exito", Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();

            if(idProducto == null){
                returnIntent.putExtra("id", productos.getId());
            }
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    protected void TomarFotoProducto(){
        Intent cameraInent = new Intent("android.media.action.IMAGE_CAPTURE");
        saveDir = new File(Environment.getExternalStorageDirectory() +"/Foto-Productos");
        if (!saveDir.exists())saveDir.mkdirs();
        photo = new File(saveDir,  "tmp.jpg");
        cameraInent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(cameraInent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
             case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        botonSacarFoto.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        }
    }

    protected void elemintarTipoProducto(String tipo,Long idProd){
        if (tipo == null || idProd == null) return;
        switch (tipo){
            case "Zapatos":
                Zapatos zapatos =  new Select().from(Zapatos.class).where("producto = ? ",idProd).executeSingle();
                zapatos.delete();
                break;
            case "Camisa":
                Camisas camisas =  new Select().from(Camisas.class).where("producto = ? ",idProd).executeSingle();
                camisas.delete();
                break;
            case "Pantalon":
                Pantalones pantalones   = new Select().from(Pantalones.class).where("producto = ? ",idProd).executeSingle();
                pantalones.delete();
                break;
            case "Ropa Interior":
                RopaInterioires ropaInterioires =  new Select().from(RopaInterioires.class).where("producto = ? ",idProd).executeSingle();
                ropaInterioires.delete();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Float pval;
        if (!hasFocus) {
            try {
                pval = Float.valueOf(precioProducto.getText().toString());
            }
            catch (Exception e){ return ;}
            if(precioProducto.getText().toString().matches("\\d+")){
                precioProducto.setText(String.format("%.2f",pval));
            }
            else if(precioProducto.getText().toString().matches("\\d+\\.\\d*")){
                precioProducto.setText(String.format("%.2f",pval));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuProductos = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_generico_editar, menu);
        menuProductos.findItem(R.id.nuevo).setVisible(false);

        if (idProducto == null) {
            menuProductos.findItem(R.id.editar).setVisible(false);
            menuProductos.findItem(R.id.eliminar).setVisible(false);
        }
        cambiarEstadoComponentes();
        return true;
    }
    private void cambiarEstadoComponentes(){

        switch (modo) {
            case Detalle:
                HabilitarEdicion(false);
                break;
            case Editar:
                HabilitarEdicion(true);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                menuProductos.findItem(R.id.editar).setVisible(false);
                modo = Editar;
                cambiarEstadoComponentes();
                return true;
            case R.id.eliminar:
                borrarProducto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void borrarProducto() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.create();

        dialog.setTitle("Eliminar Contacto");

        dialog.setMessage("Esta seguro que desea eliminar este Contacto?");

        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                entidades.Productos prod = new Select()
                        .from(entidades.Productos.class)
                        .where("id = ? ", idProducto.toString())
                        .executeSingle();

                elemintarTipoProducto(prod.getTipo(), prod.getId());
                if (prod.getRutaImagen() != null) {
                    File imagepath = new File(prod.getRutaImagen());
                    imagepath.delete();
                }
                ActiveAndroid.getDatabase().execSQL(String.format("delete from productos where id =%d;",prod.getInternalId()));
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
