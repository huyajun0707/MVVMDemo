package com.hyj.demo.jsbridgedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.WebViewJavascriptBridge;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/index.html");
//        webView.loadUrl("https://cn.bing.com");
        findViewById(R.id.btCallJs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //方式一

                // 注意调用的JS方法名要对应上
                // 调用javascript的callJs()方法
//                webView.loadUrl("javascript:callJs('方式一')");

                //方式二
                // 只需要将第一种方法的loadUrl()换成下面该方法即可
                webView.evaluateJavascript("javascript:callJs('方式二')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        Log.d("callJs返回值", "onReceiveValue: " + value);
                    }
                });
            }


        });


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder aletDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                aletDialogBuilder.setTitle("Alert");
                aletDialogBuilder.setMessage(message);
                aletDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                aletDialogBuilder.setCancelable(false);
                aletDialogBuilder.create().show();
                return true;
            }

        });


        //JS调native方法
        webView.addJavascriptInterface(new NativeJsInterface(), "android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Log.d("--->", "shouldOverrideUrlLoading:" + url);
                if (uri.getScheme().equals("js")) {
                    String param = uri.getQueryParameter("param");
                    try {
                        JSONObject jsonObject = new JSONObject(param);
                        String method = jsonObject.getString("method");
                        switch (method) {
                            case "goActivity":
                                startActivity(new Intent(MainActivity.this, JsBridgeActivity.class));
                                break;
                            default:
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    view.loadUrl(url);
                }

                return true;

            }
        });

        //js调用本地方法，通过prompt拦截
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")) {
                    String param = uri.getQueryParameter("param");
                    try {
                        JSONObject jsonObject = new JSONObject(param);
                        String method = jsonObject.getString("method");
                        switch (method) {
                            case "goActivity":
                                startActivity(new Intent(MainActivity.this, JsBridgeActivity.class));
                                String ret = "js调用了native,这是返回结果";
                                result.confirm(ret);
                                return true;
                            default:
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }

    //本地方法
    public class NativeJsInterface {
        @JavascriptInterface
        public void goActivity() {
            startActivity(new Intent(MainActivity.this, JsBridgeActivity.class));
        }
    }


}
