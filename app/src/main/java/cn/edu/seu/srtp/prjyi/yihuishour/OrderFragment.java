/*
 * Created by Pixel Frame on 2017/7/28.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by pm421 on 7/15/2017.
 * 订单页
 */

public class OrderFragment extends android.support.v4.app.Fragment {
    ListView mListView;
    Button mNewOrderButton;
    View.OnClickListener mLisNewOrder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_tab, container, false);
        initListView(view);
        mNewOrderButton = (Button) view.findViewById(R.id.id_button_neworder);
        mLisNewOrder = new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(getActivity(), OrderActivity.class));}
        };
        mNewOrderButton.setOnClickListener(mLisNewOrder);
        return view;
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.id_view_orderlist);
        Random r = new Random();
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("OrderName", "回收订单#"+ (i+1) );
            map.put("OrderDate", "建立于 X月X日 HH:MM:SS");
            map.put("OrderStatus", "订单已完成");
            map.put("OrderId", "#" + r.nextDouble());
            map.put("OrderImage", R.mipmap.order_item);
            data.add(map);
        }
        SimpleAdapter mList = new SimpleAdapter(getContext(),
                data,
                R.layout.orlist_item,
                new String[] {"OrderName", "OrderDate", "OrderStatus", "OrderId", "OrderImage"},
                new int[] {R.id.id_tv_order, R.id.id_tv_orderdate, R.id.id_tv_orderstatus, R.id.id_tv_orderid, R.id.id_iv_order});
        mListView.setAdapter(mList);
    }
}
