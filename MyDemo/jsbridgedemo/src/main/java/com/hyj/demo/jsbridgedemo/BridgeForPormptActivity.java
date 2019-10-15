package com.hyj.demo.jsbridgedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.hyj.demo.jsbridgedemo.bridge.InjectedChromeClient;
import com.hyj.demo.jsbridgedemo.bridge.JsCallJava;

public class BridgeForPormptActivity extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_for_pormpt);
        webView = findViewById(R.id.webview);
        webView.setWebChromeClient(new MyWebChromClient("native", NativeMethod.class));

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

        public static void call(final WebView webView, String data) {
            Log.d("---->", "data:" + data);


        }
    }


}
