package com.example.wangzhengze.mypowerword.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by WANGZHENGZE on 2015/9/18.
 */
public class RecyclerViewVertical extends RecyclerView {

    private static final String TAG = "RecyclerViewVertical";

    private static final int HEADER_FOOTER_MAX_SIZE = 127;

    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

    public RecyclerViewVertical(Context context) {
        super(context);
        init(context);
    }

    public RecyclerViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new LinearLayoutManager(context));
    }

    public class FixedViewInfo {
        public View view;
        public Object data;
        public boolean isSelectable;
    }

    public void addHeaderView(View view) throws SizeTooLargeException {

        if (getHeaderViewCount() >= HEADER_FOOTER_MAX_SIZE) {
            throw new SizeTooLargeException("header view size is too large, max size is 127");
        }

        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.data = null;
        fixedViewInfo.isSelectable = false;
        fixedViewInfo.view = view;

        mHeaderViewInfos.add(fixedViewInfo);
    }

    public void addFooterView(View view) throws SizeTooLargeException {
        if (getFooterViewCount() >= HEADER_FOOTER_MAX_SIZE) {
            throw new SizeTooLargeException("header view size is too large, max size is 127");
        }

        FixedViewInfo fixedViewInfo = new FixedViewInfo();
        fixedViewInfo.data = null;
        fixedViewInfo.isSelectable = false;
        fixedViewInfo.view = view;

        mFooterViewInfos.add(fixedViewInfo);
    }

    public int getHeaderViewCount() {
        return mHeaderViewInfos.size();
    }

    public int getFooterViewCount() {
        return mFooterViewInfos.size();
    }

    public void removeHeaderView(View view) {
        removeFixedViewInfo(view, mHeaderViewInfos);
    }

    public void removeFooterView(View view) {
        removeFixedViewInfo(view, mFooterViewInfos);
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; ++i) {
            FixedViewInfo info = where.get(i);
            if (info.view == v) {
                where.remove(i);
                break;
            }
        }
    }

    public class SizeTooLargeException extends Exception {

        public SizeTooLargeException(String msg) {
            super(msg);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getHeaderViewCount() > 0 || getFooterViewCount() > 0) {
            super.setAdapter(new HeaderViewRecyclerViewVerticalAdapter<>(mHeaderViewInfos, mFooterViewInfos, adapter));
        } else {
            super.setAdapter(adapter);
        }

    }
}
