package silverhillapps.com.planetexpress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.ObjectGraph;
import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.fragments.CountriesFragment;
import silverhillapps.com.planetexpress.fragments.ItemSelectedListener;
import silverhillapps.com.planetexpress.loader.RepositoryReceiver;
import silverhillapps.com.planetexpress.manager.ManagerInterface;
import silverhillapps.com.planetexpress.manager.ManagerModule;
import silverhillapps.com.planetexpress.model.CountryResult;


/**
 * This is the main activity of the app, where the list of all the countries where we need to send packages
 * are shown in a list through a fragment.
 */
public class CountriesActivity extends ActionBarActivity implements ItemSelectedListener, RepositoryReceiver {

    @Inject
    ManagerInterface mManager;

    private CountriesFragment mFragment;

    private Map<String,CountryResult> mCountries;

    private boolean isLoading = false; //Determines whether the app is loading data

    //The injection graph
    private ObjectGraph mGraph;

    private ProgressDialog mDialog; //Loading dialog.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_countries);

        mGraph = ObjectGraph.create(new ManagerModule());
        mGraph.inject(this);

        mFragment = new CountriesFragment();


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();

        createDialog();
        mDialog.show();

        // We fire the data retrieval from the repository
        mManager.getCountryResultList(this);
        isLoading = true;

    }

    /**
     * Method used for creating the dialog used for notifying the user that the data is being processed.
     */
    private void createDialog() {

        this.mDialog = new ProgressDialog(this);
        this.mDialog.setTitle(getResources().getString(R.string.text_dialog_processing));
        this.mDialog.setMessage(getResources().getString(R.string.subtext_dialog_wait));
        this.mDialog.setCancelable(false);
        this.mDialog.setIndeterminate(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //We complete the action of reloading data.
        if (id == R.id.action_reload) {
            if(!isLoading){

                //We delete the results
                List<String> listKeys = new ArrayList<String>();
                mFragment.updateResults(listKeys);

                mManager.clean();
                mManager.getCountryResultList(this); // Update the data through the manager and showing the dialog.
                isLoading = true;
                mDialog.show();


            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // Methods from ItemSelectedListener interface
    @Override
    public void onItemSelected(String country) {
        /*Here we have two options depending on the final device. We can use the fragment manager to show
		the two fragments in the same activity like master-detail or like this case, fire a new activity
		that contains the detail fragment for smartphones. */


        Intent detailActivityIntent = new Intent(CountriesActivity.this, DetailActivity.class);
        detailActivityIntent.putExtra(Constants.PassingParamConstants.ACTIVITY_PASS_PACKAGE, mCountries.get(country));

        startActivity(detailActivityIntent);
    }

    // Repository Receiver methods
    @Override
    public void getResults(final Object result, final int code) {
        runOnUiThread(new Runnable() {//We could use a handler to notify the ui thread as well
            @Override
            public void run() {

                mDialog.dismiss();
                isLoading = false;
                if(mFragment!=null){

                    switch(code){
                        case Constants.PassingParamConstants.REST_RETURN_LIST_PACKAGES:
                            mCountries = (Map<String, CountryResult>)result;
                            List<String> listKeys = new ArrayList<String>(mCountries.keySet());
                            mFragment.updateResults(listKeys);
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void getError(int code) {
        runOnUiThread(new Runnable() { //We could use a handler to notify the ui thread as well
                          @Override
                          public void run() {

                              isLoading = false;
                              mDialog.dismiss();
                          }
                      });

    }


    //Getters and setters


    public boolean isLoading() {
        return isLoading;
    }

}
