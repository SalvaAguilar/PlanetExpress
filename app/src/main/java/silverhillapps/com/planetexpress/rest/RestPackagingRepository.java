package silverhillapps.com.planetexpress.rest;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import silverhillapps.com.planetexpress.SHPEApplication;
import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.loader.PackagingRepositoryModule;
import silverhillapps.com.planetexpress.manager.ConversionManagerModule;
import silverhillapps.com.planetexpress.manager.ManagerInterface;
import silverhillapps.com.planetexpress.model.Conversion;
import silverhillapps.com.planetexpress.model.PackageUnit;
import silverhillapps.com.planetexpress.loader.PackagingRepository;
import silverhillapps.com.planetexpress.loader.RepositoryReceiver;

/**
 * This is the rest loader. it generates the http calls to rest api for any of the methods needed in the app.
 * It uses http-request library.
 * @author salva
 *
 */
public class RestPackagingRepository extends PackagingRepository{

    @Inject Gson g;


    public RestPackagingRepository(){


        ((SHPEApplication)SHPEApplication.getAppContext()).getObjectGraph().inject(this);

    }

    /**
     * This method obtains the package list through the rest endpoint
     * @param receiver
     * @param manager
     */
    @Override
    public void getPackageList(final RepositoryReceiver receiver, final ManagerInterface manager) {

        PackagingRestClient.get(Constants.RestApiConstants.PACKAGING_LIST_METHOD, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {

                       Runnable r = new Runnable() {
                           @Override
                           public void run() {
                               String str = new String(responseBody);

                               List<PackageUnit> packageUnitList = parseJsonPackage(str);


                               manager.getResults(packageUnitList, Constants.PassingParamConstants.REST_RETURN_LIST_PACKAGES, receiver);
                           }
                       };

                        Thread t = new Thread(r);
                        t.start(); // As the data parsing is complex, we need to run it in a new Thread, in order to not saturate the ui thread.

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //TODO manage the error in the connection
                    }

                }
        );
    }

    /**
     * This method obtains the conversion list through the rest endpoint
     * @param receiver
     * @param manager
     */
    @Override
    public void getConversionList(final RepositoryReceiver receiver, final ManagerInterface manager) {

        PackagingRestClient.get(Constants.RestApiConstants.CONVERSION_LIST_METHOD, null,

                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,final byte[] responseBody) {


                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                String str = new String(responseBody);

                                List<Conversion> conversionList = parseJsonConversion(str);


                                manager.getResults(conversionList, Constants.PassingParamConstants.REST_RETURN_LIST_CONVERSION, receiver);
                            }
                        };

                        Thread t = new Thread(r);
                        t.start(); // As the data parsing is complex, we need to run it in a new Thread, in order to not saturate the ui thread.


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //TODO manage the error in the connection
                    }

                }
        );
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

}