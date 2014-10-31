package silverhillapps.com.planetexpress;

import android.app.Application;
import android.content.Context;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import silverhillapps.com.planetexpress.loader.PackagingRepositoryModule;
import silverhillapps.com.planetexpress.manager.ConversionManagerModule;
import silverhillapps.com.planetexpress.manager.ManagerModule;

/**
 * Application class for wrapping global runtime variables
 * @author salva
 *
 */
public class SHPEApplication extends Application {

    private static Context mContext; // Application context for assets loading

    private ObjectGraph objectGraph;

    public void onCreate(){
        super.onCreate();
        SHPEApplication.mContext = getApplicationContext();
        Object[] modules = getModules().toArray();
        objectGraph = ObjectGraph.create(modules);

    }

    /**
     * Method which returns the application context
     * @return the application context
     */
    public static Context getAppContext() {
        return SHPEApplication.mContext;
    }


    /**
     * Return the modules that are going to be injected through this app.
     * @return
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                 new ManagerModule(), new ConversionManagerModule(), new PackagingRepositoryModule()

        );
    }

    public ObjectGraph getObjectGraph() {
        return this.objectGraph;
    }

}