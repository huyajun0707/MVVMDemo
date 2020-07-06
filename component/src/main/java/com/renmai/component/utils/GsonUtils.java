package com.renmai.component.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 解析json类
 * Created by puhui-hebin on 2017/5/22.
 */
public class GsonUtils {
    //获取gson对象
    public static Gson getGson() {
        return new Gson();
    }

    //获取一个实体类
    public static <T> T getModel(String json, Class<T> cla) {
        return getGson().fromJson(json, cla);
    }

    //获取一个实体类
    public static <T> T getModel(JsonElement json, Class<T> cla) {
        return getGson().fromJson(json, cla);
    }

    public static JsonObject getJsonObject(JsonObject jsonObject, String key) {
        if (jsonObject.has(key) && !jsonObject.get(key).isJsonNull()) {
            return jsonObject.getAsJsonObject(key);
        }
        return null;
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    /**
     * 当根据字段来解析json时候加上是否有对应的key，以及获取到的值是否为null的判断，如果有异常情况会给出默认值
     */
    public static int getInt(JsonObject obj, String key) {
        return obj.has(key) ? (obj.get(key).isJsonNull() ? -1 : obj.get(key).getAsInt()) : -1;
    }

    public static String getString(JsonObject obj, String key) {
        return obj.has(key) ? (obj.get(key).isJsonNull() ? "" : obj.get(key).getAsString()) : "";
    }

    public static long getLong(JsonObject obj, String key) {
        return obj.has(key) ? (obj.get(key).isJsonNull() ? -1 : obj.get(key).getAsLong()) : -1;
    }

    public static double getDouble(JsonObject obj, String key) {
        return obj.has(key) ? (obj.get(key).isJsonNull() ? -1 : obj.get(key).getAsDouble()) : -1;
    }

    public static boolean getBoolean(JsonObject obj, String key) {
        return obj.has(key) && (!obj.get(key).isJsonNull() && obj.get(key).getAsBoolean());
    }

    //根据json字符串获取jsonObject
    public static JsonObject getJsonObject(String body) {
        JsonObject object;
        object = new JsonParser().parse(body).getAsJsonObject();
        return object;
    }

    /**
     * 转换时可以忽略指定的属性
     *
     * @param src
     * @param strategy
     * @return
     */
    public static String toJsonByStrategy(Object src, final String strategy) {
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().equals(strategy); //
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

        };
        return new GsonBuilder()
                .setExclusionStrategies(exclusionStrategy) //
                .create().toJson(src);
    }

    /**
     * 去除转义
     *
     * @param src
     * @return
     */
    public static String toJsonDisableHtmlEscaping(Object src) {

        return new GsonBuilder()
                .disableHtmlEscaping()//去除转义  防止网址被转义
                .create().toJson(src);
    }


}
