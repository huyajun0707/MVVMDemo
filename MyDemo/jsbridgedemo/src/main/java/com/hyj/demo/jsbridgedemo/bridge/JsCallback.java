package com.hyj.demo.jsbridgedemo.bridge;

import android.util.Log;
import android.webkit.WebView;

import java.lang.ref.WeakReference;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-15 17:32
 * @depiction ：
 */
public class JsCallback {
    private int mIndex;
    private boolean mCouldGoOn;
    private WeakReference<WebView> mWebViewRef;
    private int mIsPermanent;
    private String mInjectedName;

    public JsCallback(WebView view, String injectedName, int index) {
        mCouldGoOn = true;
        mWebViewRef = new WeakReference<WebView>(view);
        mInjectedName = injectedName;
        mIndex = index;
    }

    public void apply(Object... args) throws JsCallbackException {
        if (mWebViewRef.get() == null) {
            throw new JsCallbackException("the WebView related to the JsCallback has been recycled");
        }
        if (!mCouldGoOn) {
            throw new JsCallbackException("the JsCallback isn't permanent,cannot be called more than once");
        }
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                builder.append("\"");
            }
            builder.append(String.valueOf(arg));
            if (isStrArg) {
                builder.append("\"");
            }
        }
        String execJs = String.format(Constant.CALLBACK_JS_FORMAT, mInjectedName, mIndex, mIsPermanent, builder.toString());
        Log.d("---->execjs", execJs);
        mWebViewRef.get().loadUrl(execJs);
        mCouldGoOn = mIsPermanent > 0;
    }

    public void setPermanent(boolean value) {
        mIsPermanent = value ? 1 : 0;
    }
}
