package pe.javapetit.apps.porlosNinos;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 14/10/13
 * Time: 09:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.

        context = getApplicationContext();
    }

    public static String getApplicationResourceRoot() {

        return Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName();

    }
}
