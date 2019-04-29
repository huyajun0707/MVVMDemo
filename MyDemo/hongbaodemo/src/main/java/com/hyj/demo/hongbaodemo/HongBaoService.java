package com.hyj.demo.hongbaodemo;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/26 14:13
 * @description :
 * =========================================================
 */
public class HongBaoService extends AccessibilityService {
    public String[] fiter = new String[]{"恭喜发财"};
    AccessibilityNodeInfo rootNodeInfo = null;
    private static final String TAG = "HongBaoService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: ");
        //事件监听 在微信窗体发生改变时会被调用
        rootNodeInfo = event.getSource();
        if (rootNodeInfo == null) {
            return;
        }
        Log.d(TAG, "onAccessibilityEvent: 检测到文字");
        startClick(rootNodeInfo);
    }

    private void startClick(AccessibilityNodeInfo rootNodeInfo) {
        List<AccessibilityNodeInfo> list = findByText(rootNodeInfo);
        if (list == null)
            return;
        AccessibilityNodeInfo nodeInfo = list.get(list.size() - 1);
        if (nodeInfo != null) {
            if (nodeInfo.getText().equals("已拆开")) {
                return;
            }
            //触发点击事件
            Log.d(TAG, "startClick: 触发点击事件");
            boolean isClick = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (!isClick) {
                recleClick(rootNodeInfo);
            }
        }

    }

    private void recleClick(AccessibilityNodeInfo rootNodeInfo) {
        int childCount = rootNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo info = rootNodeInfo.getChild(i);
            if (info == null)
                continue;
            if (info.getChildCount() > 0)
                recleClick(info);
            else
                rootNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    /**
     * 根据文字去查找是否有相关文字节点
     *
     * @param rootNodeInfo
     * @return
     */
    private List<AccessibilityNodeInfo> findByText(AccessibilityNodeInfo rootNodeInfo) {
        for (String name : fiter) {
            List<AccessibilityNodeInfo> list = rootNodeInfo.findAccessibilityNodeInfosByText(name);
            if (list != null && !list.isEmpty()) {
                return list;
            }
        }
        return null;
    }

    @Override
    public void onInterrupt() {

    }
}
