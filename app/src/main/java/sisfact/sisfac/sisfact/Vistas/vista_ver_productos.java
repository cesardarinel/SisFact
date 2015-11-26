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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import entidades.Productos;
import sisfact.sisfac.sisfact.R;

public class vista_ver_productos extends AppCompatActivity implements View.OnClickListener{

    protected GridLayout gridLayout;
    protected ViewGroup.LayoutParams params;
    protected ViewGroup.LayoutParams imageParams;
    protected ViewGroup.LayoutParams labelParams;
    private  Intent intt;
    Long id;
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
           BitmapFactory.Options opt = new BitmapFactory.Options();

           Bitmap bitmap = BitmapFactory.decodeFile(var.getRutaImagen(),opt);
           ImageLoader img = ImageLoader.getInstance();
           if (bitmap == null){
               imageButton.setImageResource(R.drawable.camera);
           }
           else img.displayImage("file:///" + var.getRutaImagen(), imageButton);

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
        Intent intent = new Intent(this,detalle_producto_simple.class);
        intent.putExtra("id",v.getTag().toString());
        startActivity(intent);
        finish();
    }
}


