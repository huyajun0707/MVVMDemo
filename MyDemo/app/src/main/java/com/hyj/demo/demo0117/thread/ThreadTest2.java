package com.hyj.demo.demo0117.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/17 11:16
 * @description : 先执行完AB   再执行C（AB为异步线程）
 * =========================================================
 */
public class ThreadTest2 {
    public static void main(String[] args) {
        threadC.start();
    }


    private static Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问
    public static int state = 0;

    public static Thread threadA = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("A" + "come");
            for (int i = 0; i < 10; i++) {
                System.out.println("A" + i);
            }
        }
    });
    public static Thread threadB = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("B" + "come");
            for (int i = 0; i < 10; i++) {
                System.out.println("B" + i);
            }
        }
    });
    public static Thread threadC = new Thread(new Runnable() {
        @Override
        public void run() {
            threadA.start();
            threadB.start();
            try {
                threadA.join();
                threadB.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C" + "come");
        }
    });
}
