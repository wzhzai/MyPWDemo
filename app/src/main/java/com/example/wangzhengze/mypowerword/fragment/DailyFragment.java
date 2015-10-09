package com.example.wangzhengze.mypowerword.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzhengze.mypowerword.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment {

    private static final String TAG = "DailyFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView mTvName;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvName = (TextView) view.findViewById(R.id.name);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        TodayHistoryService service = retrofit.create(TodayHistoryService.class);
        Map<String, String> map = new HashMap<>();
        map.put("month", "4");
        map.put("day", "6");
        map.put("appkey", "1307ee261de8bbcf83830de89caae73f");

        Observable<HistoryBean> result = service.listRepos(map);
        Log.e(TAG, "this is no net?!");
        result
                .subscribeOn(Schedulers.io())
                .doOnNext(historyBean1 -> Log.e(TAG, "doOnNext thread = " + Thread.currentThread().getName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historyBean -> {
                    Log.e(TAG, "currentThread = " + Thread.currentThread().getName());
                    mTvName.setText("aaaaaaa");
                });
//        new Thread(() -> {
//
////            Call<HistoryBean> result = service.listRepos(map);
////            try {
////                Log.e(TAG, "result = " + result.execute().body().toString());
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }).start();
        test();

    }


    public interface TodayHistoryService {
        @Headers("apikey: 142ac2bc6840094a4766e8c80189273f")
        @GET("/netpopo/todayhistory/todayhistory")
        Observable<HistoryBean> listRepos(@QueryMap Map<String, String> options);
//        Call<HistoryBean> listRepos(@QueryMap Map<String, String> options);
    }


    static class HistoryBean {
        String error;
        String msg;
        List<DataBean> data;

        static class DataBean {
            String id;
            String name;
            String year;
            String month;
            String day;
            String content;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", year='" + year + '\'' +
                        ", month='" + month + '\'' +
                        ", day='" + day + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "HistoryBean{" +
                    "error='" + error + '\'' +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    Callback<HistoryBean> callback = new Callback<HistoryBean>() {
        @Override
        public void onResponse(Response<HistoryBean> response, Retrofit retrofit) {
            Log.e(TAG, "response code = " + response.code() + "; response = " + response.body().toString());
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "t = " + Arrays.toString(t.getStackTrace()));
        }
    };

    private void test() {
        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onNext("hello, world!");
//            subscriber.onCompleted();
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "subscriber --> onCompleted()");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "subscriber --> onNext() = " + s);
            }
        };


        Subscriber<String> subscriber2 = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "subscriber2 --> onCompleted()");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "subscriber2 --> onNext() = " + s);
            }
        };

        observable.subscribe(subscriber);
        observable.subscribe(subscriber2);

//        query("Hello, world!")
//                .flatMap(Observable::from)
//                .subscribe(url -> Log.e(TAG, "url = " + url));

    }

    Observable<List<String>> query(String text) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("url" + (i + 1));
        }
        return Observable.create(subscriber -> {
            subscriber.onNext(stringList);
            subscriber.onCompleted();
        });
    }

}
