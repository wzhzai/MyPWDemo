package com.example.wangzhengze.mypowerword.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGZHENGZE on 2015/9/18.
 */
public class HeaderViewRecyclerViewVerticalAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private static final int VIEW_TYPE_HEADER_MASK = 0;

    private static final int VIEW_TYPE_FOOTER_MASK = 128;

    private static final int VIEW_TYPE_NONE = -1;

    private static final List<RecyclerViewVertical.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList<>();

    private ArrayList<RecyclerViewVertical.FixedViewInfo> mHeaderViewInfos;

    private ArrayList<RecyclerViewVertical.FixedViewInfo> mFooterViewInfos;

    private final RecyclerView.Adapter<VH> mAdapter;

    public HeaderViewRecyclerViewVerticalAdapter(ArrayList<RecyclerViewVertical.FixedViewInfo> headerViewInfos, ArrayList<RecyclerViewVertical.FixedViewInfo> footerViewInfos, RecyclerView.Adapter<VH> adapter) {
        mHeaderViewInfos = headerViewInfos;
        mFooterViewInfos = footerViewInfos;
        mAdapter = adapter;
    }

    class FixedViewHolder extends RecyclerView.ViewHolder {

        public FixedViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        int p;

        if (viewType >= VIEW_TYPE_FOOTER_MASK) {
            p = viewType ^ VIEW_TYPE_FOOTER_MASK;
            return (VH) new FixedViewHolder(mFooterViewInfos.get(p).view);
        } else {
            p = viewType;
            return (VH) new FixedViewHolder(mHeaderViewInfos.get(p).view);
        }

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (position >= getHeadersCount() && position < mAdapter.getItemCount() + getHeadersCount()) {
            mAdapter.onBindViewHolder(holder, position - getHeadersCount());
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
        int numHeaders = getHeadersCount();

        if (position < numHeaders) {
            return position;
        }

        int oldCount = 0;

        if (mAdapter != null) {
            oldCount = mAdapter.getItemCount();
        }

        int numOther = numHeaders + oldCount;

        if (position >= numOther) {
            return VIEW_TYPE_FOOTER_MASK | position - numOther;
        }

        return VIEW_TYPE_NONE;
    }

    private int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    private int getFootersCount() {
        return mFooterViewInfos.size();
    }

    public boolean removeHeader(View v) {
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            RecyclerViewVertical.FixedViewInfo info = mHeaderViewInfos.get(i);
            if (info.view == v) {
                mHeaderViewInfos.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean removeFooter(View v) {
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            RecyclerViewVertical.FixedViewInfo info = mFooterViewInfos.get(i);
            if (info.view == v) {
                mFooterViewInfos.remove(i);
                return true;
            }
        }

        return false;
    }
}
