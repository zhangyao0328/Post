package com.post.module.main.tab.one;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.post.R;
import com.post.module.main.base.BaseFrgment;
import com.post.net.NetConfig;

/**
 * Created by zhangyao on 16/8/8.
 */
public class OneTabFrgment extends BaseFrgment{

    @ViewInject(R.id.textView2)
    TextView textView;

    @ViewInject(R.id.button)
    Button button;

    @Override
    public int setContentview() {
        return R.layout.layout_test;
    }

    @Override
    public void succeed(Object o, int tag) {

        textView.setText(o.toString());
        Log.d("******", o.toString());
    }

    @Override
    public void error(int tag) {
        textView.setText("error");
        Log.d("******", "error");
    }

    private void getData(){
        getAsync(NetConfig.TEST_URL, new String[]{}, new String[]{}, 0, true);
    }

    @OnClick(R.id.button)
    private void loadData(View view){
        getData();
    }
}
