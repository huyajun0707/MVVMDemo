package com.hyj.demo.recyclerviewdemo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class AutoAdapter1 extends RecyclerView.Adapter<AutoAdapter1.ViewHolder> {
    private Context context;
    private List<BookList> data;
    private OnItemButtonClickListener onItemButtonClickListener;
    private List<AutoAdapter2> autoAdapter2s = new ArrayList<>();

    public AutoAdapter1(Context context, List<BookList> data, OnItemButtonClickListener onItemButtonClickListener) {
        this.context = context;
        this.data = data;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getTitle());
        AutoAdapter2 autoAdapter2 = new AutoAdapter2(context, data.get(position).getBooks(), onItemButtonClickListener);
        autoAdapter2s.add(autoAdapter2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(autoAdapter2);

    }

    public List<AutoAdapter2> getAutoAdapter2s() {
        return autoAdapter2s;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvTitle);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

}
