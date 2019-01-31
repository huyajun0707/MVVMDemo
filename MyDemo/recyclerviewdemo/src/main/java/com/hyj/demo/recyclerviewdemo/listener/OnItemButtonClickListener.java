package com.hyj.demo.recyclerviewdemo.listener;

import android.view.View;

import com.hyj.demo.recyclerviewdemo.entity.Book;

/**
 * Created by huyj on 2017/9/6.
 */

public interface OnItemButtonClickListener {

    void onItemButtonClick(Book book, int position);

}
