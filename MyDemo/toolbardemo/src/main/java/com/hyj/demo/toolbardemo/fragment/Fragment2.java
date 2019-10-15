package com.hyj.demo.toolbardemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyj.demo.toolbardemo.R;
import com.hyj.demo.toolbardemo.ui.FragmentStatusBarActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("---->" + this.getClass().getName(), "setUserVisibleHint:" + isVisibleToUser);
        if ( getActivity()!=null&&((FragmentStatusBarActivity) getActivity()).getStatusView() != null) {
            if (isVisibleToUser) {
                ((FragmentStatusBarActivity) getActivity()).getStatusView().setBackgroundColor(Color.BLUE);
            } else {
                ((FragmentStatusBarActivity) getActivity()).getStatusView().setBackgroundColor(Color.YELLOW);
            }
        }


    }
}
