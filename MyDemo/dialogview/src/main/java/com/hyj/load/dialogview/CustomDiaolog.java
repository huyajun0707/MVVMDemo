package com.hyj.load.dialogview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkserver.jk.base.dialog.listener.OnDialogNegativeListener;
import com.jkserver.jk.base.dialog.listener.OnDialogPositiveListener;


/** 
 * =========================================================
 * @author      :   HuYajun     <13426236872@163.com> 
 * @date        :   2019/4/14 18:27
 * @version     :   
 * @description :   
 * =========================================================
 */  

public class CustomDiaolog extends DialogFragment implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvMessageTop;
    private TextView tvMessageBottom;
    private String mTitle;
    private String mMessageTop;
    private String mMessageBottom;
    private String mCenter;
    private LinearLayout llBottomCenter;
    private LinearLayout llBottom;
    private LinearLayout llNegative;
    private LinearLayout llPositive;
    private TextView tvNegative;
    private TextView tvPositive;
    private TextView tvCenter;
    private String mNegative;
    private String mPositive;
    private int mRequstCode;
    private Context mContext;
    private static FragmentManager mFragmentManager;
//    private static SMSDiaolog mSmsDiaolog;
    private OnDialogPositiveListener mOnDialogPositiveListener;
    private OnDialogNegativeListener mOnDialogNegativeListener;

    public static synchronized CustomDiaolog create(FragmentManager fragmentManager) {
//        if (mSmsDiaolog == null) {
//            mSmsDiaolog = new SMSDiaolog();
//        }
        mFragmentManager = fragmentManager;
        return new CustomDiaolog();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llNegative:
                if (mOnDialogNegativeListener != null && mRequstCode != 0) {
                    mOnDialogNegativeListener.onNegativeButtonClicked(mRequstCode);
                }
                getDialog().dismiss();
                break;
            case R.id.llPositive:
                if (mOnDialogPositiveListener != null && mRequstCode != 0) {
                    mOnDialogPositiveListener.onPositiveButtonClicked(mRequstCode);
                }
                getDialog().dismiss();
                break;
            case R.id.llBottomCenter:
                if (mOnDialogPositiveListener != null && mRequstCode != 0) {
                    mOnDialogPositiveListener.onPositiveButtonClicked(mRequstCode);
                }
                getDialog().dismiss();
                break;
        }

    }

    public CustomDiaolog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle = title;
        }
        return this;
    }

    public CustomDiaolog setMessageTop(String message) {
        if (!TextUtils.isEmpty(message)) {
            mMessageTop = message;
        }
        return this;
    }

    public CustomDiaolog setMessageBottom(String message) {
        if (!TextUtils.isEmpty(message)) {
            mMessageBottom = message;
        }
        return this;
    }

    public CustomDiaolog setCenter(String center) {
        mCenter = center;
        return this;
    }

    public CustomDiaolog setNegative(String negative) {
        if (!TextUtils.isEmpty(negative)) {
            mNegative = negative;
        }
        return this;
    }

    public CustomDiaolog setPositive(String positive) {
        if (!TextUtils.isEmpty(positive)) {
            mPositive = positive;
        }
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉Dialog的标题部分
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_custom_view, null);
        tvTitle = view.findViewById(R.id.tvDialogTitle);
        tvMessageTop = view.findViewById( R.id.tvDialogMessageTop);
        tvMessageBottom = view.findViewById( R.id.tvDialogMessageBottom);
        tvNegative = view.findViewById( R.id.tvNegative);
        tvPositive =view.findViewById( R.id.tvPositive);
        tvMessageBottom = view.findViewById( R.id.tvDialogMessageBottom);
        llNegative = view.findViewById(R.id.llNegative);
        llNegative.setOnClickListener(this);
        llPositive = view.findViewById(R.id.llPositive);
        llPositive.setOnClickListener(this);
        llBottomCenter = view.findViewById(R.id.llBottomCenter);
        llBottomCenter.setOnClickListener(this);
        llBottom =view.findViewById(R.id.llBottom);
        llBottom.setOnClickListener(this);
        tvCenter = view.findViewById( R.id.tvCenter);
        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);
        }else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mMessageTop)) {
            tvMessageTop.setText(mMessageTop);
        }else {
            tvMessageTop.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mMessageBottom)) {
            tvMessageBottom.setText(mMessageBottom);
        }else {
            tvMessageBottom.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mNegative)) {
            llBottom.setVisibility(View.VISIBLE);
            llBottomCenter.setVisibility(View.GONE);
            tvNegative.setText(mNegative);
        }
        if (!TextUtils.isEmpty(mPositive)) {
            tvPositive.setText(mPositive);
        }

        if(!TextUtils.isEmpty(mCenter)){
            llBottom.setVisibility(View.GONE);
            llBottomCenter.setVisibility(View.VISIBLE);
            tvCenter.setText(mCenter);
        }else {
            llBottom.setVisibility(View.VISIBLE);
            llBottomCenter.setVisibility(View.GONE);
        }

        if (mContext != null) {
            if (mRequstCode != 0 && mContext instanceof OnDialogPositiveListener) {
                mOnDialogPositiveListener = (OnDialogPositiveListener) mContext;
            }
            if (mRequstCode != 0 && mContext instanceof OnDialogNegativeListener) {
                mOnDialogNegativeListener = (OnDialogNegativeListener) mContext;
            }
        }
        //点击外部不消失
        setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            /**
             * 设置Dialog的大小
             */
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.70), (int) (dm.heightPixels * 0.25));

        }

    }

    public CustomDiaolog setRequestCode(int requestCode) {
        mRequstCode = requestCode;
        return this;
    }

    public void show(Context context) {
        mContext = context;
        super.show(mFragmentManager, null);

    }


}
