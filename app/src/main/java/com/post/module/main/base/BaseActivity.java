package com.post.module.main.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.github.anzewei.parallaxbacklayout.ParallaxActivityBase;
import com.lidroid.xutils.ViewUtils;
import com.post.module.main.base.prsenter.BasePresenterImpl;
import com.post.module.main.base.prsenter.BasePrsenter;
import com.post.module.main.base.view.BaseView;

import butterknife.ButterKnife;

/**
 * Created by zhangyao on 16/8/8.
 */
public abstract class BaseActivity extends ParallaxActivityBase implements BaseView{


    BasePrsenter basePrsenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(this);
    }

    public abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    public void getAsync(String url, String[] key, String[] value, int where, boolean isDoalog){

        if(basePrsenter == null){
            basePrsenter = new BasePresenterImpl(this);
        }

        basePrsenter.getAsync(url, key, value, where, true);
    }
}
