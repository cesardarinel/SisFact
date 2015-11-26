package sisfact.sisfac.sisfact.Vistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_ver_productos extends AppCompatActivity implements View.OnClickListener{

    protected GridLayout gridLayout;
    protected ViewGroup.LayoutParams params;
    protected ViewGroup.LayoutParams imageParams;
    protected ViewGroup.LayoutParams labelParams;
    private  Intent intt;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_todos_los_productos);

        List<Productos> productosList =  new Select().from(Productos.class).execute();
        gridLayout =(GridLayout) findViewById(R.id.ver_album);
        imageParams = new ViewGroup.LayoutParams(300,200);
        labelParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params = new ViewGroup.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);

       for (Productos var : productosList){
            LinearLayout linearLayout = new LinearLayout(this);
           linearLayout.setOrientation(LinearLayout.VERTICAL);
           ImageView imageButton = new ImageView(this);
            TextView textView = new TextView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(var.getRutaImagen());

            if (bitmap == null){
                bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.camera);
                imageButton.setImageBitmap(bitmap);
            }
            else imageButton.setImageBitmap(bitmap);

           textView.setText(var.getNombre());

           imageButton.setLayoutParams(imageParams);
           imageButton.setTag(var.getId());


           textView.setLayoutParams(labelParams);
           imageButton.setOnClickListener(this);

           linearLayout.setLayoutParams(params);
           linearLayout.addView(imageButton);
           linearLayout.addView(textView);

           gridLayout.addView(linearLayout);
       }

    }

    @Override
    public void onClick(View v) {

        final Productos prod = new Select().from(Productos.class).where("id = ?",v.getTag().toString()).executeSingle();
        setContentView(R.layout.ver_producto_individual);

        TextView titulo = (TextView) findViewById(R.id.producto_individual_nombre);
        TextView precio = (TextView) findViewById(R.id.producto_individual_precio);
        ImageView imagen = (ImageView) findViewById(R.id.producto_individual_imagen);

        Button butt = (Button) findViewById(R.id.producto_individual_ver_detalles);
        titulo.setText(prod.getNombre());
        precio.setText(String.format("%.2f", Float.valueOf(prod.getPrecio().toString())));
        Bitmap bitmap = BitmapFactory.decodeFile(prod.getRutaImagen());

        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.camera);
        }

        imagen.setImageBitmap(bitmap);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + prod.getRutaImagen()), "image/*");
                startActivity(intent);
            }
        });
        intt = new Intent(this, sisfact.sisfac.sisfact.Vistas.Productos.class);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intt.putExtra("id",prod.getId());
                startActivity(intt);
            }
        });
    }
}
