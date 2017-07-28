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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.seu.srtp.prjyi.yihuishour.settingActivities.AboutActivity;

/**
 * Created by pm421 on 7/15/2017.
 * 设置页
 */

public class SettingFragment extends android.support.v4.app.Fragment {
    ListView mSettingList;
    ImageButton mUserButton;
    View.OnClickListener mClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_tab, container, false);
        initListView(view);
        mUserButton = (ImageButton) view.findViewById(R.id.id_ib_edituser);
        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LoginActivity.class);
                startActivity(it);
            }
        };
        mUserButton.setOnClickListener(mClickListener);
        return view;
    }

    private void initListView(View view) {
        mSettingList = (ListView) view.findViewById(R.id.id_view_setlist);
        ArrayList<HashMap<String, String>> setting_text = new ArrayList<>();
        HashMap<String,String> map = new HashMap<>();
        map.put("SettingItem","历史订单");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","我的优惠");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","我的收藏");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","推送设置");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","隐私设置");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","关于Yi回收");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","检查更新");
        setting_text.add(map);

        map = new HashMap<>();
        map.put("SettingItem","退出登录");
        setting_text.add(map);

        SimpleAdapter mSetlist = new SimpleAdapter(getContext(),
                setting_text,
                R.layout.stlist_item,
                new String[]{"SettingItem"},
                new int[]{R.id.id_tv_setting});
        mSettingList.setAdapter(mSetlist);
        mSettingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),AboutActivity.class));
            }
        });
    }
}
