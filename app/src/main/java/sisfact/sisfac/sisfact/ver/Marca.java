package sisfact.sisfac.sisfact.ver;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import entidades.Interface.GenericListView;
import entidades.Marcas;
import sisfact.sisfac.sisfact.R;


public class Marca extends AppCompatActivity {

    private  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_marcas);
        listView = (ListView) findViewById(R.id.ver_marca_lsvw);

        ArrayList<GenericListView> listgen  ;
        listgen =  getIntent().getParcelableArrayListExtra("listaDeObjetos");

        List<String> m  =new ArrayList<>();

        for (GenericListView item : listgen)m.add(item.getItemListName());

        ArrayAdapter<String> lel =  new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                m
        );

        listView.setAdapter(lel);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Hacer
                finish();
            }
        });
    }
}
