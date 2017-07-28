/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour.util;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by pm421 on 7/21/2017.
 * GridView使用的BaseViewHolder
 */

class BaseViewHolder {
    public BaseViewHolder() {
    }

    static <T extends View> T get(View view, int id) {
        SparseArray viewHolder = (SparseArray)view.getTag();
        if(viewHolder == null) {
            viewHolder = new SparseArray();
            view.setTag(viewHolder);
        }

        View childView = (View)viewHolder.get(id);
        if(childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        return (T) childView;
    }
}
