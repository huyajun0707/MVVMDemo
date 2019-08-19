package com.hyj.demo.demo0117;

import android.os.Looper;

import java.lang.reflect.InvocationHandler;
import java.util.concurrent.TransferQueue;

import javax.xml.transform.Transformer;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/8/12 16:26
 * @description :
 * =========================================================
 */
public class Loop {

    public static void main(String[] args) {
        //
        //        Thread thread = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                Looper.prepare();
        //            }
        //        });
        Bank bank = new Bank();
        Transfer t = new Transfer(bank);
        Thread t1 = new Thread(t);
        t1.start();
        Thread t2 = new Thread(t);
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bank.get());
    }

    static class Transfer implements Runnable {
        Bank bank;

        public Transfer(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                bank.set();
                System.out.println(Thread.currentThread() + "" + bank.get());
            }
        }
    }

    static class Bank {
        ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 100;
            }
        };

        public int get() {
            return t.get();
        }

        public void set() {
            t.set(t.get() + 10);
        }

    }

}
