/*
 * Created by Pixel Frame on 2017/8/9.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;
import cn.edu.seu.srtp.prjyi.yihuishour.util.ImageloaderGridAdapter;

public class SelectAvatarActivity extends AppCompatActivity implements View.OnClickListener{

    GridView avatarGridView;
    Button avatarConfirm;
    int avatarID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);
        avatarGridView = (GridView) findViewById(R.id.id_gv_avatar);
        avatarGridView.setAdapter(new ImageloaderGridAdapter(this));
        avatarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                avatarID = position + 1;
                String txt = "选择头像"+avatarID;
                avatarConfirm.setText(txt);
            }
        });
        avatarConfirm = (Button) findViewById(R.id.id_button_avatar_confirm);
        avatarConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_button_avatar_confirm) {
            final GlobalData globalData = (GlobalData) getApplication();
            globalData.getUser().setAvatar("http://115.159.188.117/img/avatar/" + avatarID + ".png");
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://115.159.188.117/PHP/select_avatar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SelectAvatarActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SelectAvatarActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("id", Integer.toString(globalData.getUser().getId()));
                    params.put("aid", Integer.toString(avatarID));
                    return params;
                }
            };
            requestQueue.add(stringRequest); 
            finish();
        }
    }
}
