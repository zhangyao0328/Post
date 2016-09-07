package com.post.module.main.base.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * zhangyao
 * 16/8/16
 * zhangyao@jiandanxinli.com
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{
    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> mViews;


    public BaseRecyclerViewHolder(View view) {
        super(view);
        this.mViews = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public  <V extends View> V findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (V) view;
    }


}
