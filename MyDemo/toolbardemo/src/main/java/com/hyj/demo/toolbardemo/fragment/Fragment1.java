package com.hyj.demo.toolbardemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyj.demo.toolbardemo.R;
import com.hyj.demo.toolbardemo.ui.FragmentStatusBarActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    public Fragment1() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        view.findViewById(R.id.btSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((FragmentStatusBarActivity) getActivity()).getStatusView() != null) {
                    Log.d("---->" + this.getClass().getName(), "statusView:");
                    ((FragmentStatusBarActivity) getActivity()).getStatusView().setBackgroundColor(Color.YELLOW);

                }
            }
        });


        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("---->" + this.getClass().getName(), "setUserVisibleHint:" + isVisibleToUser);
        if (getActivity() != null) {
            if (((FragmentStatusBarActivity) getActivity()).getStatusView() != null) {
                Log.d("---->" + this.getClass().getName(), "statusView:");
                if (isVisibleToUser) {
                    ((FragmentStatusBarActivity) getActivity()).getStatusView().setBackgroundColor(Color.YELLOW);
                } else {
                    ((FragmentStatusBarActivity) getActivity()).getStatusView().setBackgroundColor(Color.BLUE);
                }
            }
        }

    }
}
