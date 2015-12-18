package sisfact.sisfac.sisfact.Vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.activeandroid.ActiveAndroid;

import com.activeandroid.query.Select;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import entidades.Contactos;
import entidades.CuentasPorCobrar;
import entidades.CuentasPorPagar;
import sisfact.sisfac.sisfact.R;

public class vista_reporte extends AppCompatActivity implements View.OnClickListener {

    final Context dialog = this;
    private Button boton_dialog_facturas;
    private Button boton_dialog_contactos;
    private Button boton_dialog_cuentaCobrar;
    private Button boton_dialog_cuentaPagar;
    private RadioButton boton_radio_pdf;
    private RadioButton boton_radio_excel;
    private LayoutInflater lo;
    private View vistaDialog;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_reporte);

        //Creando ventana
        boton_dialog_facturas  = (Button) findViewById(R.id.vista_reporte_btn_factura);
        boton_dialog_contactos = (Button) findViewById(R.id.vista_reporte_btn_contacto);
        boton_dialog_cuentaCobrar = (Button) findViewById(R.id.vista_reporte_btn_cuentasCobrar);
        boton_dialog_cuentaPagar = (Button) findViewById(R.id.vista_reporte_btn_cuentasPagar);
        //boton_radio_pdf = new RadioButton(dialog);
        //boton_radio_pdf = (RadioButton) findViewById(R.id.dialog_rbtn_pdf);
        //boton_radio_excel = (RadioButton) findViewById(R.id.dialog_rbtn_excel);
        boton_dialog_facturas.setVisibility(View.GONE);
        boton_dialog_facturas.setOnClickListener(this);
        boton_dialog_contactos.setOnClickListener(this);
        boton_dialog_cuentaCobrar.setOnClickListener(this);
        boton_dialog_cuentaPagar.setOnClickListener(this);

    }

    /**
     *
     * @param tipo
     */
    public void crearVentana(final String tipo){

        final RadioButton pdf = new RadioButton(dialog);
        pdf.setText("PDF");
        lo = LayoutInflater.from(dialog);
        vistaDialog = lo.inflate(R.layout.vista_reportes_opciones_dialog, null);
        alerta = new AlertDialog.Builder(dialog);
        alerta.setView(vistaDialog);
        alerta.setView(pdf);
        alerta.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface vistaOpcion, int id) {
                vistaOpcion.cancel();
            }
        });
        System.out.println("TIpooooooooooo" + tipo);
        alerta.setPositiveButton("Generar Reporte", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface vistaOpcion, int id) {
                Date fecha = new Date();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", new Locale("es","ES"));
                System.out.println("la fechaaaaa" + fecha.toString());
                if (pdf.isChecked()) {
                    if (tipo == "contacto") {
                        String path = "/reportes/contactos" +dateFormatter.format(new Date()) + ".pdf";
                        ActiveAndroid.getDatabase();
                        try {
                            crearPDFcontacto(creadoArchivo(path));
                        } catch (Exception e) {

                        }
                    }

                    if (tipo == "cuentasCobrar") {
                        try {
                            String path = "/reportes/cuentasPorCobrar"+ dateFormatter.format(new Date()) + ".pdf";
                            crearPDFCuentaCobrar(creadoArchivo(path));
                        } catch (Exception e) {

                        }
                    }

                    if (tipo == "cuentasPagar") {
                        try {
                            String path = "/reportes/cuentasPorPagar"+ dateFormatter.format(new Date()) + ".pdf";
                            crearPDFCuentaPagar(creadoArchivo(path));
                        } catch (Exception e) {

                        }
                    }

                }

            }

        });

        AlertDialog alertDialog = alerta.create();
        // show it
        alertDialog.show();

    }

    public String creadoArchivo(String path){
        ActiveAndroid.getDatabase();
        String fpath = "";
        try {
            File file = new File(Environment.getExternalStorageDirectory() + path);
            file.getParentFile().mkdirs();
            fpath = file.getAbsolutePath();
            System.out.println("Se crea PDF" + file.getCanonicalPath());
        }catch(Exception e){

        }
        return fpath;
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vista_reporte, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.vista_reporte_btn_factura:
                crearVentana("factura");
                break;
            case R.id.vista_reporte_btn_contacto:
                crearVentana("contacto");
                break;
            case R.id.vista_reporte_btn_cuentasCobrar:
                crearVentana("cuentasCobrar");
                break;
            case R.id.vista_reporte_btn_cuentasPagar:
                crearVentana("cuentasPagar");
                break;

            default:
                break;
        }
    }

    public void crearPDFcontacto(String path){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Font f20 = new Font(Font.FontFamily.COURIER,20);
            f20.setStyle(Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Contactos" + "\n" + "MIGLEZ" + "\n\n",f20);
            Chunk line = new Chunk(new LineSeparator());
            titulo.setAlignment(Element.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(7);
            table.addCell("Nombre");
            table.addCell("Apellido");
            table.addCell("Telefono");
            table.addCell("Celular");
            table.addCell("Correo");
            table.addCell("Suplidor");
            table.addCell("Cliente");
            ArrayList<Contactos> conctactoLista = obtenerContactos();
            for(int i=0; i < conctactoLista.size();i++){
                table.addCell(conctactoLista.get(i).getNombre().toString());
                table.addCell(conctactoLista.get(i).getApellido().toString());
                table.addCell(conctactoLista.get(i).getTelefono().toString());
                table.addCell(conctactoLista.get(i).getCelular().toString());
                table.addCell(conctactoLista.get(i).getCorreo().toString());
                if(conctactoLista.get(i).isEsSuplidor()){
                    table.addCell("Es Suplidor");
                }else{
                    table.addCell("N/A");
                }
                if(conctactoLista.get(i).isEsCliente()){
                    table.addCell("Es Cliente");
                }else {
                    table.addCell("N/A");
                }

            }
            document.add(titulo);
            document.add(line);
            document.add(table);
            document.close();
            System.out.println("Este es documento" + document);

        }catch(Exception e){

        }
    }

    public void crearPDFCuentaCobrar(String path){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Font f20 = new Font(Font.FontFamily.COURIER,20);
            f20.setStyle(Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Cuentas Por Cobrar" + "\n" + "MIGLEZ" + "\n\n",f20);
            Chunk line = new Chunk(new LineSeparator());
            titulo.setAlignment(Element.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(7);
            table.addCell("No. Factura");
            table.addCell("Fecha Creada");
            table.addCell("Monto");
            table.addCell("Saldada");
            ArrayList<CuentasPorCobrar> cobrarLista = obtenerCuentaCobrar();
            for(int i=0; i < cobrarLista.size();i++){
                table.addCell(cobrarLista.get(i).getFactura().getInternalId().toString());
                table.addCell(cobrarLista.get(i).getFechaCreada().toString());
                table.addCell(cobrarLista.get(i).getMonto().toString());
                if(cobrarLista.get(i).EstaPagado()){
                    table.addCell("Si");
                }else{
                    table.addCell("No");
                }

            }
            document.add(titulo);
            document.add(line);
            document.add(table);
            document.close();
            System.out.println("Este es documento" + document);
        }catch(Exception e){

        }
    }

    public void crearPDFCuentaPagar(String path){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Font f20 = new Font(Font.FontFamily.COURIER,20);
            f20.setStyle(Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Cuentas Por Pagar" + "\n" + "MIGLEZ" + "\n\n",f20);
            Chunk line = new Chunk(new LineSeparator());
            titulo.setAlignment(Element.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(7);
            table.addCell("Contacto");
            table.addCell("Fecha Creada");
            table.addCell("Monto");
            table.addCell("Saldada");
            ArrayList<CuentasPorPagar> cobrarLista = obtenerCuentasPagar();
            for(int i=0; i < cobrarLista.size();i++){
                table.addCell(cobrarLista.get(i).getContacto().getNombre() + " " + cobrarLista.get(i).getContacto().getApellido());
                table.addCell(cobrarLista.get(i).getFechaCreada().toString());
                table.addCell(cobrarLista.get(i).getMonto().toString());
                if(cobrarLista.get(i).EstaPagado()){
                    table.addCell("Si");
                }else{
                    table.addCell("No");
                }

            }
            document.add(titulo);
            document.add(line);
            document.add(table);
            document.close();
            System.out.println("Este es documento" + document);
        }catch(Exception e){

        }

    }

    public ArrayList<Contactos> obtenerContactos(){
        ArrayList<Contactos> templist = new ArrayList<>();
        List<Contactos> conlist = new Select().from(Contactos.class).execute();
        templist.addAll(conlist);
        return templist;
    }

    public ArrayList<CuentasPorCobrar> obtenerCuentaCobrar(){
        ArrayList<CuentasPorCobrar> templist = new ArrayList<>();
        List<CuentasPorCobrar> conlist = new Select().from(CuentasPorCobrar.class).execute();
        templist.addAll(conlist);
        return templist;
    }

    public ArrayList<CuentasPorPagar> obtenerCuentasPagar(){
        ArrayList<CuentasPorPagar> templist = new ArrayList<>();
        List<CuentasPorPagar> conlist = new Select().from(CuentasPorPagar.class).execute();
        templist.addAll(conlist);
        return templist;
    }




}
