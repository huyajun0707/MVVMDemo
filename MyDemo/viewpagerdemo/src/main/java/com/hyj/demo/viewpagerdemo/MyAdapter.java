package com.hyj.demo.viewpagerdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/29 17:08
 * @description :
 * =========================================================
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<ItemInfo> itemInfos;

    public MyAdapter(List<ItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemInfo itemInfo = itemInfos.get(position);
        holder.tvName.setText(itemInfo.getName());
        holder.ivIcon.setImageResource(itemInfo.getIcon());
    }

    @Override
    public int getItemCount() {
        return itemInfos.size();
    }
}
