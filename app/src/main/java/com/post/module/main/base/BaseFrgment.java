package com.post.module.main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.post.module.main.base.prsenter.BasePresenterImpl;
import com.post.module.main.base.prsenter.BasePrsenter;
import com.post.module.main.base.view.BaseView;

import butterknife.ButterKnife;

/**
 * Created by zhangyao on 16/8/8.
 */
public abstract class BaseFrgment extends Fragment implements BaseView{

    BasePrsenter basePrsenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    public abstract void init();


    public void getAsync(String url, String[] key, String[] value, int where, boolean isDoalog){

        if(basePrsenter == null){
            basePrsenter = new BasePresenterImpl(this);
        }

        basePrsenter.getAsync(url, key, value, where, true);
    }



}
