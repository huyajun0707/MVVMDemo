package com.hyj.demo.demo0117;

import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 9:32
 * @description :
 * =========================================================
 */
public class Test2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("44");
//        for (String element : list) {
//            if (element.equals("22"))
//                list.remove(element);
//        }
        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).equals("44"))
                list.remove(3);
//            if (list.get(i).equals("22"))
                list.remove(2);

//            list.remove(i);
        }


    }
}
