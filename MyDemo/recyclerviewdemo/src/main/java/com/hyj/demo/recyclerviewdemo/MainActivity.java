package com.hyj.demo.recyclerviewdemo;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hyj.demo.recyclerviewdemo.entity.Book;
import com.hyj.demo.recyclerviewdemo.entity.BookList;
import com.hyj.demo.recyclerviewdemo.entity.DownloadInfo;
import com.hyj.demo.recyclerviewdemo.listener.OnItemButtonClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AutoAdapter1 autoAdapter1;
    List<BookList> data;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    //主线程处理
                    DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
                    Log.e("下载", "进度" + downloadInfo.getProgress());
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).getBooks().size(); j++) {
                            if (downloadInfo.getTypeId() == data.get(i).getBooks().get(j).getTypeId() && downloadInfo.getBookId() == data.get(i).getBooks().get(j).getBookId()) {
                                data.get(i).getBooks().get(j).setProgress(downloadInfo.getProgress());
                                Log.d("更改当前进度",
                                        "typeId:" + downloadInfo.getTypeId() + ",bookId:" + downloadInfo.getBookId() + "--->" + downloadInfo.getProgress());
                                autoAdapter1.getAutoAdapter2s().get(i).notifyItemChanged(j);
                            }
                        }
                    }
                    break;
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvMainHeaderMenu);
        initData();
        autoAdapter1 = new AutoAdapter1(this, data, new OnItemButtonClickListener() {
            @Override
            public void onItemButtonClick(final Book book, int position) {
                //去下载某一本数  此处模拟去更新某个进度
                //                executorService.execute(new Runnable() {
                //                    @Override
                //                    public void run() {
                final DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setTypeId(book.getTypeId());
                downloadInfo.setBookId(book.getBookId());
                Log.e("开启", "下载");
                ValueAnimator valueAnimator = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    valueAnimator = ValueAnimator.ofInt(0, 100);
                    valueAnimator.setDuration(10000);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer value = (Integer) animation.getAnimatedValue();
                            downloadInfo.setProgress(value);
                            Message message = Message.obtain();
                            message.what = 100;
                            message.obj = downloadInfo;
                            handler.sendMessage(message);
                        }
                    });
                    valueAnimator.start();
                }
                //                    }
                //                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(autoAdapter1);

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BookList bookList = new BookList();
            bookList.setTitle("主题" + i);
            bookList.setTypeId(i);
            List<Book> books = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                Book book = new Book();
                book.setTypeId(i);
                book.setBookId(j);
                book.setName("主题" + i + ":" + "第" + j + "本");
                books.add(book);
            }
            bookList.setBooks(books);
            data.add(bookList);
        }
    }
}
