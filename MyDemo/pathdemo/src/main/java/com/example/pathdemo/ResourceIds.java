package com.example.pathdemo;

/**
 * Created by zhangdan on 16/12/12.
 */

public interface ResourceIds {
    boolean knownIdName(String name);

    int idFromName(String name);

    String nameForId(int id);
}
