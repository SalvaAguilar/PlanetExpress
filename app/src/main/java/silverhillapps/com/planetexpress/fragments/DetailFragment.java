package silverhillapps.com.planetexpress.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import silverhillapps.com.planetexpress.R;
import silverhillapps.com.planetexpress.adapter.DetailAdapter;
import silverhillapps.com.planetexpress.conf.Conf;
import silverhillapps.com.planetexpress.conf.Constants;
import silverhillapps.com.planetexpress.model.CountryResult;


/**
 * This is the detail fragment in charge of showing the detail information of a country.
 */
public class DetailFragment extends Fragment  {


    private AbsListView mListView;

    private DetailAdapter mAdapter;

    private CountryResult mCountryResult;

    //UI Elements
    private TextView airplanesTextView;
    private TextView totalWeightTextView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We retrieve the arguments attached by the activity.
        mCountryResult = getArguments().getParcelable(Constants.PassingParamConstants.ACTIVITY_PASS_PACKAGE);

        mAdapter = new DetailAdapter(getActivity(), mCountryResult.getPackageUnitList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        airplanesTextView = (TextView) view.findViewById(R.id.row_airplanes);
        totalWeightTextView = (TextView) view.findViewById(R.id.row_total_weight);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set the global data in ui
        airplanesTextView.setText(mCountryResult.getNumberAirplanes() + " " + getResources().getString(R.string.text_row_airplanes));
        totalWeightTextView.setText(mCountryResult.getTotalWeight() + " " + Conf.ConversionConf.DEFAULT_CONVERSION_CODE.toUpperCase());

        return view;
    }


    /**
     * Method for communicating with the activity controller, with the data model updated.
     * @param result
     */
    public void updateTextResults(CountryResult result) {
        this.mCountryResult = result;
        mAdapter.setResults(result.getPackageUnitList());
    }
}
