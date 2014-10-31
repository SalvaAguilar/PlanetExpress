package silverhillapps.com.planetexpress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import silverhillapps.com.planetexpress.R;

/**
 * Adapter class for representing the general countries list view. We use ArrayAdapter as the list is not enough complex to extend from BaseAdapter
 * @author salva
 *
 */
public class CountriesAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mCountries;   // The countries
    private LayoutInflater mInflater = null;

    public CountriesAdapter(Context context, List<String> objects) {
        super(context, R.layout.list_row_general, objects);

        this.mContext = context;
        this.mCountries = objects;
        mInflater = LayoutInflater.from(mContext);

    }


    static class ViewHolder {
        public TextView titleTextView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rowPhotoView = convertView;

        // We reuse the views
        if (rowPhotoView == null) {

            rowPhotoView = mInflater.inflate(R.layout.list_row_general, null);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) rowPhotoView.findViewById(R.id.title_textView_general);

            rowPhotoView.setTag(holder);
        }
        else{
            holder = (ViewHolder) rowPhotoView.getTag();
        }

        // UI update
        holder.titleTextView.setText(getItem(position));

        return rowPhotoView;
    }


    /**
     * Method for communicating with the fragment to notify the new data model.
     * @param result
     */
    public void setResults(List<String> result) {
        this.clear();

        for(String s: result){this.add(s);} // we creater the loop because the addAll method is not available in sdk 9, should be needed minimum 11.

        notifyDataSetChanged();

    }

}