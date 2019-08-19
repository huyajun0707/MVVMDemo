package com.hyj.demo.demo0117;

import android.os.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/5/28 16:00
 * @description : 延迟初始化
 * =========================================================
 */
public class DelayInitDispatcher {
    private Queue<Task> delayTasks = new LinkedList<>();
    private MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {

        @Override
        public boolean queueIdle() {
            if(delayTasks.size()>0){
                Task task = delayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !delayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        delayTasks.add(task);
        return this;
    }

    class DispatchRunnable implements Runnable {
        public DispatchRunnable(Task task) {
        }

        @Override
        public void run() {

        }
    }

}
