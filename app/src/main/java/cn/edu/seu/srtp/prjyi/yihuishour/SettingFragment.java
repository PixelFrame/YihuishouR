/*
 * Created by Pixel Frame on 2017/11/4.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.seu.srtp.prjyi.yihuishour.settingActivities.AboutActivity;
import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;
import cn.edu.seu.srtp.prjyi.yihuishour.util.User;

import static cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS.LEVEL_ADMIN;
import static cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS.LEVEL_SERV;
import static com.android.volley.toolbox.Volley.newRequestQueue;

/**
 * Created by pm421 on 7/15/2017.
 * 设置页
 */

public class SettingFragment extends android.support.v4.app.Fragment {
    ListView settingList;
    ImageButton userButton;
    View.OnClickListener lisSettings;
    View.OnClickListener lisAvatar;
    ImageView userAvatar;
    TextView userName;
    TextView userLevel;
    TextView userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_tab, container, false);
        initListView(view);
        userButton = (ImageButton) view.findViewById(R.id.id_ib_edituser);
        lisSettings = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LoginActivity.class);
                startActivity(it);
            }
        };
        lisAvatar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SelectAvatarActivity.class));
            }
        };
        userButton.setOnClickListener(lisSettings);

        userAvatar = (ImageView) view.findViewById(R.id.id_iv_avatar);
        userName = (TextView) view.findViewById(R.id.id_tv_username);
        userInfo = (TextView) view.findViewById(R.id.id_tv_userinfo);
        userLevel = (TextView) view.findViewById(R.id.id_tv_userlv);
        initUserView();

        return view;
    }

    private void initUserView() {
        GlobalData globalData = (GlobalData) getActivity().getApplication();
        if(globalData.getUser() != null) {
            if (globalData.getUser().getId() != -1)
                userButton.setVisibility(View.INVISIBLE);
            if (globalData.getUser().getAvatar() != null)
                updateAvatar();
            userName.setText(globalData.getUser().getName());
            userLevel.setText(String.format(getResources().getString(R.string.formatuserlv),globalData.getUser().getLevel() % 1000 + 1));

            String info;
            if (globalData.getUser().getLevel()/1000 == LEVEL_ADMIN) {
                info = "系统管理员";
            } else if (globalData.getUser().getLevel()/1000 == LEVEL_SERV){
                info = "服务提供商";
            } else if (globalData.getUser().getLevel()/1000 > 0) {
                info = "VIP" + globalData.getUser().getLevel()/1000 + "用户";
            } else { info = "普通用户"; }
            userInfo.setText(info);
            userAvatar.setOnClickListener(lisAvatar);
        }
    }

    private void initListView(View view) {
        settingList = (ListView) view.findViewById(R.id.id_view_setlist);
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

        SimpleAdapter setList = new SimpleAdapter(getContext(),
                setting_text,
                R.layout.stlist_item,
                new String[]{"SettingItem"},
                new int[]{R.id.id_tv_setting});
        settingList.setAdapter(setList);
        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position != 7) startActivity(new Intent(getActivity(),AboutActivity.class));
               else {
                   GlobalData globalData = (GlobalData) getActivity().getApplication();
                   globalData.setUser(new User());
                   userButton.setVisibility(View.VISIBLE);
                   userAvatar.setImageResource(R.mipmap.setting_unpressed);
               }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        initUserView();
    }
    private void updateAvatar() {
        GlobalData globalData = (GlobalData) getActivity().getApplicationContext();
        RequestQueue requestQueue = newRequestQueue(getContext());
        ImageRequest imageRequest = new ImageRequest(globalData.getUser().getAvatar(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        GlobalData resGlobalData = (GlobalData) getActivity().getApplicationContext();
                        resGlobalData.setBmpAvatar(bitmap);
                        userAvatar.setImageBitmap(resGlobalData.getBmpAvatar());
                    }
                }, 500, 500, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(imageRequest);
    }
}
