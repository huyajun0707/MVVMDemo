package com.hyj.demo.demo0117;

import java.util.Locale;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/1/17 9:39
 * @description :
 * =========================================================
 */
public class Test {
    public static void mian(String args[]) {
        String number = "1.235";
//        String numberFormat = String.format("%.2f", number);
        String numberFormat = String.format(Locale.CHINA, "%.2f", number);
        System.out.print(numberFormat);
    }

}
