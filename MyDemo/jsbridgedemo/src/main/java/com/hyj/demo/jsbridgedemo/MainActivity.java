package com.hyj.demo.jsbridgedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Picture;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

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

        findViewById(R.id.btTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Info> infos = new ArrayList<>();
                Info info = new Info();
                info.setId(1L);
                info.setName("test");
                info.setAge(10);
                infos.add(info);
                isString(info.getName());

                ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes fa) {
                        return fa.getName().equals("id"); //
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }

                };

                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(myExclusionStrategy) //
                        .create();
                String result = gson.toJson(infos);
                Log.d("--->result", "onClick: " + result);

            }
        });


//        webView.loadUrl("https://cn.bing.com/");
//        webView.loadUrl("https://cn.bing.com");
        webView.loadUrl("file:///android_asset/default.html");



        findViewById(R.id.btCallJs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //方式一

                // 注意调用的JS方法名要对应上
                // 调用javascript的callJs()方法
//                webView.loadUrl("javascript:callJs('方式一')");

                //方式二
                // 只需要将第一种方法的loadUrl()换成下面该方法即可
                JSONObject jsonObject  = new JSONObject();
                try {
                    jsonObject.put("value","方式二");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                webView.evaluateJavascript("javascript:callJs(" + "0000" + ")", new ValueCallback<String>() {
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
//                AlertDialog.Builder aletDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                aletDialogBuilder.setTitle("Alert");
//                aletDialogBuilder.setMessage(message);
//                aletDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                aletDialogBuilder.setCancelable(false);
//                aletDialogBuilder.create().show();
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

            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                webView.evaluateJavascript("javascript:setImageView(\"" + "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591790217027&di=3ca350154277cfe599783672fa2061ed&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg" + "\")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果

                    }
                });
                view.post(new Runnable() {
                  @Override
                  public void run() {
//                      int  height = (int) (view.getContentHeight());//得到的是网页在手机上真实的高度
//                      int  height=  webView.getMeasuredHeight();
                      int  height=  webView.getHeight();
                      Log.d("----->webview", "onPageFinished: "+height);
                  }
              });

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        int contentHeight = webView.getContentHeight();
//                        int viewHeight = webView.getHeight();
//                        Toast.makeText(MainActivity.this, "*** " + contentHeight + " - " + viewHeight, Toast.LENGTH_SHORT).show();
//
//                        Log.d("--->", "run: contentHeight:"+contentHeight+",viewHeight:"+viewHeight);
//                    }
//                }, 500);
            }
        });




        webView.setPictureListener(new WebView.PictureListener() {
            int previousHeight;
            @Override
            public void onNewPicture(WebView w, Picture p) {
                int height = w.getContentHeight();
                if (previousHeight == height) return;
                previousHeight = height;
                Log.d("--->", "WebView getContentHeight" + height);
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

    private void isString(Object name) {
        Log.d("---->", "isString: " + (name instanceof String));

    }

    //本地方法
    public class NativeJsInterface {
        @JavascriptInterface
        public void goActivity() {
            startActivity(new Intent(MainActivity.this, JsBridgeActivity.class));
        }

        @JavascriptInterface
        public String test() {
            return "ok";
        }
    }





}
