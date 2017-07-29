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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.seu.srtp.prjyi.yihuishour.util.LocationPoint;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlRequest;

/**
 * Created by pm421 on 7/15/2017.
 * 地图导航页 包含回收点列表的ListView
 */

public class AmapMapFragment extends android.support.v4.app.Fragment {

    ListView mListView;
    List<LocationPoint> locationPoints;

    String locationURL = "";


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab, container, false);
        initListView(view);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        XmlRequest xmlRequest = new XmlRequest(Request.Method.POST, locationURL, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    //我们看到第二个参数 “listRoot”我们传入的是item。第三个参数是ListBean.class
                    //第四个参数“beanRoot”我们传入的是root。第四个参数是Bean.class
                    locationPoints = XmlParser.parse_location(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        requestQueue.add(xmlRequest);
        return view;
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.id_view_rplist);
        List<HashMap<String, String>> data = new ArrayList<>();
        for(LocationPoint locationPoint:locationPoints)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("ItemTitle", locationPoint.getPoiName());
            map.put("ItemText", locationPoint.getPoiDesc());
            data.add(map);
        }
        SimpleAdapter mRPlist = new SimpleAdapter(getContext(),
                data,
                R.layout.rplist_item,
                new String[] {"ItemTitle", "ItemText"},
                new int[] {R.id.list_tv_title, R.id.list_tv_info});
        mListView.setAdapter(mRPlist);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),MapActivity.class));
            }
        });
    }
}
