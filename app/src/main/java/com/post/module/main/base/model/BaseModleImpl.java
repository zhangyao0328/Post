package com.post.module.main.base.model;

import android.util.Log;

import com.post.module.main.base.view.BaseView;
import com.post.net.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * Created by zhangyao on 16/8/8.
 */
public class BaseModleImpl implements BaseModle {

    @Override
    public void load(final BaseView baseView, String url, String[] key, String[] value, int where, final boolean isDoalog) {

        OkHttpClientManager.getAsyn(url, key, value, new MyResultCallback<String>(where, baseView, isDoalog)
        {
            public void onError(Request request, Exception e, int tag)
            {
                Log.e("TAG", "onError" + e.getMessage());
                e.printStackTrace();
                baseView.error(tag);
            }

            public void onResponse(String u, int tag)
            {
                if (!u.isEmpty()){
                    baseView.succeed(u, tag);
                    Log.e("TAG", u);
                }
            }
        }, where);
    }

    public abstract class MyResultCallback<T> extends OkHttpClientManager.ResultCallback<T>
    {

        BaseView baseView;
        boolean isShowDialog;
        int where;

        MyResultCallback(int wheres, BaseView bs, boolean dialog){
            this.baseView = bs;
            isShowDialog = dialog;
            this.where = wheres;
        }

        @Override
        public void onBefore(Request request)
        {
            super.onBefore(request);
//            setTitle("loading...");
            if (isShowDialog){
            }
        }

        @Override
        public void onAfter()
        {
            super.onAfter();
//            setTitle("Sample-okHttp");
            if (isShowDialog){
            }
        }
    }
}
