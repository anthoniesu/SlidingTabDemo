package com.demo.slidingtabdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.slidingtabdemo.R;
import com.demo.slidingtabdemo.entity.DataSet;
import com.demo.slidingtabdemo.listener.OnLoadMoreListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataSet.Data> mDataSet;
    private Context mContext;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public ItemAdapter(Context context, RecyclerView recyclerView, List<DataSet.Data> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
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
        } else if (viewType == ITEM_TYPE.STYLE_LOADING.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            mViewHolder = new LoadingViewHolder(view);
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
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position) == null)
            return ITEM_TYPE.STYLE_LOADING.ordinal();

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

    public void setLoaded() {
        isLoading = false;
    }

    public static enum ITEM_TYPE {
        STYLE_COMPLETE,
        STYLE_IMAGE,
        STYLE_LOADING,
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }
}