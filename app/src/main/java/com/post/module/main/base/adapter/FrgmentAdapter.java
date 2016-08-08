package com.post.module.main.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zhangyao on 16/8/8.
 */
public class FrgmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;

    public FrgmentAdapter(FragmentManager fm, ArrayList<Fragment> lists) {
        super(fm);

        this.list = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
