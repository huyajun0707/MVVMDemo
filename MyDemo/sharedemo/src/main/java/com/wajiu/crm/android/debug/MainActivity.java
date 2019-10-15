package com.wajiu.crm.android.debug;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btShareImg, btShareFile, btShareVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btShareImg = findViewById(R.id.btShareImg);
        btShareFile = findViewById(R.id.btShareFile);
        btShareVideo = findViewById(R.id.btShareVideo);
        btShareImg.setOnClickListener(this);
        btShareFile.setOnClickListener(this);
        btShareVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        File directory = Environment.getExternalStoragePublicDirectory("Wallet");
        File file = null;
        ShareType shareType = null;
        switch (v.getId()) {
            case R.id.btShareImg:
                shareType = ShareType.SHARE_IMAGE;
                file = new File(directory, "1553757630467.jpg");
//                file = new File(directory, "1528263655086.jpg");
                break;
            case R.id.btShareFile:
                shareType = ShareType.SHARE_FILE;
                file = new File(directory, "apolloWebviewConfig.txt");
                break;
            case R.id.btShareVideo:
                shareType = ShareType.SHARE_VIDEO;
                file = new File(directory, "VID_20190406_165806.mp4");
//                file = new File(directory, "f3d53e4dbbc818970ff4b51c3f29aad0.mp4");
                break;
        }

            ShareSDKUtil.getInstance().createShareView(MainActivity.this, v, shareType, file
                    .getPath());

    }

}
