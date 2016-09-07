package com.post.module.main.tab.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.post.R;
import com.post.bean.Discover;
import com.post.module.main.base.BaseFrgment;
import com.post.net.NetConfig;
import com.post.widget.recycleview.LoadMoreListener;
import com.post.widget.recycleview.RecyclerViewUpRefresh;

/**
 * Created by zhangyao on 16/8/8.
 */
public class OneTabFrgment extends BaseFrgment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListener {

    private final int NET_TAG = 1001;
    private final int NET_TAG_REFRESH = 1002;
    private final int NET_TAG_LOAD_MORE = 1003;

    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerViewUpRefresh recyclerViewUpRefresh;

    OneTabFrgmentAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.frgemt_home_one, null);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr);
        recyclerViewUpRefresh = (RecyclerViewUpRefresh) view.findViewById(R.id.rv);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerViewUpRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OneTabFrgmentAdapter(getActivity());
        recyclerViewUpRefresh.setAdapter(adapter);
        recyclerViewUpRefresh.setLoadMoreListener(this);
        recyclerViewUpRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        init();

        return view;
    }

    @Override
    public void init() {

        getAsync(NetConfig.TEST_URL, new String[]{}, new String[]{}, NET_TAG, true);
    }

    @Override
    public void succeed(Object o, int tag) {

//        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        switch (tag){
            case NET_TAG:
                Discover discover =  JSON.parseObject(o.toString(), Discover.class);
                adapter.addDataTop(discover.getEntities());
                break;
            case NET_TAG_REFRESH:
                adapter.addDataTop(JSON.parseObject(o.toString(), Discover.class).getEntities());
                break;
            case NET_TAG_LOAD_MORE:
                adapter.addData(JSON.parseObject(o.toString(), Discover.class).getEntities());
                break;
        }

    }

    @Override
    public void error(int tag) {
        Log.d("******", "error");
        switch (tag){
            case NET_TAG:
                break;
        }
    }

    @Override
    public void onRefresh() {
        getAsync(NetConfig.TEST_URL, new String[]{}, new String[]{}, NET_TAG_REFRESH, true);
    }

    @Override
    public void onLoadMore() {
        getAsync(NetConfig.TEST_URL, new String[]{}, new String[]{}, NET_TAG_LOAD_MORE, true);
    }
}
