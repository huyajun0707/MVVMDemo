//package com.hyj.demo.recyclerviewdemo.update;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.List;
//
//import dw.ebook.R;
//import dw.ebook.db.DB;
//import dw.ebook.entity.Download;
//import dw.ebook.entity.EBookBookDataEntity;
//import dw.ebook.entity.EBookBookStoreDataEntity;
//import dw.ebook.entity.EBookDownloadEntity;
//import dw.ebook.util.EBookConstants;
//import dw.ebook.util.EBookDwGetImage;
//import dw.ebook.util.inter.EBookBookStoreDownloadInter;
//import dw.ebook.view.activity.EBookDirectoryDetailsActivity;
//
///**
// * 内部adapter
// */
//public class EBookBookStoreListAdapterHorizontal extends RecyclerView.Adapter<EBookBookStoreListAdapterHorizontal.ViewHolder> {
//    Context mContext;
//    private List<EBookBookDataEntity> mDatas;
//    private List<EBookBookStoreDataEntity> sourceList;
//    private EBookBookStoreDownloadInter downloadInter;
//    private int sourcePosition;
//    private String urlType;
//    private String url;
//
//    public EBookDownloadEntity eBookDownloadEntity;
//
//    public EBookBookStoreListAdapterHorizontal(Context mContext, List<EBookBookDataEntity> mDatas, int sourcePosition, EBookBookStoreDownloadInter downloadInter, List<EBookBookStoreDataEntity> sourceList) {
//        this.mContext = mContext;
//        this.mDatas = mDatas;
//        this.downloadInter = downloadInter;
//        this.sourcePosition = sourcePosition;
//        this.sourceList = sourceList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ebook_book_store_list_item, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        EBookDwGetImage.getImage(holder.item_cover_image, mContext, mDatas.get(position).getCover_img());
//        holder.item_progrss.setProgress(Integer.parseInt(mDatas.get(position).getProgress()));
//
//
////        List<Download> downloadList = DB.getDownloads(EBookConstants.UID);
////        for (int i = 0; i < downloadList.size(); i++) {
////            if (EBookConstants.FREE_ZIP.equals(urlType))
////                url = mDatas.get(position).getFree_download_url();
////            else
////                url = mDatas.get(position).getAuth_download_url();
////            if (EBookConstants.COMPLETED.equals(downloadList.get(i).status) && downloadList.get(i).download_url.equals(url) && "11".equals(EBookConstants.UID)) {
////                holder.item_progrss.setProgress(100);
////                holder.item_read.setVisibility(View.VISIBLE);
////            } else {
////                holder.item_progrss.setProgress(Integer.parseInt(mDatas.get(position).getProgress()));
////            }
////        }
//
//
//        if ("100".equals(sourceList.get(sourcePosition).getBookDataEntityList().get(position).getProgress())) {
//            holder.item_read.setVisibility(View.VISIBLE);
//        }
//
//        if (TextUtils.isEmpty(mDatas.get(position).getAuth_download_url()))
//            holder.item_download_auth.setVisibility(View.GONE);
//        else
//            holder.item_download_auth.setVisibility(View.VISIBLE);
//        if (TextUtils.isEmpty(mDatas.get(position).getFree_download_url()))
//            holder.item_download_free.setVisibility(View.GONE);
//        else
//            holder.item_download_free.setVisibility(View.VISIBLE);
//
//        holder.item_download_free.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                downloadInter.onDownloadClick(sourcePosition, position, EBookConstants.FREE_ZIP);
//                mDatas = sourceList.get(sourcePosition).getBookDataEntityList();
//                urlType = EBookConstants.FREE_ZIP;
//            }
//        });
//        holder.item_download_auth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                downloadInter.onDownloadClick(sourcePosition, position, EBookConstants.AUTH_ZIP);
//                mDatas = sourceList.get(sourcePosition).getBookDataEntityList();
//                urlType = EBookConstants.AUTH_ZIP;
//            }
//        });
//
//        holder.item_read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, EBookDirectoryDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", eBookDownloadEntity.getUnpath() + "index.html");
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//
//            }
//        });
//
//
//    }
//
//
//
////    public void getDownloads(){
////
////    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas == null ? 0 : mDatas.size();
//    }
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView item_cover_image;
//        public TextView item_read;
//        public TextView item_download_free;
//        public TextView item_download_auth;
//        public ProgressBar item_progrss;
//
//        public ViewHolder(View v) {
//            super(v);
//            item_cover_image = v.findViewById(R.id.item_cover_image);
//            item_read = v.findViewById(R.id.item_read);
//            item_download_free = v.findViewById(R.id.item_download_free);
//            item_download_auth = v.findViewById(R.id.item_download_auth);
//            item_progrss = v.findViewById(R.id.item_progrss);
//        }
//    }
//
//}
