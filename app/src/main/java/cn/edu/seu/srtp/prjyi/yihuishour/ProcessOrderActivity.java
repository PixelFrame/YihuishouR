/*
 * Created by Pixel Frame on 2017/11/5.
 * Copyright (c) 2017. All Rights Reserved.
 *
 * To use contact by e-mail: pm421@live.com.
 */

package cn.edu.seu.srtp.prjyi.yihuishour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.Order;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class ProcessOrderActivity extends AppCompatActivity {

    TextView tvOid;
    TextView tvItem;
    TextView tvDate;
    TextView tvLocation;
    Button btnAccept;
    View.OnClickListener lisAccept;
    RequestQueue requestQueue;
    Order order;
    int oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_order);
        tvOid = findViewById(R.id.id_tv_po_oid);
        tvDate = findViewById(R.id.id_tv_po_date);
        tvItem = findViewById(R.id.id_tv_po_item);
        tvLocation = findViewById(R.id.id_tv_po_location);
        btnAccept = findViewById(R.id.id_button_accept);
        requestQueue = newRequestQueue(this);
        init();
    }

    private void init(){
        Intent it = getIntent();
        oid = it.getIntExtra("oid",0);
        getOrder();
        tvOid.setText(String.valueOf(oid));
        lisAccept = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getStatus() == 0){
                    sendAcc(0);
                } else if (order.getStatus() == 1) {
                    sendAcc(1);
                }

            }
        };
    }

    private void getOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.GetOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("NULL")) {
                            Toast.makeText(ProcessOrderActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            try {
                                order = XmlParser.parse_order(response).get(0);
                                updateView();
                                btnAccept.setOnClickListener(lisAccept);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(ProcessOrderActivity.this, "系统错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProcessOrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("oid", String.valueOf(oid));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateView() {
        String ItemCat = null;
        String BtnText = null;
        if (order.getItems().isEmpty()) tvItem.setText("无");
        else {
            switch (order.getItems().get(0).getCatagory()) {
                case 0:
                    ItemCat = getString(R.string.Type_0);
                    break;
                case 1:
                    ItemCat = getString(R.string.Type_1);
                    break;
                case 2:
                    ItemCat = getString(R.string.Type_2);
                    break;
                case 3:
                    ItemCat = getString(R.string.Type_3);
                    break;
                case 4:
                    ItemCat = getString(R.string.Type_4);
                    break;
                case 5:
                    ItemCat = getString(R.string.Type_5);
                    break;
            }
            tvItem.setText(String.format("物品类别: %s\n物品数量: %s",
                    ItemCat,
                    order.getItems().get(0).getNum()));
        }
        switch (order.getStatus()) {
            case -1: BtnText = "恢复"; break;
            case 0: BtnText = "接单"; break;
            case 1: BtnText = "完成"; break;
            case 2: BtnText = "取消"; break;
        }
        tvDate.setText(order.dateToString());
        if(order.getLocation() == null) {
            tvLocation.setText("无");
        } else {
            tvLocation.setText(order.getLocation());
        }
        btnAccept.setText(BtnText);
    }

    private void sendAcc(final int stat) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.EditOrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("成功")) {
                            Toast.makeText(ProcessOrderActivity.this, "接单成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ProcessOrderActivity.this, "接单失败，请重试", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProcessOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                } ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("type", "0");
                params.put("oid", String.valueOf(oid));
                params.put("status", String.valueOf(stat + 1));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
