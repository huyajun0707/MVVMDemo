package com.hyj.load.x5demo;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hyj.load.x5demo.util.FileUtil;
import com.hyj.load.x5demo.util.X5Util;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private Button btOpenFileForPath, btOpenFile;
    private static final String TAG = "MainActivity";
    private LinearLayout normalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        copy();
        setListenrer();
        //TODO 初始化webSettings及默认选择webview加载页面
        initWebView();

    }

    private void findViewById() {
        webView = findViewById(R.id.webView);
        btOpenFileForPath = findViewById(R.id.btOpenFileForPath);
        btOpenFile = findViewById(R.id.btOpenFile);
        normalView = findViewById(R.id.navigation1);
    }

    private void setListenrer() {
        btOpenFileForPath.setOnClickListener(this);
        btOpenFile.setOnClickListener(this);
    }

    /**
     * 初始化webSettings及默认选择webview加载页面
     */
    private void initWebView() {
        X5Util.getInstance().setWebSettings(webView.getSettings());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        //TODO 配置支持全屏播放
        X5Util.getInstance().setCommonWebChromeClient(webView, normalView);
        //TODO 设置下载监听，比如网页中包含下载的东西，可自定义下载设置
        X5Util.getInstance().setDownloadListener(this, webView);
        webView.loadUrl("https://www.cnblogs.com/kaituorensheng/p/3776527.html#ctmk");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btOpenFileForPath:
                //TODO 打开本地文件
                String filePath = getFilePath();
                Log.d(TAG, "filePath: " + filePath);
                FileDisplayActivity.show(this, filePath);
                break;
            case R.id.btOpenFile:
                //TODO 浏览本也文件
                Intent intent = new Intent(this, FilechooserActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void copy() {
        // 开始复制
        String path = "file" + File.separator;
        FileUtil.getInstance().copyAssetsFileToAppFiles(this, path + "test.docx", "test.docx");
//        FileUtil.getInstance().copyAssetsFileToAppFiles(this, path + "test.pdf", "test.pdf");
//        FileUtil.getInstance().copyAssetsFileToAppFiles(this, path + "test.pptx", "test.pptx");
//        FileUtil.getInstance().copyAssetsFileToAppFiles(this, path + "test.txt", "test.txt");
//        FileUtil.getInstance().copyAssetsFileToAppFiles(this, path + "test.xlsx", "test.xlsx");
    }

    private String getFilePath() {
        return getFilesDir().getAbsolutePath() + File.separator + "test.docx";
    }
}
