package com.post.module.main.base.model;

import com.post.module.main.base.view.BaseView;

/**
 * Created by zhangyao on 16/8/8.
 */
public interface BaseModle {

    void load(BaseView baseView, String url, String[] key, String[] value, int where, boolean isDoalog);


}
