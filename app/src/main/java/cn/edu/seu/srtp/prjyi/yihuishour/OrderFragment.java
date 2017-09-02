/*
 * Created by Pixel Frame on 2017/9/2.
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.edu.seu.srtp.prjyi.yihuishour.util.Order;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlRequest;

/**
 * Created by pm421 on 7/15/2017.
 * 订单页
 */

public class OrderFragment extends android.support.v4.app.Fragment {
    ListView mListView;
    Button mNewOrderButton;
    View.OnClickListener mLisNewOrder;

    List<Order> orders;
    String locationURL = "http://115.159.188.117/data/Orders_test.xml";
    SimpleAdapter listAdapter;
    List<HashMap<String, Object>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_tab, container, false);
        initListView(view);
        internetRequest();
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
        if(data == null) {
            data = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            map.put("OrderName", "无数据");
            map.put("OrderDate", "无数据");
            map.put("OrderStatus", "无数据");
            map.put("OrderId", "#****************");
            map.put("OrderImage", R.mipmap.order_icon_incomplete);
            data.add(map);
        }
        listAdapter = new SimpleAdapter(getContext(),
                data,
                R.layout.orlist_item,
                new String[] {"OrderName", "OrderDate", "OrderStatus", "OrderId", "OrderImage"},
                new int[] {R.id.id_tv_order, R.id.id_tv_orderdate, R.id.id_tv_orderstatus, R.id.id_tv_orderid, R.id.id_iv_order});
        mListView.setAdapter(listAdapter);
    }

    private void internetRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        XmlRequest xmlRequest = new XmlRequest(locationURL, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    orders = XmlParser.parse_order(response);
                    if(orders != null) {
                        List<HashMap<String, Object>> newdata = new ArrayList<>();
                        for (Order order : orders) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("OrderName", order.getAlias());
                            map.put("OrderDate", (order.dateToString()));
                            map.put("OrderStatus", order.getStatus()==0? "订单已完成":"订单未完成");
                            map.put("OrderId", "#" + order.getId());
                            map.put("OrderImage", order.getStatus()==0? R.mipmap.order_icon_complete : R.mipmap.order_icon_incomplete);
                            newdata.add(map);
                        }
                        data.clear();
                        data.addAll(newdata);
                    }
                    listAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(xmlRequest);
    }
}
