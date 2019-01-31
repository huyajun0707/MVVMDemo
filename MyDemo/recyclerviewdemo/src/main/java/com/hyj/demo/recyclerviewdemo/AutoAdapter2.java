package com.hyj.demo.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyj.demo.recyclerviewdemo.entity.Book;
import com.hyj.demo.recyclerviewdemo.entity.BookList;
import com.hyj.demo.recyclerviewdemo.listener.OnItemButtonClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/1/25 12:26
 * @description :
 * =========================================================
 */
public class AutoAdapter2 extends RecyclerView.Adapter<AutoAdapter2.ViewHolder> {
    private Context context;
    private List<Book> data;
    private OnItemButtonClickListener onItemButtonClickListener;


    public AutoAdapter2(Context context, List<Book> data, OnItemButtonClickListener onItemButtonClickListener) {
        this.context = context;
        this.data = data;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        holder.progressBar.setProgress(data.get(position).getProgress());
        holder.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemButtonClickListener != null) {
                    onItemButtonClickListener.onItemButtonClick(data.get(position), position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ProgressBar progressBar;
        private Button btDownload;
        private ImageView ivIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            name = itemView.findViewById(R.id.tvTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
            btDownload = itemView.findViewById(R.id.btDownload);
        }
    }

}
