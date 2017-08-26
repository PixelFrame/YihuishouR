/*
 * Created by Pixel Frame on 2017/8/26.
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.seu.srtp.prjyi.yihuishour.util.GlobalData;
import cn.edu.seu.srtp.prjyi.yihuishour.util.Order;
import cn.edu.seu.srtp.prjyi.yihuishour.util.XmlParser;
import cn.edu.seu.srtp.prjyi.yihuishour.util._CONSTANTS;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Button mScan = (Button) findViewById(R.id.id_button_scan);
        View.OnClickListener mLisScan = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customScan();
            }
        };
        mScan.setOnClickListener(mLisScan);

    }

    public void customScan(){
        new IntentIntegrator(this)
                .setPrompt("请将二维码置于框内")
                .setOrientationLocked(false)
                .setCaptureActivity(ScanActivity.class)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"扫描失败，请重试",Toast.LENGTH_LONG).show();
            } else {
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                sendOrder(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void sendOrder(final String scanRes) {
        RequestQueue requestQueue = newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, _CONSTANTS.OrderURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderActivity.this, "订单建立成功", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, "订单建立失败", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                GlobalData globalData = (GlobalData) getApplication();
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(globalData.getUser().getId()));
                List<Order> orders = new ArrayList<>();
                orders.add(createOrder());
                try {
                    params.put("scanRes", scanRes);
                    params.put("xmlStr", XmlParser.serialize_order(orders));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private Order createOrder() {
        Order order = new Order();
        order.setStatus(0);
        order.setAlias("");
        order.setAttrib(0);
        order.setDate(Integer.parseInt(new SimpleDateFormat("yyyymmdd").format(new Date())));
        return order;
    }
}
