package com.post.module.main.base.prsenter;

import com.post.module.main.base.model.BaseModle;
import com.post.module.main.base.model.BaseModleImpl;
import com.post.module.main.base.view.BaseView;

/**
 * Created by zhangyao on 16/8/8.
 */
public class BasePresenterImpl implements BasePrsenter{

    BaseView baseView;

    BaseModle baseModle;

    public BasePresenterImpl(BaseView baseView1){

        this.baseView = baseView1;

        baseModle = new BaseModleImpl();
    }

    @Override
    public void getAsync(String url, String[] key, String[] value, int where, boolean isDoalog) {

        if(baseView != null){
            baseModle.load(baseView, url, key, value, where, true);
        }
    }

    @Override
    public void postAsync(String url, String[] key, String[] value, int where, boolean isDoalog) {

    }
}
