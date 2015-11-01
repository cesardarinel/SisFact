package sisfact.sisfac.sisfact.ver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import sisfact.sisfac.sisfact.R;


public class Marca extends AppCompatActivity {

    private  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_marcas);
        //llenar la lista
    }
}
