package sisfact.sisfac.sisfact.ver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import entidades.FacturaProductos;
import entidades.Facturas;
import sisfact.sisfac.sisfact.R;


public class CuentasPorCobrar extends AppCompatActivity implements View.OnClickListener{

    protected Button volver;
    protected TableLayout tabla;
    protected TextView Pagado;
    protected TextView PagadoValor;
    protected TextView Adeudado;
    protected TextView AdeudadoValor;

    protected ArrayList <entidades.CuentasPorCobrar> historialDeCuenta;

    protected TableRow.LayoutParams layoutPago;
    protected TableRow.LayoutParams layoutFecha;
    protected TableRow.LayoutParams layoutEsPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Facturas fact = getIntent().getParcelableExtra("factura");
        setContentView(R.layout.layout_cuentas_por_cobrar_list_view_adaptor);

        volver = (Button) findViewById(R.id.cuentas_por_cobrar_btn_volver);
        tabla = (TableLayout) findViewById(R.id.cuentas_por_cobrar_tabla);

        Pagado = (TextView) findViewById(R.id.cuentas_por_cobrar_texto_pagado);
        PagadoValor = (TextView) findViewById(R.id.cuentas_por_cobrar_texto_pagado_valor);

        Adeudado = (TextView) findViewById(R.id.cuentas_por_cobrar_texto_adeudado);
        AdeudadoValor = (TextView) findViewById(R.id.cuentas_por_cobrar_texto_adeudado_valor);


        layoutPago =  new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1f);
        layoutFecha =  new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1f);
        layoutEsPago =  new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1f);

        if (fact != null){
            historialDeCuenta = new Select()
                    .from(entidades.CuentasPorCobrar.class)
                    .where("factura = ? ",fact.getId())
                    .orderBy("fecha_pagada ASC").execute();
            Float totalPagado = 0f;
            Float totalAdeudado = 0f;
            ArrayList<FacturaProductos> itemesDeCompra = new Select()
                                                        .from(entidades.FacturaProductos.class)
                                                        .where("factura = ? ",fact.getId()).execute();
            for (entidades.FacturaProductos item : itemesDeCompra){
                totalAdeudado += item.getPrecioHistorico();
            }

            for(entidades.CuentasPorCobrar item : historialDeCuenta){
                totalPagado += item.getPago();

                TextView viewPago =  new TextView(this);
                viewPago.setGravity(Gravity.START);


                TextView viewFecha =  new TextView(this);

                TextView viewEsPago =  new TextView(this);
                viewEsPago.setGravity(Gravity.END);

                viewPago.setText(item.getPago().toString());
                viewFecha.setText(item.getFechaPagada().toString());

                if (item.isEsPagado()){
                    viewEsPago.setText("Si");
                }
                else{
                    viewEsPago.setText("No");
                }
                TableRow fila = new TableRow(this);

                fila.setLayoutParams(
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                );

                fila.addView(viewPago);
                fila.addView(viewFecha);
                fila.addView(viewEsPago);

                totalAdeudado = totalAdeudado -  totalPagado;
                tabla.addView(fila);
                if (totalAdeudado != 0){
                    Adeudado.setVisibility(View.VISIBLE);
                    AdeudadoValor.setVisibility(View.VISIBLE);
                    AdeudadoValor.setText(totalAdeudado.toString());
                }
                if (totalPagado != 0){
                    Pagado.setVisibility(View.VISIBLE);
                    PagadoValor.setVisibility(View.VISIBLE);
                    PagadoValor.setText(totalPagado.toString());
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
