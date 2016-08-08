package com.post.module.main.base;

import android.support.annotation.LayoutRes;

import com.github.anzewei.parallaxbacklayout.ParallaxActivityBase;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by zhangyao on 16/8/8.
 */
public abstract class BaseActivity extends ParallaxActivityBase{

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(this);
    }

    public abstract void init();
}
