package com.hyj.demo.jsbridgedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.hyj.demo.jsbridgedemo.bridge.InjectedChromeClient;
import com.hyj.demo.jsbridgedemo.bridge.InjectedWebviewClient;
import com.hyj.demo.jsbridgedemo.bridge.JsCallJava;

public class BridgeForPormptActivity extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_for_pormpt);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromClient("myApp", NativeMethod.class));
        webView.setWebViewClient(new InjectedWebviewClient(this));
        webView.loadUrl("file:///android_asset/bridge_index2.html");
    }


    public class MyWebChromClient extends InjectedChromeClient {

        public MyWebChromClient(String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        public MyWebChromClient(JsCallJava JsCallJava) {
            super(JsCallJava);
        }
    }


    public  static class NativeMethod {

        public static String call(final WebView webView, String data) {
            Log.d("---->", "data:" + data);

            return "{\"result\": \"success\"}";


        }
        public static void show(final WebView webView, String data) {
            Log.d("---->", "data:" + data);


        }
    }


}
