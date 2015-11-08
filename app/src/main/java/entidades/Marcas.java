package entidades;


import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entidades.Interface.GenericListView;

@Table(name="marcas")
public class Marcas extends Model implements GenericListView {
    @Column(name="nombre")
    private String nombre;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public  Marcas(){}
    //Necesario para leer los datos del parcel
    public Marcas(Parcel in){
        nombre = in.readString();
    }

    @Override
    public String getItemListName() {
        return nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //este metodo es para escribir los datos
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Nota en el mismo orden que en el contructor
        dest.writeString(nombre);
    }
    //obligatoria para que llene los objetos del parecel que se vatan a crear
    public static final Parcelable.Creator<Marcas> CREATOR =  new Parcelable.Creator<Marcas>(){

        @Override
        public Marcas createFromParcel(Parcel source) {
            return new Marcas(source);
        }

        @Override
        public Marcas [] newArray(int size) {
            return new Marcas[size ];
        }
    };
}
