package com.hyj.demo.demo0117.encrypt;

import java.io.UnsupportedEncodingException;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/6/20 10:55
 * @description :
 * =========================================================
 */
public class EnctyptTest {

    public static void main(String[] args) {
        String movieName = "影王少帅的女长官";
        String enctypt1 = ("base64-" + Base64Util.encode(movieName.getBytes()));
        String enctypt2 = "jiami";
        String decode1 = Base64Util.decodeMovieName(enctypt1);
        String decode2 = Base64Util.decodeMovieName(enctypt2);
        System.out.println("enctypt：" + enctypt1);
        System.out.println("decode1：" + decode1);
        System.out.println("decode2：" + decode2);


    }
}
