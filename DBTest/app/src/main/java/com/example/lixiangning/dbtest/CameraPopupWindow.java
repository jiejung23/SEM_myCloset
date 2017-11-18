package com.example.lixiangning.dbtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * Created by lixiangning on 2017/10/22.
 */

public class CameraPopupWindow extends PopupWindow implements View.OnClickListener {
    private Button btnCamera, btnAlbum, btnCancel;
    private View mPopView;
    private OnItemClickListener mListener;
    private View popView;

    public CameraPopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);

        setPopupWindow();
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.list_popup, null);

//        popView = mPopView.findViewById(R.id.pop_layout1);
//        popView.getBackground().setAlpha(150);

        btnCamera = (Button) mPopView.findViewById(R.id.btn_camera);
        btnAlbum = (Button) mPopView.findViewById(R.id.btn_album);
        btnCancel = (Button) mPopView.findViewById(R.id.btn_cancel);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
//        this.setBackgroundDrawable(new ColorDrawable(0xff000000));// 设置背景透明
//        this.getBackground().setAlpha(100);



        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }
}
