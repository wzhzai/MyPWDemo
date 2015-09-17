package com.example.wangzhengze.mypowerword.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGZHENGZE on 2015/9/17.
 */
public class RecyclerHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int RECYCLER_TYPE_HEADER = 1;
    public static final int RECYCLER_TYPE_FOOTER = 2;

    private final RecyclerView.Adapter<VH> mAdapter;

    private List<View> mHeaderViewList;

    private List<View> mFooterViewList;

    public RecyclerHeaderAdapter(RecyclerView.Adapter<VH> adapter, List<View> headerViewList, List<View> footerViewList) {
        mAdapter = adapter;
        mHeaderViewList = headerViewList;
        mFooterViewList = footerViewList;

        if (mHeaderViewList == null) {
            mHeaderViewList = new ArrayList<>();
        }

        if (mFooterViewList == null) {
            mFooterViewList = new ArrayList<>();
        }

    }

    public int getHeadersCount() {
        return mHeaderViewList.size();
    }

    public int getFootersCount() {
        return mFooterViewList.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return mAdapter.onCreateViewHolder(parent, viewType);
            case RECYCLER_TYPE_HEADER:
                return (VH) new HeaderHolder(mHeaderViewList.get(0));
            case RECYCLER_TYPE_FOOTER:
                return (VH) new FooterHolder(mFooterViewList.get(0));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (position >= getHeadersCount() && position < mAdapter.getItemCount() + getHeadersCount() - 1) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeadersCount()) {
            return RECYCLER_TYPE_HEADER;
        } else if (position >= getHeadersCount() + mAdapter.getItemCount()) {
            return RECYCLER_TYPE_FOOTER;
        } else {
            return 0;
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
