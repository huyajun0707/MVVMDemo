package com.hyj.demo.processdemo.constant;

import android.os.Build;
import android.os.StatFs;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 15:18
 * @description :
 * =========================================================
 */
public class KeepAliveConstants {
    public static final int HIDE_FOREGROUND_SERVICE_STOP_DELAY = 2000; // 隐藏前台通知的延迟时间
    public static final int FOREGROUND_NOTIFICATION_ID = 13691; // 前台通知ID
    public static final String REMOTE_PROCESS_NAME = ":keepalive"; // 远程保活进程名
    public static final long INTERVAL_WAKEUP = 30000L;

    public static final String ACTION_FINISH_ONE_PIXEL_ACTIVITY = "ACTION_FINISH_ONE_PIXEL_ACTIVITY"; // 一像素界面关闭广播

    public static String FOREGROUND_NOTIFICATION_TITLE = ""; // 前台通知的标题
    public static String FOREGROUND_NOTIFICATION_DESCRIPTION = ""; // 前台通知的描述
    public static int FOREGROUND_NOTIFICATION_ICON_ID = 0; // 前台通知的图标

    // 7.0以下才开启前台服务
    public static boolean OPEN_FOREGROUND_SERVICE = Build.VERSION.SDK_INT <= Build.VERSION_CODES.N;
    // 5.0以上开启 job scheduler，5.0以下开启 alarm manager
    public static boolean OPEN_JOB_SCHEDULER_OR_ALARM_MANAGER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    // 7.0以上需要单独适配 job scheduler
    public static boolean MATCH_JOB_SCHEDULER_7 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    public static final boolean MEDIA_PLAYER_POWER = true; // 后台音乐播放省电模式
    public static final int MEDIA_PLAYER_DELAY = 10000; // 后台音乐播放间隔时间

}
