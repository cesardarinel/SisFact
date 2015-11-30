package entidades;

import java.io.Serializable;

public class ItemLista implements Serializable {
    protected String id;
    protected String info;
    protected String texto1;
    protected String texto2;

    public ItemLista(){}

    public ItemLista(String ID, String Info, String Texto1, String texto2) {
        this.setId(ID);
        this.setInfo(Info);
        this.setTexto1(Texto1);
        this.setTexto2(texto2);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTexto1() {
        return texto1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }

    public String getTexto2() {
        return texto2;
    }

    public void setTexto2(String texto2) {
        this.texto2 = texto2;
    }
}