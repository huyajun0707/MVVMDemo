package com.example.roomdemo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * @author ： HuYajun <huyajun0707@gmail.com>
 * @version ： 1.0
 * @date ： 2019-10-14 20:50
 * @depiction ：
 */

public class Address {

    public String street;
    public String state;
    public String city;

    @ColumnInfo(name = "post_code")
    public int postCode;
}
