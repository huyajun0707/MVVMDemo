package com.hyj.demo.demo0117.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/17 9:35
 * @description : ABC按顺序执行
 * =========================================================
 */
public class ThreadTest {
    public static void main(String args[]) {
        threadA.start();
        threadB.start();
        threadC.start();

    }

    private static Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问
    public static int state = 0;

    public static Thread threadA = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "come");
            for (int i = 0; i < 10; ) {
                lock.lock();
                if (state % 3 == 0) {
                    System.out.println("A" + i);
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }
    });
    public static Thread threadB = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "come");
            for (int i = 0; i < 10; ) {
                lock.lock();
                if (state % 3 == 1) {
                    System.out.println("B" + i);
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }
    });
    public static Thread threadC = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "come");
            for (int i = 0; i < 10; ) {
                lock.lock();
                if (state % 3 == 2) {
                    System.out.println("C" + i);
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }
    });
}
