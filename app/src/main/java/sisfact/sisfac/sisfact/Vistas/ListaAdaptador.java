package sisfact.sisfac.sisfact.Vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import entidades.ItemLista;
import sisfact.sisfac.sisfact.R;

/**
 * Created by cesar on 11/12/2015.
 */
public class ListaAdaptador extends ArrayAdapter<ItemLista> {
    //
    private Context context;
    private ArrayList<ItemLista> datos;

    /**
     *
     * @param context
     * @param datos
     */
    public ListaAdaptador(Context context, ArrayList<ItemLista> datos) {
        super(context, R.layout.listado_vista_generic, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = LayoutInflater.from(context).inflate(R.layout.listado_vista_generic,null);


        TextView ID = (TextView) item.findViewById(R.id.id);
        ID.setText(datos.get(position).getId());

        TextView Tipo = (TextView) item.findViewById(R.id.texto2);
        Tipo.setText(datos.get(position).getTexto2());

        TextView Zona = (TextView) item.findViewById(R.id.texto1);
        Zona.setText(datos.get(position).getTexto1());

        TextView Desc = (TextView) item.findViewById(R.id.info);
        Desc.setText(datos.get(position).getInfo());

        TextView info2 = (TextView) item.findViewById(R.id.info2);
        info2.setText(datos.get(position).getInfo2());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }


}
