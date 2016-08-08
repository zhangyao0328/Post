package com.post.module.main.base.view;

/**
 * Created by zhangyao on 16/8/8.
 */
public interface BaseView<T> {

    void succeed(T t, int tag);

    void error(int tag);
}
