package entidades.ActiveAndroid;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;


public class init extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

    }
}