package com.hyj.demo.processdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.PowerManager;
import android.text.TextUtils;

import com.hyj.demo.processdemo.alarmmode.NotificationClickReceiver;
import com.hyj.demo.processdemo.constant.KeepAliveConstants;

import java.util.Iterator;
import java.util.List;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 15:32
 * @description :
 * =========================================================
 */
public class Utils {
    public static boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        String packageName = context.getPackageName();
        return TextUtils.equals(processName, packageName);
    }

    public static void startForegroundSafety(Service service) {
        if (service == null) {
            return;
        }
        try {
            Intent intent = new Intent(service.getApplicationContext(), NotificationClickReceiver.class);
            intent.setAction(NotificationClickReceiver.CLICK_NOTIFICATION);
            Notification notification = NotificationUtils.createNotification(
                    service.getApplicationContext(),
                    KeepAliveConstants.FOREGROUND_NOTIFICATION_TITLE,
                    KeepAliveConstants.FOREGROUND_NOTIFICATION_DESCRIPTION,
                    KeepAliveConstants.FOREGROUND_NOTIFICATION_ICON_ID, intent);
            service.startForeground(KeepAliveConstants.FOREGROUND_NOTIFICATION_ID, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopForegroundSafety(Service service, boolean removeNotification) {
        if (service == null) {
            return;
        }
        try {
            service.stopForeground(removeNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServiceSafety(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        try {
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopServiceSafety(Service service) {
        if (service == null) {
            return;
        }
        try {
            service.stopSelf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startActivitySafety(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void finishSafety(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBroadcastSafety(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        try {
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerBroadcastReceiverSafety(Context context, BroadcastReceiver receiver,
                                                       IntentFilter filter) {
        if (context == null || receiver == null || filter == null) {
            return;
        }
        try {
            context.registerReceiver(receiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterBroadcastReceiverSafety(Context context, BroadcastReceiver receiver) {
        if (context == null || receiver == null) {
            return;
        }
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bindServiceSafety(Context context, Intent intent, ServiceConnection connection) {
        if (context == null || intent == null || connection == null) {
            return;
        }
        try {
            context.bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unbindServiceSafety(Context context, ServiceConnection connection) {
        if (context == null || connection == null) {
            return;
        }
        try {
            context.unbindService(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isServiceRunning(Context ctx, String className) {
        boolean isRunning = false;
        try {
            if (ctx == null || TextUtils.isEmpty(className)) {
                return false;
            }
            ActivityManager activityManager = (ActivityManager) ctx
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return false;
            }
            List<ActivityManager.RunningServiceInfo> servicesList = activityManager
                    .getRunningServices(Integer.MAX_VALUE);
            if (servicesList == null) {
                return false;
            }
            Iterator<ActivityManager.RunningServiceInfo> l = servicesList.iterator();
            while (l.hasNext()) {
                ActivityManager.RunningServiceInfo si = l.next();
                if (TextUtils.equals(className, si.service.getClassName())) {
                    isRunning = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }

    public static boolean isRunningTaskExist(Context context, String processName) {
        try {
            if (context == null || TextUtils.isEmpty(processName)) {
                return false;
            }
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
            if (processList == null) {
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo info : processList) {
                if (TextUtils.equals(info.processName, processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isForeground(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return false;
            }
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (tasks != null && !tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (TextUtils.equals(topActivity.getPackageName(), context.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isScreenOn(Context context) {
        if (context == null) {
            return false;
        }
        try {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm == null) {
                return false;
            }
            return pm.isScreenOn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
