package com.hyj.demo.viewpagerdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/29 17:30
 * @description :   listView
 * =========================================================
 */
public class MyFragment2 extends Fragment {
    private String title;
    private TextView tvTitle;
    private MyWebview webview;

    public static MyFragment2 newInstance(String title, String content) {
        Bundle args = new Bundle();
        args.putString("title", title);
        MyFragment2 myFragment = new MyFragment2();
        myFragment.setArguments(args);
        return myFragment;
    }

    public MyFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my2, container, false);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        webview = rootView.findViewById(R.id.webview);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        tvTitle.setText(title);
        initWebView();
        return rootView;
    }

    private void initWebView() {
        String url = "https://i.ifeng.com/";
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webview.loadUrl(url);
                return true;
            }

        });
    }

}
