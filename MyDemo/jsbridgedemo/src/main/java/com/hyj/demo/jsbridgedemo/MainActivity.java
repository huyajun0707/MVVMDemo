package com.hyj.demo.jsbridgedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.WebViewJavascriptBridge;

public class MainActivity extends AppCompatActivity {
    private BridgeWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        webView = findViewById(R.id.webview);


    }
    private void JSMethod(){
//        webView.registerHandler("submitFromWeb", new BridgeHandler(){
//            @Override
//            public void handler(String data, CallBackFunction function){
//                function.onCallBack("submitFrom web exe, response data from java");
//            }
//        });
    }

}
