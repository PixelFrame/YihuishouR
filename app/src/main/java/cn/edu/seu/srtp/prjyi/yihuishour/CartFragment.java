/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pm421 on 7/15/2017.
 * 购物车页
 */

public class CartFragment extends android.support.v4.app.Fragment {
    ListView mListView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_tab, container, false);

        initListView(view);
        return view;
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.id_view_cartlsit);
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemName", "购物车项目"+ (i+1) );
            map.put("ItemPrice", "价格： ￥" + i*450);
            map.put("ItemNum", i);
            map.put("ItemImage", R.mipmap.cart_unpressed);
            data.add(map);
        }
        SimpleAdapter mList = new SimpleAdapter(getContext(),
                data,
                R.layout.cart_item,
                new String[] {"ItemName", "ItemPrice", "ItemNum", "ItemImage"},
                new int[] {R.id.id_tv_itemname, R.id.id_tv_itemprice, R.id.id_edit_num, R.id.id_iv_itemimage});
        mListView.setAdapter(mList);
    }
}
