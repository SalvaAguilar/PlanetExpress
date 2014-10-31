package silverhillapps.com.planetexpress.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.ObjectGraph;
import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.loader.PackagingRepository;
import silverhillapps.com.planetexpress.loader.PackagingRepositoryModule;
import silverhillapps.com.planetexpress.loader.RepositoryReceiver;
import silverhillapps.com.planetexpress.model.*;
import silverhillapps.com.planetexpress.utils.ConnectionUtils;

/**
 * This class is in charge of the data loading, separating the data loading completely from the view controller.
 */
public class LoaderManagerFacade implements ManagerInterface{

    @Inject PackagingRepository repository;
    @Inject ConversionManager conversionManager;

    private List<PackageUnit> packageList;
    private Map<String,CountryResult> countryResultList;

    public LoaderManagerFacade(){
        this.packageList = new ArrayList<PackageUnit>();
        this.countryResultList = new HashMap<String, CountryResult>();
    }

    /**
     * This method retrieves the package list
     * @param receiver
     * @return
     */
    @Override
    public List<Package> getCountryResultList(RepositoryReceiver receiver) {

        ObjectGraph graph = ObjectGraph.create(new PackagingRepositoryModule(),new ConversionManagerModule());
        graph.inject(this);

        List<Package> packageList = new ArrayList<Package>();

        //We fire the network load if the connection is available. Another conditions can be set here
        if(ConnectionUtils.isNetworkAvailable()) {
            repository.getPackageList(receiver, this); //This method performs the calculation if there is any connection in the device
            repository.getConversionList(receiver, this);
        }

        return packageList;
    }

    /**
     * Method in charge of collecting the results from the low level repository. It manages the persistence and postprocessing of the data obtained.
     * @param result
     * @param code
     * @param receiver
     */
    @Override
    public void getResults(final Object result, int code, final RepositoryReceiver receiver) {

        switch(code){
            case Constants.PassingParamConstants.REST_RETURN_LIST_PACKAGES:

                new Thread() {
                    public void run() {
                        packageList = (List<PackageUnit>) result;
                        processData(receiver);
                    }
                }.start();


                break;
            case Constants.PassingParamConstants.REST_RETURN_LIST_CONVERSION:

                new Thread() {
                    public void run() {
                        conversionManager.setConversionList((List<Conversion>) result);
                        processData(receiver);
                    }
                }.start();


                break;
        }

    }


    /**
     * Method that translate the server model to the app client model that will be used in the views.
     * @param receiver
     */
    private void processData(RepositoryReceiver receiver){

        if(packageList!=null){
            if(packageList.size()>0 && !conversionManager.isEmpty()){

                for(PackageUnit p: packageList){
                    if(countryResultList.containsKey(p.getDestinationCode())){
                        CountryResult cr = countryResultList.get(p.getDestinationCode());
                        insertPackageIntoCountry(p, cr);
                    }else{
                        CountryResult cr = new CountryResult();
                        cr.setCountryCode(p.getDestinationCode());
                        insertPackageIntoCountry(p, cr);
                        countryResultList.put(p.getDestinationCode(),cr);
                    }
                }

                receiver.getResults(countryResultList, Constants.PassingParamConstants.REST_RETURN_LIST_PACKAGES);
            }
        }
    }

    /**
     * This method adds a packageUnit into a CountryResult
     * @param p
     * @param cr
     */
    private void insertPackageIntoCountry(PackageUnit p, CountryResult cr) {
        if(conversionManager.correctUnitsPackage(p)!=null){
            cr.includePackageUnit(p);
        }else{
            //TODO manage the error
        }
    }

    /**
     * This method clean all the structures used in the manager.
     */
    public void clean(){
        this.countryResultList = new HashMap<String, CountryResult>();
        this.packageList = new ArrayList<PackageUnit>();
        conversionManager.clean();
    }

}
