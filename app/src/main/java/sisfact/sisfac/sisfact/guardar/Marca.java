    package sisfact.sisfac.sisfact.guardar;

    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.widget.Button;
    import android.widget.EditText;
    import android.view.View;
    import android.widget.Toast;

    import entidades.Marcas;
    import sisfact.sisfac.sisfact.R;


    public class Marca extends AppCompatActivity implements View.OnClickListener{

        protected EditText nombreMarca;
        protected Button botonGuardar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ver_marcas);
            nombreMarca = (EditText) findViewById(R.id.guardar_marca_nombre_marca);
            botonGuardar = (Button) findViewById(R.id.guardar_marca_btn_guardar);
            botonGuardar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Marcas marca = new Marcas();
            marca.setNombre(nombreMarca.getText().toString());
            try {
                marca.save();
                Toast.makeText(this,"Guardado con Exito",Toast.LENGTH_SHORT).show();
                finish();

            }
            catch (Exception e){
                //// TODO: 11/4/2015  
            }
            //posibement volver al menu
        }
    }
