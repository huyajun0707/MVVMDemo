package com.hyj.load.sharedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtil.getInstance().defaultShare(MainActivity.this,
                        "分享",
                        "我是分享文本",
                        null,
                        null,
                        "http://sharesdk.cn", new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String,
                                    Object> hashMap) {

                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(Platform platform, int i) {

                            }
                        });
            }
        });

    }

//    OnekeyShare oks = new OnekeyShare();
//    // title标题，微信、QQ和QQ空间等平台使用
//                oks.setTitle("分享");
//    // titleUrl QQ和QQ空间跳转链接
//                oks.setTitleUrl("http://sharesdk.cn");
//    // text是分享文本，所有平台都需要这个字段
//                oks.setText("我是分享文本");
//    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//    // url在微信、微博，Facebook等平台中使用
//                oks.setUrl("http://sharesdk.cn");
//    // comment是我对这条分享的评论，仅在人人网使用
//                oks.setComment("我是测试评论文本");
//// 启动分享GUI
//                oks.show(MainActivity.this);
}
