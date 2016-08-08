package com.post.module.main.base.prsenter;

/**
 * Created by zhangyao on 16/8/8.
 */
public interface BasePrsenter {

    void getAsync(String url, String[] key, String[] value, int where, boolean isDoalog );

    void postAsync(String url, String[] key, String[] value, int where, boolean isDoalog);

}
