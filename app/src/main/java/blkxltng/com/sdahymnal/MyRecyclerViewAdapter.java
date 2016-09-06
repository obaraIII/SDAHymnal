package blkxltng.com.sdahymnal;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by firej on 8/30/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {

    private List<Hymns> mHymnsList;
    private Context mContext;
    private Activity mActivity;

    public MyRecyclerViewAdapter(Context context, Activity a, List<Hymns> hymnsList) {
        this.mHymnsList = hymnsList;
        this.mActivity = a;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_hymns, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final Hymns hymns = mHymnsList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(hymns.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .centerInside()
                .into(customViewHolder.imageView);

        String sectionString = hymns.getSection() + "\n(Hymns " + hymns.getFirstHymn() + "-" + hymns.getLastHymn() + ")";

        //Setting text view title
        customViewHolder.textView.setText(sectionString);

        customViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)mActivity;
                mainActivity.sectionClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mHymnsList ? mHymnsList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected View mView;

        public CustomViewHolder(View view) {
            super(view);
            this.mView = view;
            this.imageView = (ImageView) view.findViewById(R.id.imageview_card);
            this.textView = (TextView) view.findViewById(R.id.textview_card);
        }

    }
}
