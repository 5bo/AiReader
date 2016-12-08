package com.hdmb.ireader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tb.hdmb.R;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 14:14
 * 文件    TbReader
 * 描述
 */
public class LoadingDialog extends Dialog {

    Context context;
    LinearLayout ll_loading;
    TextView tv_message;

    public LoadingDialog(Context context) {
        super(context, R.style.DialogFullscreen);
        this.context = context;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected LoadingDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        tv_message = (TextView) view.findViewById(R.id.tv_content);
        setContentView(view);

        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.lodingDialogWindowAnim); //设置窗口弹出动画
        view.findViewById(R.id.fl_parent).setOnClickListener(v -> {
//            dismiss();
        });
    }

    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message.toString().trim());
        } else {
            tv_message.setVisibility(View.GONE);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        View iv_img = findViewById(R.id.iv_img);
        iv_img.startAnimation(animation);
    }

    public void setHideBackground(boolean isShow) {
        if (isShow) {
            ll_loading.setBackgroundResource(R.drawable.ic_loading_bg);
        } else {
            ll_loading.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
