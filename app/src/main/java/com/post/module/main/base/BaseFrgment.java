package com.post.module.main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.post.module.main.base.prsenter.BasePresenterImpl;
import com.post.module.main.base.prsenter.BasePrsenter;
import com.post.module.main.base.view.BaseView;

/**
 * Created by zhangyao on 16/8/8.
 */
public abstract class BaseFrgment extends Fragment implements BaseView{

    BasePrsenter basePrsenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentview(), container, false);
        ViewUtils.inject(this, view);

        return view;
    }

    public abstract int setContentview();


    public void getAsync(String url, String[] key, String[] value, int where, boolean isDoalog){

        if(basePrsenter == null){
            basePrsenter = new BasePresenterImpl(this);
        }

        basePrsenter.getAsync(url, key, value, where, true);
    }



}
