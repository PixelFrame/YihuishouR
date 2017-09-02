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
import android.widget.AdapterView;
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

import cn.edu.seu.srtp.prjyi.yihuishour.util.LocationPoint;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlRequest;
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

/**
 * Created by pm421 on 7/15/2017.
 * 地图导航页 包含回收点列表的ListView
 */

public class AmapMapFragment extends android.support.v4.app.Fragment {

    ListView mListView;
    List<LocationPoint> locationPoints;
    SimpleAdapter listAdapter;
    List<HashMap<String, String>> data;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab, container, false);
        initListView(view);
        internetRequest();
        return view;
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.id_view_rplist);
        if(data == null) {
            data = new ArrayList<>();
            HashMap<String, String> map = new HashMap<>();
            map.put("ItemTitle", "无数据");
            map.put("ItemText", "无数据");
            data.add(map);
        }
        listAdapter = new SimpleAdapter(getContext(),
                data,
                R.layout.rplist_item,
                new String[] {"ItemTitle", "ItemText"},
                new int[] {R.id.list_tv_title, R.id.list_tv_info});
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(locationPoints != null) {
                    Intent it = new Intent(getActivity(), MapActivity.class);
                    it.putExtra("Latitude", locationPoints.get(position).getLatLng().latitude);
                    it.putExtra("Longitude", locationPoints.get(position).getLatLng().longitude);
                    startActivity(it);
                } else {
                    internetRequest();
                }
            }
        });
    }

    private void internetRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        XmlRequest xmlRequest = new XmlRequest(_CONSTANTS.MapDataURL, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    locationPoints = XmlParser.parse_location(response);
                    if(locationPoints != null) {
                        List<HashMap<String, String>> newdata = new ArrayList<>();
                        for (LocationPoint locationPoint : locationPoints) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("ItemTitle", locationPoint.getPoiName());
                            map.put("ItemText", locationPoint.getPoiDesc());
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
