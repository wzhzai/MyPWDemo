package com.example.wangzhengze.mypowerword.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzhengze.mypowerword.R;
import com.example.wangzhengze.mypowerword.adapter.RecyclerHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingFragment extends Fragment {

    private final static String TAG = "ReadingFragment";

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;

    private Context mContext;

    private List<String> mContentList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadingFragment newInstance(String param1, String param2) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContentList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        AppCompatActivity compatActivity = (AppCompatActivity) mContext;
        compatActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.menu_button);

        for (int i = 0; i < 100; i++) {
            mContentList.add("this is " + i);
        }

        List<View> mHeaderViewList = new ArrayList<>();

        List<View> mFooterViewList = new ArrayList<>();

        TextView tvHeader = new TextView(mContext);
        tvHeader.setText("this is header");
        TextView tvFooter = new TextView(mContext);
        tvFooter.setText("this is footer");

        mHeaderViewList.add(tvHeader);
        mFooterViewList.add(tvFooter);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.reading_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new RecyclerHeaderAdapter<>(new ReadingRecyclerAdapter(), mHeaderViewList, mFooterViewList));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }

    class ReadingRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder --> viewType = " + viewType);
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_reading_recycler, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvTitle.setText(mContentList.get(position));
        }

        @Override
        public int getItemCount() {
            return mContentList.size();
        }
    }
}
