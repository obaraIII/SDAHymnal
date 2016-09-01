package blkxltng.com.sdahymnal;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by firej on 8/31/2016.
 */
public class HymnListAdapter implements ListAdapter {

    private List<Hymns> mHymnsList;
    private Context mContext;
    private Activity mActivity;

    public HymnListAdapter (Context context, Activity activity, List<Hymns> hymnsList) {
        this.mHymnsList = hymnsList;
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mHymnsList.size();
    }

    @Override
    public Object getItem(int i) {
        return mHymnsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Holds the view references
        Holder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_hymn, viewGroup, false);
            holder = new Holder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.list_item_hymn_textview);
            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }
        if (mHymnsList.get(i) != null) {
            String hymn = mHymnsList.get(i).getNumber() + " - " + mHymnsList.get(i).getTitle();
            holder.mTextView.setText(hymn);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class Holder {
        TextView mTextView;
    }
}
