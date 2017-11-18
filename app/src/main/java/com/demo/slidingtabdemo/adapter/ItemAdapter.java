package com.demo.slidingtabdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.slidingtabdemo.R;
import com.demo.slidingtabdemo.entity.DataSet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataSet.Data> mDataSet;
    private Context mContext;

    public ItemAdapter(Context context, List<DataSet.Data> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder = null;
        if (viewType == ITEM_TYPE.STYLE_COMPLETE.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_complete, parent, false);
            mViewHolder = new ViewHolderComplete(view);
        } else if (viewType == ITEM_TYPE.STYLE_IMAGE.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_image, parent, false);
            mViewHolder = new ViewHolderImage(view);
        }
        return mViewHolder;
    }

    private void loadImageViewfromUrl(String url, ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .fit()
                .into(imageView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderComplete) {
            ((ViewHolderComplete) holder).tvTitle.setText(mDataSet.get(position).getTitle());
            ((ViewHolderComplete) holder).tvContent.setText(mDataSet.get(position).getContent());
            loadImageViewfromUrl(mDataSet.get(position).getImageLink(), ((ViewHolderComplete) holder).ivImageLink);

        } else if (holder instanceof ViewHolderImage) {
            loadImageViewfromUrl(mDataSet.get(position).getImageLink(), ((ViewHolderImage) holder).ivImageLink);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String title = mDataSet.get(position).getTitle();
        if (title != null && !title.isEmpty()) {
            return ITEM_TYPE.STYLE_COMPLETE.ordinal();
        } else {
            return ITEM_TYPE.STYLE_IMAGE.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return (mDataSet == null) ? 0 : mDataSet.size();
    }

    public static enum ITEM_TYPE {
        STYLE_COMPLETE,
        STYLE_IMAGE,
    }

    public class ViewHolderComplete extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvContent;
        public ImageView ivImageLink;

        public ViewHolderComplete(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvContent = (TextView) v.findViewById(R.id.tv_content);
            tvContent.setEllipsize(TextUtils.TruncateAt.END);
            ivImageLink = (ImageView) v.findViewById(R.id.iv_imagelink);
        }
    }

    public class ViewHolderImage extends RecyclerView.ViewHolder {
        public ImageView ivImageLink;

        public ViewHolderImage(View v) {
            super(v);
            ivImageLink = (ImageView) v.findViewById(R.id.iv_imagelink);
        }
    }

}