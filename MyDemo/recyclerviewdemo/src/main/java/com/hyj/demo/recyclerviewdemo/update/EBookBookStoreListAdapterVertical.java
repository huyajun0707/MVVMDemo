//package com.hyj.demo.recyclerviewdemo.update;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import dw.ebook.R;
//import dw.ebook.entity.EBookBookStoreListEntity;
//import dw.ebook.entity.EBookDownloadEntity;
//import dw.ebook.util.inter.EBookBookStoreDownloadInter;
//import dw.ebook.view.activity.EBookAllBooksListActivity;
//
///**
// * 外部adapter
// */
//public class EBookBookStoreListAdapterVertical extends RecyclerView.Adapter<EBookBookStoreListAdapterVertical.ViewHolder> {
//    Context mContext;
//    private EBookBookStoreListEntity mDatas;
//    private EBookBookStoreListAdapterHorizontal adapterHorizontal;
//    private GridLayoutManager gridLayoutManager;
//    private EBookBookStoreDownloadInter downloadInter;
//
//    private List<EBookBookStoreListAdapterHorizontal> adapters = new ArrayList<>();
//
//    public EBookBookStoreListAdapterVertical(Context mContext, EBookBookStoreListEntity mDatas, EBookBookStoreDownloadInter downloadInter) {
//        this.mContext = mContext;
//        this.mDatas = mDatas;
//        this.downloadInter = downloadInter;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ebook_book_store_list_vertical, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.book_store_list_source.setText(mDatas.getBookStoreDataEntityList().get(position).getTitle());
//        holder.book_store_list_horizontal_recycle.setHasFixedSize(true);//设置固定大小
//        holder.book_store_list_horizontal_recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
//        gridLayoutManager = new GridLayoutManager(mContext, 1);
//        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
//        holder.book_store_list_horizontal_recycle.setLayoutManager(gridLayoutManager);
//        adapterHorizontal = new EBookBookStoreListAdapterHorizontal(mContext, mDatas.getBookStoreDataEntityList().get(position).getBookDataEntityList(),
//                position, downloadInter, mDatas.getBookStoreDataEntityList());
//        adapters.add(adapterHorizontal);
//        holder.book_store_list_horizontal_recycle.setAdapter(adapterHorizontal);
//
//        holder.book_store_list_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EBookAllBooksListActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas == null ? 0 : mDatas.getBookStoreDataEntityList().size();
//    }
//
////    public void setProgress(EBookDownloadEntity eBookDownloadEntity) {
////        this.eBookDownloadEntity = eBookDownloadEntity;
////
////    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView book_store_list_source;
//        public TextView book_store_list_more;
//        public RecyclerView book_store_list_horizontal_recycle;
//
//        public ViewHolder(View v) {
//            super(v);
//            book_store_list_source = v.findViewById(R.id.book_store_list_source);
//            book_store_list_more = v.findViewById(R.id.book_store_list_more);
//            book_store_list_horizontal_recycle = v.findViewById(R.id.book_store_list_horizontal_recycle);
//        }
//    }
//
//    public List<EBookBookStoreListAdapterHorizontal> getAdapters() {
//        return adapters;
//    }
//
//}
