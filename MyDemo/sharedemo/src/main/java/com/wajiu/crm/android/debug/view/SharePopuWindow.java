package com.wajiu.crm.android.debug.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wajiu.crm.android.debug.R;
import com.wajiu.crm.android.debug.listener.OnShareItemClickListener;

/**
 * =========================================================
 * @author      :   HuYajun     <13426236872@163.com>
 * @date        :   2019/4/10 10:45
 * @version     :
 * @description :
 * =========================================================
 */
public class SharePopuWindow extends PopupWindow implements View.OnClickListener {

    private Activity mContext;
    private RelativeLayout rlCancel;
    private View mShareView;
    private OnShareItemClickListener mListener;
    private LinearLayout llQQ, llQzone, llWechat, llWxcircle;

    public SharePopuWindow(Activity context) {
        super(context);
        mContext = context;
        setBackgroudAlpha(context, 0.5f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        mShareView = inflater.inflate(R.layout.share_popupwindow, null);
        rlCancel = (RelativeLayout) mShareView.findViewById(R.id.rlCancel);
        //取消按钮
        rlCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        setView();
        init();
    }

    private void init() {
        llQQ = mShareView.findViewById(R.id.ll_share_item_qq);
        llQQ.setOnClickListener(this);
        llQzone = mShareView.findViewById(R.id.ll_share_item_qzone);
        llQzone.setOnClickListener(this);
        llWechat = mShareView.findViewById(R.id.ll_share_item_wechat);
        llWechat.setOnClickListener(this);
        llWxcircle = mShareView.findViewById(R.id.ll_share_item_wxcircle);
        llWxcircle.setOnClickListener(this);

    }

    private void setBackgroudAlpha(Activity context, float alpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = alpha;
        context.getWindow().setAttributes(lp);//改变透明度，并重新设置
    }


    public void setOnItemClickListener(OnShareItemClickListener listener) {
        this.mListener = listener;
    }


    private void setView() {
        //设置SelectPicPopupWindow的View
        this.setContentView(mShareView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation_Push_Bottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xcc000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mShareView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mShareView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroudAlpha(mContext, 1f);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            switch (v.getId()) {
                case R.id.ll_share_item_qq:
                    mListener.onItemClick(0);
                    break;
                case R.id.ll_share_item_qzone:
                    mListener.onItemClick(1);
                    break;
                case R.id.ll_share_item_wechat:
                    mListener.onItemClick(2);
                    break;
                case R.id.ll_share_item_wxcircle:
                    mListener.onItemClick(3);
                    break;
            }

        }

    }

}
