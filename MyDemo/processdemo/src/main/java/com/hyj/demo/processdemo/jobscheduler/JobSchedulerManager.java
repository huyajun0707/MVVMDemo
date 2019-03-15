package com.hyj.demo.processdemo.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.hyj.demo.processdemo.constant.KeepAliveConstants;
import com.hyj.demo.processdemo.jobscheduler.service.AliveJobService;

import static android.app.job.JobScheduler.RESULT_SUCCESS;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/3/13 10:32
 * @description :
 * =========================================================
 */
public class JobSchedulerManager {
    private static final String TAG = "JobSchedulerManager";
    private static final int JOB_ID = 1;
    private static JobSchedulerManager mJobManager;
    private JobScheduler mJobScheduler;
    private static Context mContext;

    private JobSchedulerManager(Context ctxt) {
        this.mContext = ctxt;
        mJobScheduler = (JobScheduler) ctxt.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public final static JobSchedulerManager getJobSchedulerInstance(Context ctxt) {
        if (mJobManager == null) {
            mJobManager = new JobSchedulerManager(ctxt);
        }
        return mJobManager;
    }

    @TargetApi(21)
    public void startJobScheduler() {

        // 如果JobService已经启动或API<21，返回
        if (AliveJobService.isJobServiceAlive() || isBelowLOLLIPOP()) {
            return;
        }
        mJobScheduler.cancel(JOB_ID);
        // 构建JobInfo对象，传递给JobSchedulerService
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(mContext, AliveJobService.class));
        if (isOverN()) {
            Log.i(TAG, "AliveJobService 大于等于7.0，手动实现定时器效果");
            builder.setMinimumLatency(KeepAliveConstants.INTERVAL_WAKEUP); //执行的最小延迟时间
            builder.setOverrideDeadline(KeepAliveConstants.INTERVAL_WAKEUP);  //执行的最长延时时间
            builder.setMinimumLatency(KeepAliveConstants.INTERVAL_WAKEUP);
            builder.setBackoffCriteria(KeepAliveConstants.INTERVAL_WAKEUP, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            Log.i(TAG, "AliveJobService 大于5.0小于7.0，自动实现定时器效果");
            // 设置每3秒执行一下任务
            builder.setPeriodic(KeepAliveConstants.INTERVAL_WAKEUP);
        }
        // 设置设备重启时，执行该任务
        builder.setPersisted(true);
        // 当插入充电器，执行该任务
        builder.setRequiresCharging(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobInfo info = builder.build();
        //开始定时执行该系统任务
        int result = mJobScheduler.schedule(info);
        if (result == RESULT_SUCCESS) {
            Log.d(TAG, "startJobScheduler ------ success!!!");
        }else{
            Log.d(TAG, "startJobScheduler ------ fail!!!");
        }
    }

    @TargetApi(21)
    public void stopJobScheduler() {
        if (isBelowLOLLIPOP())
            return;
        mJobScheduler.cancelAll();
    }

    private boolean isBelowLOLLIPOP() {
        // API< 21
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    private boolean isOverN() {
        // API>= 24
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
