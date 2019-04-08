package com.hyj.demo.unittestdemo;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/3 14:07
 * @description :
 * =========================================================
 */
public class Bind {
    public static void bind(Activity activity){
        for (Field field:activity.getClass().getDeclaredFields()){
            BindVew bindVew = field.getAnnotation(BindVew.class);
            if(bindVew!=null){
                try {
                    field.set(activity,activity.findViewById(bindVew.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
