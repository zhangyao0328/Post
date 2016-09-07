package com.post.module.main.tab.one;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.post.R;
import com.post.bean.Discover;
import com.post.module.main.base.adapter.BaseTurboAdapter;
import com.post.module.main.base.holder.BaseRecyclerViewHolder;

import java.util.List;

/**
 * zhangyao
 * 16/9/5
 * zhangyao@jiandanxinli.com
 */
public class OneTabFrgmentAdapter extends BaseTurboAdapter<Discover.EntitiesBean, OneTabFrgmentAdapter.SimpleViewHolder>{

    public OneTabFrgmentAdapter(Context context) {
        super(context);
    }


    protected void convert(final SimpleViewHolder holder, Discover.EntitiesBean item) {

        holder.tv.setText(item.getEntity().getTitle());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public OneTabFrgmentAdapter(Context context, List<Discover.EntitiesBean> data) {
        super(context, data);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(inflateItemView(R.layout.item_type_3, parent));
    }




    class SimpleViewHolder extends BaseRecyclerViewHolder {

        TextView tv;

        protected SimpleViewHolder(View view) {
            super(view);
            tv = findViewById(R.id.text);
        }
    }
}
