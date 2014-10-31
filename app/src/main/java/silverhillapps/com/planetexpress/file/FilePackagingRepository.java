package silverhillapps.com.planetexpress.file;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import silverhillapps.com.planetexpress.SHPEApplication;
import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.loader.PackagingRepository;
import silverhillapps.com.planetexpress.loader.RepositoryReceiver;
import silverhillapps.com.planetexpress.manager.ManagerInterface;
import silverhillapps.com.planetexpress.model.Conversion;
import silverhillapps.com.planetexpress.model.PackageUnit;
import silverhillapps.com.planetexpress.rest.PackagingRestClient;

/**
 * This is the file loader to the repository. It is used for testing the behaviour of the measure unit converter.
 * @author salva
 *
 */
public class FilePackagingRepository extends PackagingRepository{

    @Inject Gson g;


    public FilePackagingRepository(){

        ((SHPEApplication)SHPEApplication.getAppContext()).getObjectGraph().inject(this);

    }

    /**
     * This method obtains the package list
     * @param receiver
     * @param manager
     */
    @Override
    public void getPackageList(final RepositoryReceiver receiver, final ManagerInterface manager) {


        Runnable r = new Runnable() {
            @Override
            public void run() {

                String str = loadJSONFromAsset(Constants.FileRepositoryConstants.PACKAGING_PACKAGES_URL);

                List<PackageUnit> packageUnitList = parseJsonPackage(str);


                manager.getResults(packageUnitList, Constants.PassingParamConstants.REST_RETURN_LIST_PACKAGES, receiver);

            }
        };

        new Thread(r).start(); // we run the method in a separate thread

    }





    /**
     * This method obtains the conversion list
     * @param receiver
     * @param manager
     */
    @Override
    public void getConversionList(final RepositoryReceiver receiver, final ManagerInterface manager) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                String str = loadJSONFromAsset(Constants.FileRepositoryConstants.PACKAGING_CONVERSION_URL);

                List<Conversion> conversionList = parseJsonConversion(str);


                manager.getResults(conversionList, Constants.PassingParamConstants.REST_RETURN_LIST_CONVERSION, receiver);

            }
        };

        new Thread(r).start();


    }


    // parser methods

    /**
     * This method parses the http response in order to create the list of model objects. Package
     * @param response
     * @return
     */
    private List<PackageUnit> parseJsonPackage(String response) {
        List<PackageUnit> packageUnitList = new ArrayList<PackageUnit>();
        packageUnitList = g.fromJson(response, new TypeToken<List<PackageUnit>>(){}.getType());
        return packageUnitList;
    }


    /**
     * This method parses the http response in order to create the list of model objects. Conversion
     * @param response
     * @return
     */
    private List<Conversion> parseJsonConversion(String response) {
        List<Conversion> conversionList = new ArrayList<Conversion>();
        conversionList = g.fromJson(response, new TypeToken<List<Conversion>>(){}.getType());
        return conversionList;
    }


    /**
     * Internal method for reading the json file located in assets.
     * @return the json contents
     */
    private String loadJSONFromAsset(String file) {
        String json = null;
        try {

            InputStream is = SHPEApplication.getAppContext().getAssets().open(file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}