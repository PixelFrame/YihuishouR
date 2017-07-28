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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pm421 on 7/15/2017.
 * 地图导航页 包含回收点列表的ListView
 */

public class AmapMapFragment extends android.support.v4.app.Fragment {

    ListView mListView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_tab, container, false);
        initListView(view);
        return view;
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.id_view_rplist);
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("ItemTitle", "Point " + i);
            map.put("ItemText", "POINT " + i + " LOCATION DATA");
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
