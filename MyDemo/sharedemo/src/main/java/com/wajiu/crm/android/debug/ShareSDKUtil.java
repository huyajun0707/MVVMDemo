package com.wajiu.crm.android.debug;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import com.wajiu.crm.android.debug.listener.OnShareItemClickListener;
import com.wajiu.crm.android.debug.view.SharePopuWindow;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/8 17:09
 * @description :  https://blog.csdn.net/system_err/article/details/77048503
 * =========================================================
 */
public class ShareSDKUtil {
    private static ShareSDKUtil mShareSDKUtil;
    private static Handler mHandler = new Handler();
    private static String PACKAGE_QQ = "com.tencent.mobileqq";
    private static String QQ_JUMP_NAME = "com.tencent.mobileqq.activity.JumpActivity";
    private static String PACKAGE_WECHAT = "com.tencent.mm";
    private static String WECHAT_JUMP_NAME = "com.tencent.mm.ui.tools.ShareImgUI";
    private SharePopuWindow sharePopuWindow;
    private String FILE_PROVIDER = "com.wajiu.crm.android.debug.provider";

    private ShareSDKUtil() {
        // cannot be instantiated
    }

    public static synchronized ShareSDKUtil getInstance() {
        if (mShareSDKUtil == null) {
            mShareSDKUtil = new ShareSDKUtil();
        }
        return mShareSDKUtil;
    }

    public static void releaseInstance() {
        if (mShareSDKUtil != null) {
            mShareSDKUtil = null;
        }
    }

    public void createShareView(final Activity activity, View parent, final ShareType shareType,
                                final String path) {
        sharePopuWindow = new SharePopuWindow(activity);
        sharePopuWindow.setOnItemClickListener(new OnShareItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        if (shareType == ShareType.SHARE_IMAGE)
                            shareToQQ(QQ.NAME, null, null, null, null, null, path, null, null);
                        else if (shareType == ShareType.SHARE_VIDEO || shareType == ShareType
                                .SHARE_FILE)
                            sendFile(activity, PACKAGE_QQ, QQ_JUMP_NAME, path);
                        else
                            showError();
                        break;
                    case 1:
                        if (shareType == ShareType.SHARE_IMAGE)
                            shareToQQ(QZone.NAME, null, null, null, null, null, path, null, null);
                        else if (shareType == ShareType.SHARE_VIDEO)
                            shareToQQ(QZone.NAME, null, null, null, null, null, null, path, null);
                        else
                            showError();
                        break;
                    case 2:
                        shareToWechat(activity, shareType, path, Wechat.NAME);
                        break;
                    case 3:
                        shareToWechat(activity, shareType, path, WechatMoments.NAME);
                        break;
                }
                //TODO
//                sharePopuWindow.dismiss();
            }
        });
        //显示窗口
        sharePopuWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置layout在PopupWindow中显示的位置
    }

    private void showError() {
        Toast.makeText(MyApplication.getInstance(), "暂不支持该类型分享", Toast.LENGTH_SHORT).show();
    }

    private void shareToWechat(Context context, ShareType shareType, String path, String
            wechatType) {
        if (shareType == ShareType.SHARE_IMAGE)
            shareToWechat(wechatType, Platform.SHARE_IMAGE, null, null, null, null, null,
                    path, null, null);
        else if (wechatType.equals(Wechat.NAME) && (shareType == ShareType.SHARE_FILE ||
                shareType == ShareType.SHARE_VIDEO))
            sendFile(context, PACKAGE_WECHAT, WECHAT_JUMP_NAME, path);
        else
            showError();
    }

    /**
     * 使用默认的分享GUI
     *
     * @param title         标题
     * @param content       内容
     * @param imgUrl        图片的Url
     * @param imgPath       本地图片的Url
     * @param titleUrl      网站的Url
     * @param shareListener 分享的回调
     */
    public void defaultShare(Context context, String title, String content,
                             String imgUrl, String imgPath, String titleUrl, String filePath,
                             PlatformActionListener
                                     shareListener) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        //oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(imgPath);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(titleUrl);
        oks.setFilePath(filePath);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content + "\n链接: " + titleUrl);
        //设置回调
        oks.setCallback(shareListener);
        // 启动分享GUI
        oks.show(context);
    }


    /**
     * 分享到微信好友或朋友圈,图片可以是ur或者是本地的，默认本地图片优先
     *
     * @param wechatType    微信好友或朋友圈 Wechat.NAME 微信好友 WechatMoments.NAME 朋友圈
     * @param shareType     分享的类型 Platform.SHARE_WEBPAGE 网页 Platform.SHARE_IMAGE 图片
     * @param title         标题
     * @param content       内容
     * @param siteUrl       网站的url
     * @param imgUrl        网络图片的url
     * @param bm            bitmap
     * @param imgPath       本地图片的绝对路径
     * @param shareListener 分享的回调
     */
    public void shareToWechat(String wechatType, int shareType, String title, String
            content, String siteUrl, String imgUrl, Bitmap bm, String imgPath,
                              String filePath, PlatformActionListener shareListener) {
        Wechat.ShareParams shareParams = new Wechat.ShareParams();
        shareParams.setShareType(shareType);
        shareParams.setTitle(title);//设置标题
        shareParams.setText(content);//设置内容
        shareParams.setUrl(siteUrl);//设置网站

        if (!TextUtils.isEmpty(imgUrl)) {
            //如果有网络图片，则设置图片的url
            shareParams.setImageUrl(imgUrl);
        }

        if (bm != null) {
            //如果是bitmap类型，则设置bm
            shareParams.setImageData(bm);
        }

        if (!TextUtils.isEmpty(imgPath)) {
            //如果是本地图片路径，则设置图片路径
            shareParams.setImagePath(imgPath);
        }

        if (!TextUtils.isEmpty(filePath)) {
            Log.d("uri", filePath);
//            //如果是本地文件路径，则设置图片路径
            shareParams.setFilePath(filePath);
        }

        Platform platform = ShareSDK.getPlatform(wechatType);
        platform.share(shareParams);//分享
        if (shareListener != null) {
            platform.setPlatformActionListener(shareListener);//设置回调
        } else {
            //如果为空，设置默认回调回调
            platform.setPlatformActionListener(defaultShareListner);
        }
    }

    /**
     * 分享到QQ，QQ空间
     *
     * @param title         标题
     * @param content       内容
     * @param siteUrl       网站的url
     * @param imgUrl        网络图片的url
     * @param bm            bitmap
     * @param imgPath       本地图片的绝对路径
     * @param shareListener 分享的回调
     */
    public void shareToQQ(String qqType, String title, String content, String siteUrl, String
            imgUrl, Bitmap bm, String imgPath, String filePath, PlatformActionListener
                                  shareListener) {

        QQ.ShareParams shareParams = new QQ.ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(title);//标题
        shareParams.setText(content);//内容
        shareParams.setTitleUrl(siteUrl);//网址
        if (!TextUtils.isEmpty(filePath)) {
            shareParams.setShareType(Platform.SHARE_VIDEO);
            //如果有网络图片，则设置图片的url
            shareParams.setFilePath(filePath);
        }
        // filePath是图片的本地路径
        if (!TextUtils.isEmpty(imgUrl)) {
            //如果有网络图片，则设置图片的url
            shareParams.setImageUrl(imgUrl);
        }

        if (bm != null) {
            //如果是bitmap类型，则设置bm
            shareParams.setImageData(bm);
        }

        if (!TextUtils.isEmpty(imgPath)) {
            //如果是本地图片路径，则设置图片路径
            shareParams.setImagePath(imgPath);
        }

        Platform platform = ShareSDK.getPlatform(qqType);
        platform.share(shareParams);//分享
        if (shareListener != null) {
            platform.setPlatformActionListener(shareListener);//设置回调
        } else {
            //如果为空，设置默认回调回调
            platform.setPlatformActionListener(defaultShareListner);
        }
    }

    private void sendFile(Context context, String packageName, String jumpName, String filePath) {
        if (packageName.equals(PACKAGE_QQ) && !isInstallApp(context, PACKAGE_QQ)) {
            Toast.makeText(context, "您需要安装QQ客户端", Toast.LENGTH_LONG).show();
            return;
        }
        if (packageName.equals(PACKAGE_WECHAT) && !isInstallApp(context, PACKAGE_WECHAT)) {
            Toast.makeText(context, "您需要安装微信客户端", Toast.LENGTH_LONG).show();
            return;
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName(packageName, jumpName);
        share.setComponent(component);
        share.putExtra(Intent.EXTRA_STREAM, getFileUri(context, filePath));
        share.setType("*/*");
        context.startActivity(Intent.createChooser(share, "发送"));
    }


    private Uri getFileUri(Context context, String filePath) {
        File file = new File(filePath);
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(context, FILE_PROVIDER, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private boolean isInstallApp(Context context, String app_package) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                if (app_package.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 默认的分享回调
     */
    public PlatformActionListener defaultShareListner = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.getInstance(), "分享成功", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.getInstance(), "分享失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onCancel(Platform platform, int i) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.getInstance(), "分享已取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
