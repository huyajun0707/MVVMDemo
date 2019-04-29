package com.hyj.demo.viewpagerdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private String title;
    private TextView tvTitle;
    private RecyclerView recyclerView;

    public static MyFragment newInstance(String title, String content) {
        Bundle args = new Bundle();
        args.putString("title", title);
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        tvTitle.setText(title);
        initRecyclerVeiw();
        return rootView;
    }

    private void initRecyclerVeiw() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        List<ItemInfo> itemInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setName("第" + i + "条");
            itemInfo.setIcon(R.mipmap.ic_launcher);
            itemInfos.add(itemInfo);
        }
        MyAdapter myAdapter = new MyAdapter(itemInfos);
        recyclerView.setAdapter(myAdapter);
    }

}
