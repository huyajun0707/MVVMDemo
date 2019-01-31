//package com.hyj.demo.recyclerviewdemo.update;
//
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//
//import com.google.gson.Gson;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import dw.ebook.R;
//import dw.ebook.adapter.EBookBookStoreListAdapterVertical;
//import dw.ebook.db.DB;
//import dw.ebook.entity.Download;
//import dw.ebook.entity.EBookBookStoreListEntity;
//import dw.ebook.entity.EBookDownloadEntity;
//import dw.ebook.presenter.EBookBookStoreListPresenter;
//import dw.ebook.util.EBookConstants;
//import dw.ebook.util.inter.EBookBookStoreDownloadInter;
//import dw.ebook.util.DownService;
//import dw.ebook.view.inter.EBookBookStoreListView;
//
//
//public class EBookBookStoreListFragment extends EBookBaseFragment implements EBookBookStoreListView {
//    private RecyclerView book_store_list_recycle;
//    private LinearLayoutManager mLayoutManage;
//    private EBookBookStoreListEntity mDatas;
//    private EBookBookStoreListAdapterVertical adapterVertical;
//    private EBookBookStoreListPresenter eBookBookStoreListPresenter;
//
//    private DownService downService;
//
//    private String source_id;
//    private String book_id;
//    private String is_trial_reading;
//
//    public EBookBookStoreListFragment() {
//        this.setrId(R.layout.ebook_fragment_book_store_list);
//    }
//
//    @Override
//    public void initFragment() {
//        initView();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DownService.getInstance().init(getActivity());
//        EventBus.getDefault().register(this);
//    }
//
//    private void initView() {
//        book_store_list_recycle = getRootView().findViewById(R.id.book_store_list_recycle);
//        book_store_list_recycle.setHasFixedSize(true);//设置固定大小
//        book_store_list_recycle.setItemAnimator(new DefaultItemAnimator());//设置默认动画
//        mLayoutManage = new LinearLayoutManager(getContext());
//        mLayoutManage.setOrientation(OrientationHelper.VERTICAL);
//        book_store_list_recycle.setLayoutManager(mLayoutManage);
//        if (eBookBookStoreListPresenter == null)
//            eBookBookStoreListPresenter = new EBookBookStoreListPresenter();
//        eBookBookStoreListPresenter.attachView(this);
//        eBookBookStoreListPresenter.getBookStoreList(
//                "hk01_app",
//                EBookConstants.UID,
//                "",
//                "",
//                "android");
//
//        downService = DownService.getInstance();
//
//
//    }
//
//    @Override
//    public void onSuccessGetBookStoreList(EBookBookStoreListEntity bookStoreList) {
//        mDatas = bookStoreList;
//        if (adapterVertical == null)
//            adapterVertical = new EBookBookStoreListAdapterVertical(getContext(), mDatas, new EBookBookStoreDownloadInter() {
//
//                @Override
//                public void onDownloadClick(int sourcePosttion, int position, String type) {
//                    String url = mDatas.getBookStoreDataEntityList().get(sourcePosttion).getBookDataEntityList().get(position).getFree_download_url();
//                    book_id = mDatas.getBookStoreDataEntityList().get(sourcePosttion).getBookDataEntityList().get(position).getBook_id();
//                    source_id = mDatas.getBookStoreDataEntityList().get(sourcePosttion).getBookDataEntityList().get(position).getSource_id();
//
//                    String path = Environment.getExternalStorageDirectory().getPath() + "/ebook/";
//                    if ((EBookConstants.AUTH_ZIP).equals(type)) {
//                        //收费
//                        path = path + "1/" + source_id + "/" + book_id + "/";
//                        is_trial_reading = "1";
//                    } else {
//                        //免费
//                        path = path + "0/" + source_id + "/" + book_id + "/";
//                        is_trial_reading = "0";
//                    }
//                    createFile(path);
//
//                    Log.e("点击下载", "path" + path);
//                    Log.e("点击下载", "url" + url);
//                    Log.e("点击下载", "bookid" + book_id);
//                    Log.e("点击下载", "sourceid" + source_id);
//
//                    Download download = new Download();
//                    download.uid = EBookConstants.UID;
//                    download.source_id = source_id;
//                    download.file_size = "";
//                    download.book_id = book_id;
//                    download.status = EBookConstants.PENDING;
//                    download.finish_size = "";
//                    download.is_trial_reading = is_trial_reading;
//                    download.download_url = url;
//                    download.add_download_timestamp = String.valueOf(System.currentTimeMillis());
//                    DB.saveDownload(download);
//
//                    downService.start(url, path);
//                }
//
//            });
//        book_store_list_recycle.setAdapter(adapterVertical);
//    }
//
//    @Override
//    public void onErrorGetBookStoreList() {
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(String json) {
//        Log.i("ebook", "Event" + json);
//        Gson gson = new Gson();
//        EBookDownloadEntity eBookDownloadEntity = gson.fromJson(json, EBookDownloadEntity.class);
//        eBookDownloadEntity.setSourceId(source_id);
//        eBookDownloadEntity.setBookId(book_id);
//
//        if (EBookConstants.CONNECTED.equals(eBookDownloadEntity.getMessage())) {
//            Download download = new Download();
//            download.uid = EBookConstants.UID;
//            download.source_id = source_id;
//            download.file_size = eBookDownloadEntity.getTotal();
//            download.book_id = eBookDownloadEntity.getBookId();
//            download.status = eBookDownloadEntity.getMessage();
//            download.finish_size = eBookDownloadEntity.getSoFarBytes();
//            download.is_trial_reading = is_trial_reading;
//            download.download_url = eBookDownloadEntity.getUrl();
//            download.add_download_timestamp = String.valueOf(System.currentTimeMillis());
//            DB.saveDownload(download);
//        }
//
//        if (EBookConstants.COMPLETED.equals(eBookDownloadEntity.getMessage())) {
//            Download download = new Download();
//            download.uid = EBookConstants.UID;
//            download.source_id = source_id;
//            download.file_size = eBookDownloadEntity.getTotal();
//            download.book_id = eBookDownloadEntity.getBookId();
//            download.status = eBookDownloadEntity.getMessage();
//            download.finish_size = eBookDownloadEntity.getSoFarBytes();
//            download.is_trial_reading = is_trial_reading;
//            download.download_url = eBookDownloadEntity.getUrl();
//            download.add_download_timestamp = String.valueOf(System.currentTimeMillis());
//            DB.saveDownload(download);
//        }
//        for (int i = 0; i < mDatas.getBookStoreDataEntityList().size(); i++) {
//            for (int j = 0; j < mDatas.getBookStoreDataEntityList().get(i).getBookDataEntityList().size(); j++) {
//                if (mDatas.getBookStoreDataEntityList().get(i).getBookDataEntityList().get(j).getBook_id().equals(eBookDownloadEntity.getBookId()) &&
//                        mDatas.getBookStoreDataEntityList().get(i).getBookDataEntityList().get(j).getSource_id().equals(eBookDownloadEntity.getSourceId())) {
//                    mDatas.getBookStoreDataEntityList().get(i).getBookDataEntityList().get(j).setProgress(eBookDownloadEntity.getProgress);
//
//                    adapterVertical.getAdapters().get(i).notifyItemChanged(j);
//                }
//            }
//        }
//
////        adapterVertical.setProgress(eBookDownloadEntity);
//    }
//
//    public void createFile(String fileName) {
//        File file = new File(fileName);
//        if (fileName.indexOf(".") != -1) {
//            // 说明包含，即使创建文件, 返回值为-1就说明不包含.,即使文件
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println("创建了文件");
//        } else {
//            // 创建文件夹
//            file.mkdir();
//            System.out.println("创建了文件夹");
//        }
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        DownService.getInstance().onDestroy(getActivity());
//        EventBus.getDefault().unregister(this);
//    }
//}
