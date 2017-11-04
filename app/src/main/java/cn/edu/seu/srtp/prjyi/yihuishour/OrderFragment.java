/*
 * Created by Pixel Frame on 2017/11/4.
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;
import cn.edu.seu.srtp.prjyi.yihuishour.util.Order;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

import static cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS.LEVEL_ADMIN;

/**
 * Created by pm421 on 7/15/2017.
 * 订单页
 */

public class OrderFragment extends android.support.v4.app.Fragment {
    ListView mListView;
    Button mNewOrderButton;
    View.OnClickListener mLisNewOrder;

    List<Order> orders;
    SimpleAdapter listAdapter;
    List<HashMap<String, Object>> data;
    AdapterView.OnItemClickListener lisItemClick;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_tab, container, false);
        initListView(view);
        internetRequest();
        GlobalData globalData = (GlobalData) getActivity().getApplication();
        mNewOrderButton = (Button) view.findViewById(R.id.id_button_neworder);
        mLisNewOrder = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalData globalData = (GlobalData) getActivity().getApplication();
                if (globalData.getUser() == null) {
                    Toast.makeText(getActivity().getBaseContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else startActivity(new Intent(getActivity(), OrderActivity.class));}
        };
        if (globalData.getUser().getLevel()/1000 == LEVEL_ADMIN) mNewOrderButton.setVisibility(View.INVISIBLE);
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
        lisItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalData globalData = (GlobalData)getActivity().getApplication();
                ListView listView = (ListView)parent;
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                if(globalData.getUser() != null) {
                    if (globalData.getUser().getLevel() / 1000 == 666) {
                        Intent it = new Intent(getActivity().getBaseContext(), ProcessOrderActivity.class);
                        it.putExtra("oid", map.get("OrderId"));
                        startActivity(it);
                    } else {
                        Intent it = new Intent(getActivity().getBaseContext(), CheckOrderActivity.class);
                        it.putExtra("oid", map.get("OrderId"));
                        startActivity(it);
                    }
                }
            }
        };
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(lisItemClick);
    }

    private void internetRequest() {
        final GlobalData globalData = (GlobalData) getActivity().getApplication();
        if (globalData.getUser() == null) return;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest (Request.Method.POST, _CONSTANTS.CheckOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("NULL")) {
                            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                orders = XmlParser.parse_order(response);
                                if(orders != null) {
                                    List<HashMap<String, Object>> newdata = new ArrayList<>();
                                    for (Order order : orders) {
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("OrderName", order.getAlias());
                                        map.put("OrderDate", (order.dateToString()));
                                        switch (order.getStatus()){
                                            case 0 : map.put("OrderStatus",  "订单未完成"); break;
                                            case 1 : map.put("OrderStatus",  "订单已处理，请等待回收");break;
                                            case 2 : map.put("OrderStatus",  "订单已完成"); break;
                                            case -1 : map.put("OrderStatus", "订单已取消"); break;
                                            default : map.put("OrderStatus", "未知状态"); break;
                                        }
                                        map.put("OrderId", "#" + order.getId());
                                        map.put("OrderImage", order.getStatus()==0? R.mipmap.order_icon_incomplete : R.mipmap.order_icon_complete);
                                        newdata.add(map);
                                    }
                                    data.clear();
                                    data.addAll(newdata);
                                }
                                listAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "系统错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                if(globalData.getUser() != null) {
                    if (globalData.getUser().getLevel() / 1000 == LEVEL_ADMIN) {
                        params.put("uid", String.valueOf(-100000));
                    } else {
                        params.put("uid", Integer.toString(globalData.getUser().getId()));
                    }
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
