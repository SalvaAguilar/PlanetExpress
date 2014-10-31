package silverhillapps.com.planetexpress.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import silverhillapps.com.planetexpress.SHPEApplication;

/**
 * Class with some util methods for evaluating the connectivity of the device
 */
public class ConnectionUtils {

    /**
     * This static method checks if the device has a valid internet connection.
     * @return
     */
    public static boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager
                = (ConnectivityManager) SHPEApplication.getAppContext().getSystemService(SHPEApplication.getAppContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
