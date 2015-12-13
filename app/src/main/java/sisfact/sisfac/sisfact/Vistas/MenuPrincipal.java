package sisfact.sisfac.sisfact.Vistas;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Locale;

import sisfact.sisfac.sisfact.R;

public class MenuPrincipal extends AppCompatActivity {


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("es","US");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


    }


}
