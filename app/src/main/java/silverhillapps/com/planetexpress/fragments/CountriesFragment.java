package silverhillapps.com.planetexpress.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import silverhillapps.com.planetexpress.R;
import silverhillapps.com.planetexpress.adapter.CountriesAdapter;

/**
 * This is the fragment in charge of showing the list of countries where we need to carry the different packages.
 */
public class CountriesFragment extends Fragment implements AbsListView.OnItemClickListener {


    private ItemSelectedListener mListener;

    private AbsListView mListView;

    private CountriesAdapter mAdapter;

    private List<String> mCountryResult;

    // Mandatory empty constructor.
    public CountriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCountryResult = new ArrayList<String>();

        mAdapter = new CountriesAdapter(getActivity(), mCountryResult);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Method for capturing the user touch on the list and notifying the parent activity.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {

            mListener.onItemSelected(mCountryResult.get(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    /**
     * Method used for updating the data of the fragment from the parent activity.
     * @param crl
     */
    public void updateResults(List<String> crl) {
        this.mCountryResult = crl;
        this.mAdapter.setResults(this.mCountryResult);

    }
}