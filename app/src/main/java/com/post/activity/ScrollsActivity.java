package com.post.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.post.R;
import com.post.bean.Discover;
import com.post.module.main.base.BaseActivity;
import com.post.module.main.tab.one.OneTabFrgmentAdapter;
import com.post.net.NetConfig;
import com.post.widget.recycleview.LoadMoreListener;
import com.post.widget.recycleview.RecyclerViewUpRefresh;

public class ScrollsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, LoadMoreListener {

    private final int NET_TAG = 1001;
    private final int NET_TAG_REFRESH = 1002;
    private final int NET_TAG_LOAD_MORE = 1003;

    @ViewInject(R.id.rv)
    RecyclerViewUpRefresh recyclerViewUpRefresh;

    @ViewInject(R.id.sr)
    SwipeRefreshLayout swipeRefreshLayout;

    OneTabFrgmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolls);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scrolls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void init() {


        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerViewUpRefresh.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OneTabFrgmentAdapter(this);
        recyclerViewUpRefresh.setAdapter(adapter);
        recyclerViewUpRefresh.setNestedScrollingEnabled(false);
        recyclerViewUpRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ScrollsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        getAsync(NetConfig.TEST_URL, new String[]{}, new String[]{}, NET_TAG, true);
    }

    @Override
    public void succeed(Object o, int tag) {

        switch (tag){
            case NET_TAG:
                Discover discover =  JSON.parseObject(o.toString(), Discover.class);
                adapter.addDataTop(discover.getEntities());
                break;
        }
    }

    @Override
    public void error(int tag) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadMore() {

    }
}
