package silverhillapps.com.planetexpress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import silverhillapps.com.planetexpress.R;
import silverhillapps.com.planetexpress.model.PackageUnit;

/**
 * Adapter class for representing the detail list view. We use ArrayAdapter as the list is not enough complex to extend from BaseAdapter
 * @author salva
 *
 */
public class DetailAdapter extends ArrayAdapter<PackageUnit> {

    private Context mContext;
    private List<PackageUnit> mResult;        // The model to be displayed in the list
    private LayoutInflater mInflater = null;


    public DetailAdapter(Context context, List<PackageUnit> objects) {
        super(context, R.layout.list_row_detail, objects);

        this.mContext = context;
        this.mResult = objects;
        mInflater = LayoutInflater.from(mContext);

    }


    static class ViewHolder {
        public TextView textView;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rowView = convertView;

        // We reuse the views
        if (rowView == null) {

            rowView = mInflater.inflate(R.layout.list_row_detail, null);

            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.title_textView_detail);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        PackageUnit j = (PackageUnit) getItem(position);

        // UI update
        holder.textView.setText(j.getIdPackage());

        return rowView;
    }

    /**
     * Method for communicating with the fragment to notify the new data model.
     *
     * @param result
     */
    public void setResults(List<PackageUnit> result) {

        this.clear();

        for(PackageUnit s: result){this.add(s);} // we creater the loop because the addAll method is not available in sdk 9, should be needed minimum 11.

        notifyDataSetChanged();

    }
}