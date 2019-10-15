package com.hyj.demo.jsbridgedemo;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class JsBridgeActivity extends AppCompatActivity {

    private BridgeWebView bridgeWebView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        bridgeWebView = findViewById(R.id.bridgeWebview);
        //js调用natvie方法添加默认处理者
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.loadUrl("file:///android_asset/jsbridge_index.html");
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
        bridgeWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        bridgeWebView.registerHandler("callApp", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("---->", "handler = callApp, data from web = " + data);
                function.onCallBack("callApp exe, response data 测试 from Java");
            }

        });

        findViewById(R.id.btCallJs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "{\"params\": \"哈哈哈\"}";
                bridgeWebView.callHandler("callJs", json, new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.d("---->", "onCallBack: "+data);
                    }
                });
            }
        });

    }
}
