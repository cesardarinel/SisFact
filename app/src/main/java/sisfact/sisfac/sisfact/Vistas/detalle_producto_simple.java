package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.nostra13.universalimageloader.core.ImageLoader;

import sisfact.sisfac.sisfact.R;

public class detalle_producto_simple extends AppCompatActivity implements View.OnClickListener{

    protected  entidades.Productos prod;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {


        super.onCreate(savedInstanceState);
        String id = null;
        try{
            id = getIntent().getExtras().getString("id");
        }catch (Exception e){}

        if (id == null) finish();

        prod = new Select()
                .from(entidades.Productos.class)
                .where("id = ?",id)
                .executeSingle();
        setContentView(R.layout.ver_producto_individual);

        TextView titulo = (TextView) findViewById(R.id.producto_individual_nombre);
        TextView precio = (TextView) findViewById(R.id.producto_individual_precio);
        ImageView imagen = (ImageView) findViewById(R.id.producto_individual_imagen);

        Button butt = (Button) findViewById(R.id.producto_individual_ver_detalles);
        titulo.setText(prod.getNombre());
        precio.setText(String.format("%.2f", Float.valueOf(prod.getPrecio().toString())));
        ImageLoader img = ImageLoader.getInstance();
        if(prod.getRutaImagen() == null){
            imagen.setImageResource(R.drawable.camera);
        }
        else img.displayImage("file:///"+prod.getRutaImagen(),imagen);



        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + prod.getRutaImagen()), "image/*");
                startActivity(intent);
            }
        });
        butt.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, sisfact.sisfac.sisfact.Vistas.Productos.class);
        intent.putExtra("id",prod.getId().toString());
        startActivity(intent);
    }
}
