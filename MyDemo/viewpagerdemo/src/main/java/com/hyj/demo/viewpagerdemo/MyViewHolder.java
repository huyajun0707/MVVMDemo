package com.hyj.demo.viewpagerdemo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/29 17:09
 * @description :
 * =========================================================
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;
    public ImageView ivIcon;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        ivIcon = itemView.findViewById(R.id.ivIcon);
    }
}
