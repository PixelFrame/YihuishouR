/*
 * Created by Pixel Frame on 2017/11/4.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.seu.srtp.prjyi.yihuishour.R;

/**
 * Created by pm421 on 7/21/2017.
 * 扩展的GridView Adapter
 */

public class HomeGridAdapter extends BaseAdapter {
    private Context mContext;
    String[] img_text = new String[]{
            "分类1",
            "分类2",
            "分类3",
            "分类4",
            "分类5",
            "分类6",
            "分类7",
            "分类8",
            "分类9"
    };
    int[] imgs = new int[]{
            R.mipmap.grid_item_1,
            R.mipmap.grid_item_2,
            R.mipmap.grid_item_3,
            R.mipmap.grid_item_4,
            R.mipmap.grid_item_5,
            R.mipmap.grid_item_6,
            R.mipmap.grid_item_7,
            R.mipmap.grid_item_8,
            R.mipmap.grid_item_9,
    };

    public HomeGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public int getCount() {
        return img_text.length;
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.grid_tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.grid_iv_item);
        iv.setBackgroundResource(this.imgs[position]);
        tv.setText(img_text[position]);
        return convertView;
    }
}