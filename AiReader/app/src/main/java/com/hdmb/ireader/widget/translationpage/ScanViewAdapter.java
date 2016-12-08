package com.hdmb.ireader.widget.translationpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tb.hdmb.R;
import com.hdmb.ireader.utils.Log;

import java.util.List;

/**
 * 作者    武博
 * 时间    2016/7/27 0027 15:41
 * 文件    TbReader
 * 描述
 */
public class ScanViewAdapter extends PageAdapter {
    Context context;
    List<String> pages;

    public ScanViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.pages = list;
    }

    public void addContent(View view, int position) {
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView tv = (TextView) view.findViewById(R.id.index);
        if ((position - 1) < 0 || (position - 1) >= getCount())
            return;
        content.setText(pages.get(position - 1));
        Log.e("第" + position + "页", pages.get(position - 1));
        tv.setText("第" + position + "页");
    }

    public int getCount() {
        return pages.size();
    }

    public View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.page_layout, null);
        return view;
    }
}
