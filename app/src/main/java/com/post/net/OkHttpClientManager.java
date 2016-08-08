package com.post.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by zhangyao on 16/8/8.
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    Gson mGson;


    private GetDelegate mGetDelegate = new GetDelegate();

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled 设置cookie 一个预先定义的策略，只接受来自原始服务器的cookie。
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        //创建handler
        mDelivery = new Handler(Looper.getMainLooper());
        //获取json
        mGson = new Gson();

        /*just for test !!!*/
        //设置验证用于确认响应证书申请要求HTTPS连接的主机名。
        //如果没有设置，将使用默认的主机名验证。
        mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Exception e, int tag) {

        }

        @Override
        public void onResponse(String response, int tag) {

        }
    };

    /**
     * 单例模式创建对象
     * @return 对象
     */
    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public static void getAsyn(String url, String[] key, String[] value, ResultCallback callback, Object tag) {
        getInstance().getGetDelegate().getAsyn(url, key, value, callback, tag);
    }

    /**
     * 创建Get代表
     */
    public GetDelegate getGetDelegate() {
        return mGetDelegate;
    }

    //====================GetDelegate======================= Get请求代表
    public class GetDelegate {
        /**
         * 构建Get方式的Request对象
         * @param url 地址
         * @param tag tag
         * @return request对象
         */
        private Request buildGetRequest(String url, String[] key, String[] value, Object tag) {

            if (key.length != value.length) {
                throw new IllegalArgumentException("check your Params key or value length!");
            }

            Map<String, String> paramsMap = new TreeMap<String, String>();
            StringBuffer strBf = new StringBuffer();
            strBf.append("?");
            for (int i = 0; i < key.length; i++){
                paramsMap.put(key[i], value[i]);
                strBf.append(key[i] + "=");
                strBf.append(value[i] + "&");
            }
            strBf.append("sign=");
            strBf.append(UrlParams.genSign(paramsMap) + "&");
            strBf.append("api_key=");
            strBf.append(UrlParams.api_key);
            url += strBf.toString();

            Request.Builder builder = new Request.Builder()
                    .url(url);

            if (tag != null) {
                builder.tag(tag);
            }

            //需要增加session
//            if(GuokuApp != null){
//                builder.addHeader(key[i], value[i]);
//            }
            Log.e("GUOKU=" , url);
            return builder.build();
        }

        /**
         * 通用的方法
         */
        public Response get(Request request) throws IOException {
            Call call = mOkHttpClient.newCall(request);
            Response execute = call.execute();
            return execute;
        }

        /**
         * 同步的Get请求
         */
        public Response get(String url) throws IOException {
            return get(url);
        }

        public Response get(String url, String[] key, String[] value, Object tag) throws IOException {
            final Request request = buildGetRequest(url, key, value, tag);
            return get(request);
        }


        /**
         * 同步的Get请求
         */
        public String getAsString(String url) throws IOException {
            return getAsString(url);
        }

        public String getAsString(String url, String[] key, String[] value, Object tag) throws IOException {
            Response execute = get(url, key, value, tag);
            return execute.body().string();
        }

        /**
         * 通用的方法
         */
        public void getAsyn(Request request, ResultCallback callback) {
            deliveryResult(callback, request);
        }

        /**
         * 异步的get请求
         */
        public void getAsyn(String url, final ResultCallback callback) {
            getAsyn(url, callback);
        }

        public void getAsyn(String url, String[] key, String[] value, final ResultCallback callback, Object tag) {
            final Request request = buildGetRequest(url, key, value, tag);
            getAsyn(request, callback);
        }


    }

    /**
     * 传递结果
     */
    private void deliveryResult(ResultCallback callback, Request request) {
        if (callback == null) callback = DEFAULT_RESULT_CALLBACK;
        final ResultCallback resCallBack = callback;
        //UI thread
        callback.onBefore(request);
        //执行异步任务
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, resCallBack);
            }

            @Override
            public void onResponse(final Response response) {//在子线程中
                try {
                    final String string = response.body().string();
                    if (resCallBack.mType == String.class) {
                        sendSuccessResultCallback(string, resCallBack, (int)response.request().tag());
                    } else {
                        Object o = mGson.fromJson(string, resCallBack.mType);
                        sendSuccessResultCallback(o, resCallBack, (int)response.request().tag());
                    }


                } catch (IOException | JsonParseException e) {
                    sendFailedStringCallback(response.request(), e, resCallBack);
                }

            }
        });
    }

    /**
     * 发送错误的callbakc
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, e, (int)request.tag());
                callback.onAfter();
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback, final int tag) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, tag);
                callback.onAfter();
            }
        });
    }


    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            Log.d("***************", parameterized.getRawType().toString());
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public void onBefore(Request request) {
        }

        public void onAfter() {
        }

        public abstract void onError(Request request, Exception e, int tag);

        public abstract void onResponse(T response, int tag);
    }

}
