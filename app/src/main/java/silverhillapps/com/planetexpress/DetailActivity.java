package silverhillapps.com.planetexpress;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.fragments.DetailFragment;
import silverhillapps.com.planetexpress.model.CountryResult;


/**
 * This Activity shows the detail of a country selected from the @CountriesActivity
 */
public class DetailActivity extends ActionBarActivity {

    private CountryResult mCountryResult;

    private DetailFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // We retrieve the parameters from parent activity
        mCountryResult = getIntent().getExtras().getParcelable(Constants.PassingParamConstants.ACTIVITY_PASS_PACKAGE);

        getSupportActionBar().setTitle(mCountryResult.getCountryCode());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            mFragment = new DetailFragment();
            Bundle b = new Bundle();
            b.putParcelable(Constants.PassingParamConstants.ACTIVITY_PASS_PACKAGE ,mCountryResult);
            mFragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }


}
