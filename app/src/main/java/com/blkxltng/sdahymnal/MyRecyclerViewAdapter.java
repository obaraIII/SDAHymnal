package com.blkxltng.sdahymnal;

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

import com.blkxltng.sdahymnal.R;

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
        Picasso.with(mContext).load(imageChooser(hymns.getId()))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .resize(500,500)
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

    public int imageChooser(int input) {
        if(input == 1)
            return R.drawable.img1;
        if(input == 2)
            return R.drawable.img2;
        if(input == 3)
            return R.drawable.img3;
        if(input == 4)
            return R.drawable.img4;
        if(input == 5)
            return R.drawable.img5;
        if(input == 6)
            return R.drawable.img6;
        if(input == 7)
            return R.drawable.img7;
        if(input == 8)
            return R.drawable.img8;
        if(input == 9)
            return R.drawable.img9;
        if(input == 10)
            return R.drawable.img10;
        if(input == 11)
            return R.drawable.img11;
        if(input == 12)
            return R.drawable.img12;
        if(input == 13)
            return R.drawable.img13;
        if(input == 14)
            return R.drawable.img14;
        return R.drawable.placeholder;
    }
}
